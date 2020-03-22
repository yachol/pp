package com.pp.utils;

import java.util.List;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

import redis.clients.jedis.Jedis;

public class UpdateRedis {
	public static void update(String province, String city, String pname, Integer id) {
		// 更新地区的
		Jedis jedis = new Jedis();
		jedis.select(2);
		jedis.sadd(province, id + "");
		if (!"".equals(city))
			jedis.sadd(province + city, id + "");
		// 更新名称
		JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
		List<SegToken> process = jiebaSegmenter.process(pname, SegMode.INDEX);
		for (SegToken segToken : process) {
			jedis.sadd(segToken.word, id + "");
		}
	}
}
