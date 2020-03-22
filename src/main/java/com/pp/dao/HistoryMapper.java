package com.pp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pp.entity.History;
import tk.mybatis.mapper.common.Mapper;

public interface HistoryMapper extends Mapper<History> {
	/**
	 * 	获取我拍的商品的最高竞拍记录,按时间排序
	 */
	public List<History> getMyPPmax(@Param("uid")Integer uid);
	
}