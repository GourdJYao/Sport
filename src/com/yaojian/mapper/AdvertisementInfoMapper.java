package com.yaojian.mapper;

import java.util.List;

import com.yaojian.model.AdvertisementInfo;

public interface AdvertisementInfoMapper{

	void save(AdvertisementInfo advertisementInfo);
	boolean update(AdvertisementInfo advertisementInfo);
	boolean delete(int id);
	AdvertisementInfo findById(int id);
	AdvertisementInfo findByAdvertisementInfo(AdvertisementInfo advertisementInfo);
	List<AdvertisementInfo> findAll();
}
