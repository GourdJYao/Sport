package com.yaojian.mapper;

import java.util.List;

import com.yaojian.model.GmpInfo;

public interface GmpInfoMapper{

	void save(GmpInfo gmpInfo);
	boolean update(GmpInfo gmpInfo);
	boolean delete(int id);
	GmpInfo findById(int id);
	GmpInfo findByGmpInfo(GmpInfo gmpInfo);
	List<GmpInfo> findAll();
}
