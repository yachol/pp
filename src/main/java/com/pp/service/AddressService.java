package com.pp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pp.entity.Address;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.pp.dao.AddressMapper;

@Transactional
@Service
public class AddressService {
	@Autowired
	private AddressMapper addressMapper;

	public AddressMapper getAddressMapper() {
		return addressMapper;
	}

	public String addAddress(Address address, Integer uid) {
		if ("是".equals(address.getIsdefault())) {
			Example example = new Example(Address.class);
			Criteria criteria = example.createCriteria();
			criteria.andCondition("uid=", uid);
			criteria.andCondition("isdefault=", "是");
			List<Address> list = addressMapper.selectByExample(example);
			if (list != null && list.size() != 0) {
				Address address2 = list.get(0);
				address2.setIsdefault("否");
				addressMapper.updateByPrimaryKeySelective(address2);
			}
		}
		Example example = new Example(Address.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("uid=", uid);
		int countAdress = addressMapper.selectCountByExample(example);
		if (countAdress == 5)
			return "最多5条收货地址";
		return addressMapper.insertSelective(address) > 0 ? "success" : "false";
	}

	public List<Address> showAddress(Integer uid) {
		Example example = new Example(Address.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("uid=", uid);
		return addressMapper.selectByExample(example);
	}

	public Address gteAddress(Integer id) {
		return addressMapper.selectByPrimaryKey(id);
	}

	public String delateAddress(Integer aid) {
		return addressMapper.deleteByPrimaryKey(aid) > 0 ? "删除成功" : "删除失败";
	}
}