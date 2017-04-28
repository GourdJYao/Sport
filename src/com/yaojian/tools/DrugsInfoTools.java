package com.yaojian.tools;

import static org.hamcrest.CoreMatchers.containsString;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.test.context.ContextConfiguration;

import com.yaojian.model.CompanyInfo;
import com.yaojian.model.DrugsInfo;

@ContextConfiguration("/config/spring-common.xml")
public class DrugsInfoTools {
	private static String url = "http://app2.sfda.gov.cn/datasearchp/index1.do?tableId=25&tableName=TABLE25&tableView=国产药品&Id=";

	public List<Object> parseCertificatesInfo(int page) {
		String urlString = url + page;
		System.out.println("urlString:" + urlString);
		Document document = null;
		List<Object> resultList=new ArrayList<Object>();
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
			DrugsInfo drugsInfo;
			CompanyInfo companyInfo;
			if (drugsInfoList != null && drugsInfoList.size() > 0) {
				drugsInfo = getDrugsInfo(drugsInfoList, page);
				resultList.add(drugsInfo);
			}
			System.out.println("==============================================================");
			if (companyInfoList != null && companyInfoList.size() > 0) {
				companyInfo = getCompanyInfo(companyInfoList);
				resultList.add(companyInfo);
			}
			System.out.println("==============================================================");
			for (String gmpInfo : gmpInfoList) {
				System.out.println("gmpInfo:" + gmpInfo);
			}
			System.out.println("==============================================================");
			for (String advertisementInfo : advertisementInfoList) {
				System.out.println("advertisementInfo:" + advertisementInfo);
			}
			System.out.println("==============================================================");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	private CompanyInfo getCompanyInfo(List<String> companyInfoList) {
		String companyInfoString = "";
		CompanyInfo companyInfo = new CompanyInfo();
		for (int i = 0; i < companyInfoList.size(); i++) {
			companyInfoString = companyInfoList.get(i);
			System.out.println("companyInfo:" + companyInfoString);
			if (companyInfoString.contains("编号")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setBianhao(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("分类码")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setFenleima(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("省市")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setShengshi(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("企业名称")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setQiyemingcheng(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("法定代表人")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setFadingdaibiaoren(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("企业负责人")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setQiyefuzeren(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("注册地址")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setZhucedizhi(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("生产地址")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setShengchandizhi(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("生产范围")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setShechanfanwei(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("发证日期")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setFazhengriqi(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("有效期至")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setYouxiaoqizhi(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("发证机关")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setFazhengjiguan(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("签发人")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setQianfaren(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("日常监管机构")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setRichangjianguanjigou(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("日常监管人员")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setRichangjianguanrenyuan(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("社会信用代码/组织机构代码")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setShehuixinyongdaima(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("监督举报电话")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setJiandujubaodianhua(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("备注")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setBeizhu(companyInfoString);
				continue;
			}
			if (companyInfoString.contains("质量负责人")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setZiliangfuzeren(companyInfoString);
				break;
			}
		}
		return companyInfo;
	}

	private DrugsInfo getDrugsInfo(List<String> drugsInfoList, int serverid) {
		DrugsInfo drugsInfo = new DrugsInfo();
		String drugsInfoString = "";
		drugsInfo.setCfdaid(serverid);
		for (int i = 0; i < drugsInfoList.size(); i++) {
			drugsInfoString = drugsInfoList.get(i);
			System.out.println("drugsInfo:" + drugsInfoString);
			if (drugsInfoString.contains("批准文号")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setPizhuwenhao(drugsInfoString);
				continue;
			}
			if (drugsInfoString.contains("产品名称")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setDrugsname(drugsInfoString);
				continue;
			}
			if (drugsInfoString.contains("英文名称")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setYingwenmingchen(drugsInfoString);
				continue;
			}
			if (drugsInfoString.contains("商品名")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setShangpingming(drugsInfoString);
				continue;
			}
			if (drugsInfoString.contains("剂型")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setJixing(drugsInfoString);
				continue;
			}
			if (drugsInfoString.contains("规格")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setGuige(drugsInfoString);
				continue;
			}
			if (drugsInfoString.contains("生产单位")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setShengchangdanweiname(drugsInfoString);
				continue;
			}
			if (drugsInfoString.contains("生产地址")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setShengchandizhi(drugsInfoString);
				continue;
			}
			if (drugsInfoString.contains("产品类别")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setChanpinleixing(drugsInfoString);
				continue;
			}
			if (drugsInfoString.contains("批准日期")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setPizhunriqi(drugsInfoString);
				continue;
			}
			if (drugsInfoString.contains("原批准文号")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setYuanpizhuwenhao(drugsInfoString);
				continue;
			}
			if (drugsInfoString.contains("药品本位码")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setYaopinbenweima(drugsInfoString);
				continue;
			}
			if (drugsInfoString.contains("药品本位码")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setYaopinbenweima(drugsInfoString);
				continue;
			}
			if (drugsInfoString.contains("药品本位码备注")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setYaopinbenweimabeizhu(drugsInfoString);
				break;
			}
		}
		return drugsInfo;
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
							}
							// else if (element.tagName().equals("td") &&
							// element.children().size() < 1) {
							// String tempString = element.text();
							// if (tempString.trim().length() > 0) {
							// gmpList.add(tempString);
							// }
							// // System.out.println("tempString:" +
							// // tempString);
							// }
						}
						i++;
					} else if (i == 1) {
						i++;
					} else if (i == 2) {
						for (Element element : elementTemp.getAllElements()) {
							if (element.hasAttr("href")) {
								String tempString = element.attr("href");
								advertisementInfoList.add(tempString);
							}
							// else if (element.tagName().equals("td") &&
							// element.children().size() < 1) {
							// String tempString = element.text();
							// if (tempString.trim().length() > 0) {
							// advertisementInfoList.add(tempString);
							// }
							// // System.out.println("tempString:" +
							// // tempString);
							// }
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
}
