package com.yaojian.mapper;

import java.util.List;

import com.yaojian.model.DrugsInfo;

public interface DrugsInfoMapper{

	void save(DrugsInfo drugsInfo);
	boolean update(DrugsInfo drugsInfo);
	boolean delete(int id);
	DrugsInfo findById(int id);
	DrugsInfo findByDrugsInfo(DrugsInfo drugsInfo);
	List<DrugsInfo> findAll();
}
