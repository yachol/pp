package com.pp.config;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.pp.utils.RedisCache;

import redis.clients.jedis.JedisPoolConfig;
@Configuration
public class RedisConfig {
//	@Value("${JedisPoolConfig.maxTotal}")
//	private Integer maxTotal;
//
//	@Value("${JedisPoolConfig.maxIdle}")
//	private Integer maxIdle;
//
//	@Value("${JedisPoolConfig.maxWaitMillis}")
//	private Long maxWaitMillis;
//
//	@Value("${JedisPoolConfig.testOnBorrow}")
//	private boolean testOnBorrow;
//
//	@Value("${JedisConnectionFactory.hostName}")
//	private String hostName;
//
//	@Value("${JedisConnectionFactory.port}")
//	private Integer port;
//
//	@Value("${JedisConnectionFactory.timeOut}")
//	private Integer timeOut;
//
//	@Value("${JedisConnectionFactory.usePool}")
//	private boolean usePool;
//	@Bean
//	public JedisPoolConfig pool() {
//		JedisPoolConfig jpc = new JedisPoolConfig();
//		jpc.setMaxIdle(maxIdle);
//		jpc.setMaxTotal(maxTotal);
//		jpc.setMaxWaitMillis(maxWaitMillis);
//		jpc.setTestOnBorrow(testOnBorrow);
//		return jpc;
//	}
//	@Bean
//	public JedisConnectionFactory connectionFactory(JedisPoolConfig pool){
//		JedisConnectionFactory factory = new JedisConnectionFactory();
//		factory.setPoolConfig(pool);
//		factory.setHostName(hostName);
//		factory.setPort(port);
//		factory.setTimeout(timeOut);
//		factory.setUsePool(usePool);
//		return factory;
//	}
//	@Bean(name = "linda")
//	public RedisTemplate redisTemplate(JedisConnectionFactory connectionFactory) {
//		RedisTemplate rt=new RedisTemplate();
//		rt.setConnectionFactory(connectionFactory);
//		rt.setEnableTransactionSupport(usePool);
//		return rt;
//	}
	
	@Autowired
	@Qualifier("sessionRedisTemplate")
	private RedisTemplate redisTemplate;
	@Bean
	public SimpleCacheManager cachemgr() {
		RedisCache rc = new RedisCache();
		rc.setRedisTemplate(redisTemplate);
		rc.setName("cache0");
		HashSet<RedisCache> set = new HashSet<RedisCache>();
		set.add(rc);
		SimpleCacheManager manager = new SimpleCacheManager();
		manager.setCaches(set);
		return manager;
	}
}
