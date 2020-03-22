package com.pp.utils;


import java.io.Serializable;
import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.SerializationUtils;

public class RedisCache implements Cache {
	private RedisTemplate<String, Object> redisTemplate;
	private String name;

	@Override
	public void clear() {
		redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.select(4);
				connection.flushDb();
				return "ok";
			}
		});
		System.out.println("--------清空缓存--------");
	}

	@Override
	public void evict(Object key) {
		final String keyf = key.toString();
		redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.select(4);
				return connection.del(keyf.getBytes());
			}
		});
		System.out.println("--------删除缓存--------");
		System.out.println("key : " + keyf);
	}

	@Override
	public ValueWrapper get(Object key) {
		final String keyf = key.toString();
		Object obj = redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.select(4);
				byte[] keyb = keyf.getBytes();
				byte[] valueb = connection.get(keyb);
				if (valueb == null) {
					return null;
				}
				return SerializationUtils.deserialize(valueb);
				// return JSONArray.parseArray(new String(valueb));
			}
		});
		System.out.println("--------获取缓存--------");
		System.out.println("key : " + keyf);
		System.out.println("val : " + (obj != null ? MyStrUtils.substr(obj.toString(), 100) + " ~ " : null));
		return (obj != null ? new SimpleValueWrapper(obj) : null);
	}

	@Override
	public void put(Object key, Object value) {
		final String keyf = key.toString();
		final Object valuef = value;
		final long liveTime = 86400;
		redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.select(4);
				byte[] keyb = keyf.getBytes();
				byte[] valueb = SerializationUtils.serialize((Serializable) valuef);
				// byte[] valueb = JSON.toJSONString(valuef).getBytes();
				connection.set(keyb, valueb);
				if (liveTime > 0) {
					connection.expire(keyb, liveTime);
				}
				return 1L;
			}
		});
		System.out.println("--------加入缓存--------");
		System.out.println("key : " + keyf);
		System.out.println("val : " + (valuef != null ? MyStrUtils.substr(valuef.toString(), 100) + " ~ " : null));
	}

	@Override
	public <T> T get(Object arg0, Class<T> arg1) {
		return null;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Object getNativeCache() {
		return this.redisTemplate;
	}

	@Override
	public ValueWrapper putIfAbsent(Object arg0, Object arg1) {
		return null;
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public <T> T get(Object arg0, Callable<T> arg1) {
		// TODO Auto-generated method stub
		return null;
	}
}
