package com.yaojian.tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.yaojian.mapper.DrugsNameInfoMapper;
import com.yaojian.model.DrugsNameInfo;

@ContextConfiguration("/config/spring-common.xml")
public class DrugsNameGrabTools {
	@Autowired
	private DrugsNameInfoMapper drugsNameInfoMapper;
	private static String url = "http://app2.sfda.gov.cn/datasearchp/gzcxSearch.do?page=";
	private List<String> infoList = new ArrayList<String>();

	public List<DrugsNameInfo> parseCertificatesInfo(int page) {
		String urlString = url + page;
		System.out.println("urlString:" + urlString);
		Document document = null;
		List<DrugsNameInfo> bbsPathList = new ArrayList<DrugsNameInfo>();
		try {
			document = Jsoup.connect(urlString).timeout(60000).get();
			Elements elements = document.getAllElements();
			infoList.clear();
			bbsPathList = parseSpan(elements);
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bbsPathList;
	}

	private List<DrugsNameInfo> parseSpan(Elements elements) {
		List<DrugsNameInfo> list = new ArrayList<DrugsNameInfo>();
		if (elements.size() > 0) {
			DrugsNameInfo drugsNameInfo = null;
			for (Element elementTemp : elements) {
				if (elementTemp.tagName().equals("td") && elementTemp.attr("style") != null
						&& elementTemp.attr("style").contains("padding-left:15px;color:#000000;font-size:12px")) {
					if (elementTemp.text().trim().length() == 0) {
						continue;
					}
					drugsNameInfo = new DrugsNameInfo();
					drugsNameInfo.setDrugsname(elementTemp.text());
					drugsNameInfo.setUpdatedate(System.currentTimeMillis());
					list.add(drugsNameInfo);
				}
			}
		}
		return list;
	}
}
