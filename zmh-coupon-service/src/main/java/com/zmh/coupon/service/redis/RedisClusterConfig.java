//package com.zmh.coupon.service.redis;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * @author 陈尊清
// * @create 2020-09-12-18:09
// */
//@Configuration
//public class RedisClusterConfig {
//
//
//    @Value("${spring.redis.cluster.nodes}")
//    private String clusterNodes;
//
//    @Value("${spring.redis.database}")
//    private int database;
//
//    @Value("${spring.redis.timeout}")
//    private int timeout;
//
//    @Value("${spring.redis.pool.max-idle}")
//    private int maxIdle;
//
//    @Value("${spring.redis.pool.min-idle}")
//    private int minIdle;
//
//    @Value("${spring.redis.pool.max-active}")
//    private int maxActive;
//
//    @Value("${spring.redis.pool.max-wait}")
//    private long maxWait;
//
//    @Bean
//    public JedisCluster getJedisCluster() {
//        return new JedisCluster(getNodes(), timeout, poolConfig());
//    }
//
//    private JedisPoolConfig poolConfig() {
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxIdle(maxIdle);
//        config.setMinIdle(minIdle);
//        config.setMaxTotal(maxActive);
//        config.setMaxWaitMillis(maxWait);
//        return config;
//    }
//
//    private Set<HostAndPort> getNodes() {
//        String[] cNodes = clusterNodes.split(",");
//        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
//        // 分割出集群节点
//        String[] hp;
//        for (String node : cNodes) {
//            hp = node.split(":");
//            nodes.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
//        }
//        return nodes;
//    }
//}