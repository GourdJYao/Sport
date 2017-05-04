package com.yaojian.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yaojian.mapper.AdvertisementInfoMapper;
import com.yaojian.mapper.CompanyInfoMapper;
import com.yaojian.mapper.DrugsInfoMapper;
import com.yaojian.mapper.GmpInfoMapper;
import com.yaojian.model.AdvertisementInfo;
import com.yaojian.model.CompanyInfo;
import com.yaojian.model.DrugsInfo;
import com.yaojian.model.GmpInfo;
import com.yaojian.tools.DrugsInfoTools;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/spring-common.xml")
public class DrugsInfoTest {
	@Autowired
	private DrugsInfoMapper drugsInfoMapper;
	@Autowired
	private CompanyInfoMapper companyInfoMapper;
	@Autowired
	private AdvertisementInfoMapper advertisementInfoMapper;
	@Autowired
	private GmpInfoMapper gmpInfoMapper;

	@Test
	public void testAdd() {
		DrugsInfoTools drugsInfoTools = new DrugsInfoTools();
		List<List<String>> drugsAllInfoList;
		for (int i = 3863; i <= 58199; i++) {
			drugsAllInfoList = drugsInfoTools.parseCertificatesInfo(i);
			if (drugsAllInfoList != null && drugsAllInfoList.size() > 0) {
				List<String> drugsInfoList = drugsAllInfoList.get(0);
				List<String> companyInfoList = drugsAllInfoList.get(1);
				List<String> gmpInfoList = drugsAllInfoList.get(2);
				List<String> advertisementInfoList = drugsAllInfoList.get(3);
				String advertisementIdList = "";
				String gmpIdList = "";
				Integer companyid = null;
				if (advertisementInfoList != null && advertisementInfoList.size() > 0) {
					Integer serverid = null;
					for (String advertisementUrl : advertisementInfoList) {
						serverid = Integer.parseInt(advertisementUrl.split("&Id=")[1]);
						AdvertisementInfo tempAdvertisement = advertisementInfoMapper
								.findByAdvertisementServerid(serverid);
						if (tempAdvertisement != null) {
							advertisementIdList+= tempAdvertisement.getAdvertiseid() + ",";
						} else {
							tempAdvertisement = drugsInfoTools.getAdvertisementInfoList(advertisementUrl, serverid);
							if (tempAdvertisement != null
									&& tempAdvertisement.getYaopingguanggaopizhuwenhao() != null) {
								advertisementInfoMapper.save(tempAdvertisement);
								advertisementIdList += tempAdvertisement.getAdvertiseid() + ",";
							}
						}
					}
				}
				if (gmpInfoList != null && gmpInfoList.size() > 0) {
					Integer serverid = null;
					for (String gmpUrl : gmpInfoList) {
						serverid = Integer.parseInt(gmpUrl.split("&Id=")[1]);
						GmpInfo tempGmpInfo = gmpInfoMapper.findByGmpInfoServerid(serverid);
						if (tempGmpInfo != null) {
							gmpIdList+= tempGmpInfo.getGmpid() + ",";
						} else {
							tempGmpInfo = drugsInfoTools.getGmpInfoList(gmpUrl, serverid);
							if (tempGmpInfo != null && tempGmpInfo.getZhengshubianhao() != null) {
								gmpInfoMapper.save(tempGmpInfo);
								gmpIdList += tempGmpInfo.getGmpid() + ",";
							}
						}
					}
				}
				if (companyInfoList != null && companyInfoList.size() > 0) {
					CompanyInfo companyInfo = drugsInfoTools.getCompanyInfo(companyInfoList);
					if (companyInfo != null && companyInfo.getBianhao() != null) {
						CompanyInfo tempCompanyInfo = companyInfoMapper.findByCompanyInfo(companyInfo);
						if (tempCompanyInfo == null || tempCompanyInfo.getBianhao() == null
								|| tempCompanyInfo.getBianhao().trim().length() == 0) {
							companyInfoMapper.save(companyInfo);
							companyid = companyInfo.getCompanyid();
						}else{
							companyid = tempCompanyInfo.getCompanyid();
						}
					}
				}
				if (drugsInfoList != null && drugsInfoList.size() > 0) {
					DrugsInfo drugsInfo = drugsInfoTools.getDrugsInfo(drugsInfoList, i);
					DrugsInfo tempDrugsInfo = drugsInfoMapper.findByDrugsInfo(drugsInfo);
					if (tempDrugsInfo == null) {
						drugsInfo.setAdvertisementlist(advertisementIdList);
						drugsInfo.setGmplist(gmpIdList);
						drugsInfo.setShengchandanweiid(companyid);
						drugsInfoMapper.save(drugsInfo);
					} else {
						tempDrugsInfo.setAdvertisementlist(advertisementIdList);
						tempDrugsInfo.setGmplist(gmpIdList);
						tempDrugsInfo.setShengchandanweiid(companyid);
						tempDrugsInfo.setUrl(drugsInfo.getUrl());
						drugsInfoMapper.update(tempDrugsInfo);
					}
				}
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
