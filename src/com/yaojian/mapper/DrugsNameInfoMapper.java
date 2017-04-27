package com.yaojian.mapper;

import java.util.List;

import com.yaojian.model.DrugsNameInfo;

public interface DrugsNameInfoMapper {

	void save(DrugsNameInfo drugsNameInfo);

	boolean update(DrugsNameInfo drugsNameInfo);

	boolean delete(int id);

	DrugsNameInfo findById(int id);

	DrugsNameInfo findByDrugsNameInfo(DrugsNameInfo drugsNameInfo);

	List<DrugsNameInfo> findAll();
}
