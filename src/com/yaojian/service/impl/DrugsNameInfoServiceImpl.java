package com.yaojian.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yaojian.mapper.DrugsNameInfoMapper;
import com.yaojian.model.DrugsNameInfo;
import com.yaojian.service.DrugsNameInfoService;



@Service
@Transactional  //此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class DrugsNameInfoServiceImpl implements DrugsNameInfoService {
	
	@Resource
	private DrugsNameInfoMapper mapper;

	public boolean delete(int id) {
		return mapper.delete(id);
	}

	public List<DrugsNameInfo> findAll() {
		List<DrugsNameInfo> findAllList = mapper.findAll();
		return findAllList;
	}

	public DrugsNameInfo findById(int id) {
		DrugsNameInfo drugsNameInfo = mapper.findById(id);
		return drugsNameInfo;
	}

	public void save(DrugsNameInfo drugsNameInfo) {
		DrugsNameInfo tempDrugsNameInfo = findByDrugsNameInfo(drugsNameInfo);
		if(tempDrugsNameInfo!=null&&tempDrugsNameInfo.getId()!=null){
			mapper.save(drugsNameInfo);
		}else{
			tempDrugsNameInfo.setUpdatedate(new Date().getTime());
			mapper.update(tempDrugsNameInfo);
		}
	}

	public boolean update(DrugsNameInfo drugsNameInfo) {
		return mapper.update(drugsNameInfo);
	}
	
	public DrugsNameInfo findByDrugsNameInfo(DrugsNameInfo drugsNameInfo) {
		return mapper.findByDrugsNameInfo(drugsNameInfo);
	}
}
