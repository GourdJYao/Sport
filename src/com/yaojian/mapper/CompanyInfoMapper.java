package com.yaojian.mapper;

import java.util.List;

import com.yaojian.model.CompanyInfo;

public interface CompanyInfoMapper{

	void save(CompanyInfo companyInfo);
	boolean update(CompanyInfo companyInfo);
	boolean delete(int id);
	CompanyInfo findById(int id);
	CompanyInfo findByCompanyInfo(CompanyInfo companyInfo);
	List<CompanyInfo> findAll();
}
