package com.zmh.coupon.service.impl;

import com.alibaba.fastjson.JSON;
import com.zmh.coupon.model.Coupon;
import com.zmh.coupon.service.RedisCouponService;
import com.zmh.coupon.service.dto.CouponDTO;
import com.zmh.coupon.util.constan.RedisPrefix;
import com.zmh.coupon.util.enums.ErrorMessageEnum;
import com.zmh.coupon.util.enums.coupon.CouponStatus;
import com.zmh.coupon.util.exception.DataValidateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @author chenzunqing
 * @version V1.0
 * @title : RedisCouponServiceImpl
 * @Description: redis优惠卷相关操作实现
 * @date 2020/9/14 13:42
 **/
@Slf4j
@Service
public class RedisCouponServiceImpl implements RedisCouponService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据 userId 和状态找到redis的优惠券列表数据
     * @param userId
     * @param status
     * @return
     */
    @Override
    public List<CouponDTO> getRedisCoupons(Long userId, Integer status) {
        String redisKey = setRedisKey(userId,status);
        // redis 查询是否有用户的 key 对应的值集合
        List<String> couponList =  (List<String>)redisTemplate.opsForHash().values(redisKey)
                                .stream()
                                .map(coupon -> Objects.toString(coupon, null))
                                .collect(Collectors.toList());
        log.info("redis查询优惠卷{}",couponList);
        if (CollectionUtils.isEmpty(couponList)) {
            saveEmptyCouponListToRedis(userId, Collections.singletonList(status));
            return null;
        }

        return couponList.stream().map(coupon -> JSON.parseObject(coupon, CouponDTO.class)).collect(Collectors.toList());
    }

    /**
     * 保存空的优惠券列表到缓存中，防止缓存穿透。
     * @param userId
     * @param status
     */
    @Override
    public void saveEmptyCouponListToRedis(Long userId, List<Integer> status) {
        //插入一个空的
        Map<String, String> invalidCouponMap = new HashMap<>();
        Coupon coupon = new Coupon();
        coupon.setId(-1);
        invalidCouponMap.put("-1", JSON.toJSONString(coupon));

        SessionCallback sessionCallback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                status.forEach(status -> {
                    String redisKey = setRedisKey(userId, status);
                    operations.opsForHash().putAll(redisKey, invalidCouponMap);
                });
                return null;
            }
        };
        redisTemplate.executePipelined(sessionCallback);
        log.info("用户查询优惠卷没有时，保存空的数据到redis对应key{}",invalidCouponMap);
    }

    /**
     * 从 redis 中获取一个优惠券码
     * @param templateId
     * @return
     */
    @Override
    public String getRedisCouponCode(Integer templateId){
        String redisKey = String.format("%s%s",RedisPrefix.COUPON_TEMPLATE_CODE, templateId.toString());

        return (String) redisTemplate.opsForList().leftPop(redisKey);
    }

    /**
     * 将优惠券保存到 redis 中
     * @param userId
     * @param coupons
     * @param status
     * @return
     * @throws DataValidateException
     */
    @Override
    public Integer addCouponToRedis(Long userId, List<CouponDTO> coupons, Integer status) throws DataValidateException {
        Integer result = -1;
        CouponStatus couponStatus = CouponStatus.get(status);

        switch (couponStatus) {
            case USABLE:
                result = addCouponUsableRedis(userId, coupons);
                break;
            case USED:
                result = addCouponTUsedRedis(userId, coupons);
                break;
            case EXPIRED:
                result = addCouponExpiredRedis(userId, coupons);
                break;
        }

        return result;
    }



    /**
     * 新添加优惠卷到 redis
     * */
    private Integer addCouponUsableRedis(Long userId, List<CouponDTO> coupons) {

        Map<String, String> usableMap = new HashMap<>();
        coupons.forEach(coupon ->usableMap.put(coupon.getId().toString(),JSON.toJSONString(coupon)));

        String redisKey = setRedisKey(userId,CouponStatus.USABLE.getCode());
        redisTemplate.opsForHash().putAll(redisKey, usableMap);
        redisTemplate.expire(redisKey,getRandomExpirationTime(1, 2), TimeUnit.SECONDS);

        return usableMap.size();
    }

    /**
     * 添加已使用 ， 删除未使用  redis
     * @param userId
     * @param coupons
     * @return
     * @throws DataValidateException
     */
    private Integer addCouponTUsedRedis(Long userId, List<CouponDTO> coupons) throws DataValidateException {
        // 1.创建以使用的优惠卷，和未使用
        Map<String, String> usedMap = new HashMap<>(coupons.size());
        coupons.forEach(coupon -> usedMap.put(coupon.getId().toString(),JSON.toJSONString(coupon)));

        String redisKeyForUsable = setRedisKey(userId,CouponStatus.USABLE.getCode());
        String redisKeyForUsed = setRedisKey(userId,CouponStatus.USED.getCode());

        // 获取当前用户可用的优惠券
        List<CouponDTO> usableCoupons = getRedisCoupons(userId, CouponStatus.USABLE.getCode());

        // 检查当前的优惠券参数是否与 redis 中的匹配
        List<Integer> UsableIds = usableCoupons.stream().map(CouponDTO::getId).collect(Collectors.toList());
        List<Integer> UsedIds = coupons.stream().map(CouponDTO::getId).collect(Collectors.toList());

        if (!CollectionUtils.isSubCollection(UsedIds, UsableIds)) {
            throw new DataValidateException("不能使用可用优惠卷没有的优惠卷！");
        }

        SessionCallback<Objects> sessionCallback = new SessionCallback<Objects>() {
            @Override
            public Objects execute(RedisOperations operations) throws DataAccessException {

                // 1. 已使用的优惠券 redis 添加
                operations.opsForHash().putAll(redisKeyForUsed, usedMap );
                // 2. 可用的优惠券 redis 删除
                operations.opsForHash().delete(redisKeyForUsable, UsedIds.toArray());
                // 3. 重置过期时间
                operations.expire(redisKeyForUsable,getRandomExpirationTime(1, 2),TimeUnit.SECONDS);
                operations.expire( redisKeyForUsed, getRandomExpirationTime(1, 2),TimeUnit.SECONDS);

                return null;
            }
        };
           redisTemplate.executePipelined(sessionCallback);

        return coupons.size();
    }

    /**
     *  删除可用  添加获取 redis
     * @param userId
     * @param coupons
     * @return
     * @throws DataValidateException
     */
    private Integer addCouponExpiredRedis(Long userId, List<CouponDTO> coupons) throws DataValidateException{

        // 最终需要保存的
        Map<String, String> expiredMap = new HashMap<>(coupons.size());
        coupons.forEach(coupon -> expiredMap.put(coupon.getId().toString(),JSON.toJSONString(coupon)));
        // 定义 redis 的 key 键
        String redisKeyForUsable = setRedisKey( userId,CouponStatus.USABLE.getCode());
        String redisKeyForExpired = setRedisKey(userId,CouponStatus.EXPIRED.getCode() );

        // 获取可用优惠卷
        List<CouponDTO> usableCoupons = getRedisCoupons(userId, CouponStatus.USABLE.getCode());

        // 检查当前的优惠券参数是否与 redis 中的匹配
        List<Integer> usableIds = usableCoupons.stream() .map(CouponDTO::getId).collect(Collectors.toList());
        List<Integer> paramIds = coupons.stream().map(CouponDTO::getId).collect(Collectors.toList());
        if (!CollectionUtils.isSubCollection(paramIds, usableIds)) {
            throw new DataValidateException("已过期优惠卷中必须是为使用优惠卷列中！");
        }

        SessionCallback<Objects> sessionCallback = new SessionCallback<Objects>() {
            @Override
            public Objects execute(RedisOperations operations) throws DataAccessException {
                // 1. 已过期的优惠券 redis 保存
                operations.opsForHash().putAll(redisKeyForExpired, expiredMap);
                // 2. 可用的优惠券 redis 清除
                operations.opsForHash().delete(redisKeyForUsable, usableIds.toArray() );
                // 3. 重置过期时间
                operations.expire(redisKeyForUsable,getRandomExpirationTime(1, 2),TimeUnit.SECONDS);
                operations.expire(redisKeyForExpired,getRandomExpirationTime(1, 2), TimeUnit.SECONDS);
                return null;
            }
        };
        redisTemplate.executePipelined(sessionCallback);

        return coupons.size();
    }

    /**
     * 设置用户优惠卷状态保存到redis
     * @param userId
     * @param status
     * @return
     */
    private String setRedisKey(Long userId,Integer status){
        String redisKey = null;
        CouponStatus couponStatus = CouponStatus.get(status);

        switch (couponStatus) {
            case USABLE:
                redisKey = String.format("%s%s",RedisPrefix.COUPON_USER_USABLE, userId);
                break;
            case USED:
                redisKey = String.format("%s%s",RedisPrefix.COUPON_USER_USED, userId);
                break;
            case EXPIRED:
                redisKey = String.format("%s%s",  RedisPrefix.COUPON_USER_EXPIRED, userId);
                break;
        }

        return redisKey;
    }
    // 随机获取过期时间
    private Long getRandomExpirationTime(Integer min, Integer max) {

        return RandomUtils.nextLong(min * 60 * 60,max * 60 * 60);
    }
}
