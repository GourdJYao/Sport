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
public class DrugsInfoTools {
	private static String url = "http://app2.sfda.gov.cn/datasearchp/index1.do?tableId=25&tableName=TABLE25&tableView=国产药品&Id=";

	public List<String> parseCertificatesInfo(int page) {
		String urlString = url + page;
		System.out.println("urlString:" + urlString);
		Document document = null;
		List<String> drugsInfoList = new ArrayList<String>();
		List<String> companyInfoList = new ArrayList<String>();
		List<String> gmpInfoList = new ArrayList<String>();
		List<String> advertisementInfoList = new ArrayList<String>();
		try {
			document = Jsoup.connect(urlString).timeout(60000).get();
			Elements elements = document.getAllElements();
			drugsInfoList = parseDragInfo(elements);
			companyInfoList = parseCompanyInfo(elements);
			parseGmpInfo(elements, gmpInfoList, advertisementInfoList);
			for (String gmpInfo : gmpInfoList) {
				System.out.println("gmpInfo:" + gmpInfo);
			}
			for (String advertisementInfo : advertisementInfoList) {
				System.out.println("advertisementInfo:" + advertisementInfo);
			}
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return drugsInfoList;
	}

	private List<String> parseGmpInfo(Elements elements, List<String> gmpList, List<String> advertisementInfoList) {
		List<String> list = new ArrayList<String>();
		if (elements.size() > 0) {
			int i = 0;
			for (Element elementTemp : elements) {
				if (elementTemp.tagName().equals("table") && elementTemp.attr("style") != null
						&& elementTemp.attr("style").equals("border:0px #989898 dotted;background:#eaeaea")
						&& elementTemp.className().equals("msgtab")) {
					if (i == 0) {
						for (Element element : elementTemp.getAllElements()) {
							if (element.hasAttr("href")) {
								String tempString = element.attr("href");
								gmpList.add(tempString);
								// System.out.println("tempString:" +
								// tempString);
							} else if (element.tagName().equals("td") && element.children().size() < 1) {
								String tempString = element.text();
								if (tempString.trim().length() > 0) {
									gmpList.add(tempString);
								}
								// System.out.println("tempString:" +
								// tempString);
							}
						}
						i++;
					} else if (i == 1) {
						i++;
					} else if (i == 2) {
						for (Element element : elementTemp.getAllElements()) {
							if (element.hasAttr("href")) {
								String tempString = element.attr("href");
								advertisementInfoList.add(tempString);
							} else if (element.tagName().equals("td") && element.children().size() < 1) {
								String tempString = element.text();
								if (tempString.trim().length() > 0) {
									advertisementInfoList.add(tempString);
								}
								// System.out.println("tempString:" +
								// tempString);
							}
						}
					}
				}
			}
		}
		return list;
	}

	private List<String> parseCompanyInfo(Elements elements) {
		List<String> list = new ArrayList<String>();
		if (elements.size() > 0) {
			for (Element elementTemp : elements) {
				if (elementTemp.tagName().equals("table") && elementTemp.attr("style") != null
						&& elementTemp.attr("style").equals("border:0px #989898 solid;background:#eaeaea")
						&& elementTemp.className().equals("msgtab")) {
					for (Element element : elementTemp.getAllElements()) {
						if (element.tagName().equals("th") || element.tagName().equals("td")) {
							String tempString = element.text();
							list.add(tempString);
							// System.out.println("tempString:" + tempString);
						}
					}
					break;
				}
			}
		}
		return list;
	}

	private List<String> parseDragInfo(Elements elements) {
		List<String> list = new ArrayList<String>();
		if (elements.size() > 0) {
			for (Element elementTemp : elements) {
				if (elementTemp.tagName().equals("table") && elementTemp.attr("style") != null
						&& elementTemp.attr("style").equals("background:#eaeaea")
						&& elementTemp.className().equals("msgtab")) {
					for (Element element : elementTemp.getAllElements()) {
						if (element.tagName().equals("th") || element.tagName().equals("td")) {
							String tempString = element.text();
							list.add(tempString);
							// System.out.println("tempString:" + tempString);
						}
					}
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		DrugsInfoTools drugsInfoTools = new DrugsInfoTools();
		drugsInfoTools.parseCertificatesInfo(57897);
	}
}
