package com.yaojian.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yaojian.mapper.DrugsNameInfoMapper;
import com.yaojian.model.DrugsNameInfo;
import com.yaojian.tools.DrugsNameGrabTools;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/spring-common.xml")
public class DrugsNameInfoTest {
	@Autowired
	private DrugsNameInfoMapper drugsNameInfoMapper;

	@Test
	public void testAdd() {
		DrugsNameGrabTools drugsNameGrabTools=new DrugsNameGrabTools();
		List<DrugsNameInfo> drugsNameInfoList;
		for (int i = 1; i < 1244; i++) {
			drugsNameInfoList = drugsNameGrabTools.parseCertificatesInfo(i);
			if (drugsNameInfoList != null && drugsNameInfoList.size() > 0) {
				for (DrugsNameInfo tempDrugsNameInfo : drugsNameInfoList) {
					DrugsNameInfo whartDrugsNameInfo = drugsNameInfoMapper.findByDrugsNameInfo(tempDrugsNameInfo);
					if (whartDrugsNameInfo == null || whartDrugsNameInfo.getId() == null) {
						drugsNameInfoMapper.save(tempDrugsNameInfo);
					} else {
						whartDrugsNameInfo.setUpdatedate(new Date().getTime());
						drugsNameInfoMapper.update(whartDrugsNameInfo);
					}
				}
			}
		}

	}
}
