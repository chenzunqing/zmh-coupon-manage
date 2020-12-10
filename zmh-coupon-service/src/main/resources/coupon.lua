--
-- @Description 扣减库存并且返回对应数量串码LUA脚本
-- @param KEYS[1]
--            库存数key
-- @param KEYS[2]
--            串码key
-- @param ARGV[1]
--            取码数量
-- @return -1:无库存 -2:库存不足 成功则返回串码集合
-- @author cy.wang
--
 local stock = tonumber(redis.call('get', KEYS[1]))
 local num = tonumber(ARGV[1])
 local coupons = {}
 if (redis.call('exists', KEYS[2]) == 1) then
     if (stock == 0) then
		redis.call('del',KEYS[1])
		 return -1
		end
     if (stock >= num) then
		  stock = tonumber(redis.call('incrby', KEYS[1], 0 - num))
		    if(stock == 0) then 
		  	 redis.call('del',KEYS[1])
		  	end
		  for i=num ,1,-1 do
		    table.insert(coupons, redis.call('lpop', KEYS[2]))
		   end
		    return coupons
		end
		 return -2
  end
 return -1;