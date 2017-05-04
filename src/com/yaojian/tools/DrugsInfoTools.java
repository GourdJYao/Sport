package com.yaojian.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.test.context.ContextConfiguration;

import com.yaojian.model.AdvertisementInfo;
import com.yaojian.model.CompanyInfo;
import com.yaojian.model.DrugsInfo;
import com.yaojian.model.GmpInfo;

@ContextConfiguration("/config/spring-common.xml")
public class DrugsInfoTools {
	private static String url = "http://app2.sfda.gov.cn/datasearchp/index1.do?tableId=25&tableName=TABLE25&tableView=国产药品&Id=";

	public List<List<String>> parseCertificatesInfo(int page) {
		String urlString = url + page;
		System.out.println("urlString:" + urlString);
		Document document = null;
		int successCount = 0;
		List<List<String>> resultList = new ArrayList<List<String>>();
		List<String> drugsInfoList = new ArrayList<String>();
		List<String> companyInfoList = new ArrayList<String>();
		List<String> gmpInfoList = new ArrayList<String>();
		List<String> advertisementInfoList = new ArrayList<String>();
		do {
			try {
				document = Jsoup.connect(urlString).timeout(60000).get();
				Elements elements = document.getAllElements();
				drugsInfoList = parseDragInfo(elements);
				companyInfoList = parseCompanyInfo(elements);
				parseGmpInfo(elements, gmpInfoList, advertisementInfoList);
				resultList.add(drugsInfoList);
				resultList.add(companyInfoList);
				resultList.add(gmpInfoList);
				resultList.add(advertisementInfoList);
				// DrugsInfo drugsInfo;
				// CompanyInfo companyInfo;
				// if (drugsInfoList != null && drugsInfoList.size() > 0) {
				// drugsInfo = getDrugsInfo(drugsInfoList, page);
				// resultList.add(drugsInfo);
				// }
				// System.out.println("==============================================================");
				// if (companyInfoList != null && companyInfoList.size() > 0) {
				// companyInfo = getCompanyInfo(companyInfoList);
				// resultList.add(companyInfo);
				// }
				// System.out.println("==============================================================");
				// List<GmpInfo> gmpInfoArrayList = getGmpInfoList(gmpInfoList);
				// if (gmpInfoArrayList != null && gmpInfoArrayList.size() > 0)
				// {
				// for (GmpInfo gmpInfo : gmpInfoArrayList) {
				// System.out.println("gmpInfo.getServerid():" +
				// gmpInfo.getServerid()
				// + "++++++++gmpInfo.getZhengshubianhao():" +
				// gmpInfo.getZhengshubianhao());
				// }
				// resultList.add(gmpInfoArrayList);
				// }
				// System.out.println("==============================================================");
				// List<AdvertisementInfo> advertisementInfoArrayList =
				// getAdvertisementInfoList(advertisementInfoList);
				// if (advertisementInfoArrayList != null &&
				// advertisementInfoArrayList.size() > 0) {
				// for (AdvertisementInfo advertisementInfo :
				// advertisementInfoArrayList) {
				// System.out.println("advertisementInfo.getServerid():" +
				// advertisementInfo.getServerid()
				// +
				// "++++++++advertisementInfo.getYaopingguanggaopizhuwenhao():"
				// +
				// advertisementInfo.getYaopingguanggaopizhuwenhao()+"+++++++++++++++++++advertisementInfo.getGuanggaoneirong():"+advertisementInfo.getGuanggaoneirong());
				// }
				// resultList.add(advertisementInfoArrayList);
				// }
				// System.out.println("==============================================================");
				Thread.sleep(2000);
				break;
			} catch (Exception e) {
				e.printStackTrace();
				successCount++;
			}
		} while (successCount < 3);
		return resultList;
	}

	public AdvertisementInfo getAdvertisementInfoList(String urlStringTemp, Integer serverid) {
		String url = "http://app2.sfda.gov.cn";
		int successCount = 0;
		List<String> list = new ArrayList<String>();
		AdvertisementInfo advertisementInfo = null;
		do {
			try {
				Document document = Jsoup.connect(url + urlStringTemp).timeout(60000).get();
				Elements elements = document.getAllElements();
				list.clear();
				for (Element elementTemp : elements) {
					if (elementTemp.tagName().equals("table") && elementTemp.attr("style") != null
							&& elementTemp.attr("style").equals("background:#eaeaea")
							&& elementTemp.className().equals("msgtab")) {
						for (Element element : elementTemp.getAllElements()) {
							if ((element.tagName().equals("th") || element.tagName().equals("td"))
									&& element.children().size() < 1) {
								String tempString = element.text();
								list.add(tempString);
							} else if (element.hasAttr("href")) {
								String tempString = element.attr("href");
								list.add(tempString);
							}
						}
						break;
					}
				}
				if (list != null && list.size() > 0) {
					// String id = urlStringTemp.split("&Id=")[1];
					advertisementInfo = new AdvertisementInfo();
					String tempString = "";
					advertisementInfo.setServerid(serverid);
					advertisementInfo.setUrl(urlStringTemp);
					for (int i = 0; i < list.size(); i++) {
						tempString = list.get(i);
						if (tempString.trim().equals("药品广告批准文号")) {
							tempString = list.get(++i);
							advertisementInfo.setYaopingguanggaopizhuwenhao(tempString);
							continue;
						}
						if (tempString.trim().equals("单位名称")) {
							tempString = list.get(++i);
							advertisementInfo.setDanweimingchen(tempString);
							continue;
						}
						if (tempString.trim().equals("地址")) {
							tempString = list.get(++i);
							advertisementInfo.setDizhi(tempString);
							continue;
						}
						if (tempString.trim().equals("邮政编码")) {
							tempString = list.get(++i);
							advertisementInfo.setYouzhengbianma(tempString);
							continue;
						}
						if (tempString.trim().equals("通用名称")) {
							tempString = list.get(++i);
							advertisementInfo.setTongyongmingchen(tempString);
							continue;
						}
						if (tempString.trim().equals("商品名称")) {
							tempString = list.get(++i);
							advertisementInfo.setShangpingmingcheng(tempString);
							continue;
						}
						if (tempString.trim().equals("商标名称")) {
							tempString = list.get(++i);
							advertisementInfo.setShangbiaomingcheng(tempString);
							continue;
						}
						if (tempString.trim().equals("处方分类")) {
							tempString = list.get(++i);
							advertisementInfo.setChufangneixing(tempString);
							continue;
						}
						if (tempString.trim().equals("广告类别")) {
							tempString = list.get(++i);
							advertisementInfo.setGuanggaoleixing(tempString);
							continue;
						}
						if (tempString.trim().equals("时长")) {
							tempString = list.get(++i);
							advertisementInfo.setShichang(tempString);
							continue;
						}
						if (tempString.trim().equals("广告有效期")) {
							tempString = list.get(++i);
							advertisementInfo.setGuanggaoyouxiaoqi(tempString);
							continue;
						}
						if (tempString.trim().equals("广告发布内容")) {
							tempString = list.get(++i);
							advertisementInfo.setGuanggaoneirong(tempString);
							continue;
						}
						if (tempString.trim().equals("批准文号")) {
							tempString = list.get(++i);
							advertisementInfo.setPizhunweihao(tempString);
							continue;
						}
						if (tempString.trim().equals("备注")) {
							tempString = list.get(++i);
							advertisementInfo.setBeizhu(tempString);
							continue;
						}
					}
				}
				Thread.sleep(2000);
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
				successCount++;
			} catch (IOException e) {
				e.printStackTrace();
				successCount++;
			}
		} while (successCount < 3);

		return advertisementInfo;
	}

	public GmpInfo getGmpInfoList(String urlStringTemp, Integer serverid) {
		String url = "http://app2.sfda.gov.cn";
		int successCount = 0;
		List<String> list = new ArrayList<String>();
		GmpInfo gmpInfo = null;
		do {
			try {
				Document document = Jsoup.connect(url + urlStringTemp).timeout(60000).get();
				Elements elements = document.getAllElements();
				list.clear();
				for (Element elementTemp : elements) {
					if (elementTemp.tagName().equals("table") && elementTemp.attr("style") != null
							&& elementTemp.attr("style").equals("background:#eaeaea")
							&& elementTemp.className().equals("msgtab")) {
						for (Element element : elementTemp.getAllElements()) {
							if ((element.tagName().equals("th") || element.tagName().equals("td"))) {
								String tempString = element.text();
								list.add(tempString);
							}
						}
						break;
					}
				}
				if (list != null && list.size() > 0) {
					// String id = urlStringTemp.split("&Id=")[1];
					gmpInfo = new GmpInfo();
					String tempString = "";
					gmpInfo.setServerid(serverid);
					gmpInfo.setUrl(urlStringTemp);
					for (int i = 0; i < list.size(); i++) {
						tempString = list.get(i);
						if (tempString.trim().equals("省市")) {
							tempString = list.get(++i);
							gmpInfo.setShengfen(tempString);
							continue;
						}
						if (tempString.trim().equals("证书编号")) {
							tempString = list.get(++i);
							gmpInfo.setZhengshubianhao(tempString);
							continue;
						}
						if (tempString.trim().equals("企业名称")) {
							tempString = list.get(++i);
							gmpInfo.setGongsimingchen(tempString);
							continue;
						}
						if (tempString.trim().equals("地址")) {
							tempString = list.get(++i);
							gmpInfo.setDizhi(tempString);
							continue;
						}
						if (tempString.trim().equals("认证范围")) {
							tempString = list.get(++i);
							gmpInfo.setRengzhengfanwei(tempString);
							continue;
						}
						if (tempString.trim().equals("有效期截止日")) {
							tempString = list.get(++i);
							gmpInfo.setYouxiaojiezhiri(tempString);
							continue;
						}
						if (tempString.trim().equals("批准延续日期")) {
							tempString = list.get(++i);
							gmpInfo.setPizhuyanxuriqi(tempString);
							continue;
						}
						if (tempString.trim().equals("有效期延续至")) {
							tempString = list.get(++i);
							gmpInfo.setYouxiaoqiyanxuzhi(tempString);
							continue;
						}
						if (tempString.trim().equals("批准延续的认证范围")) {
							tempString = list.get(++i);
							gmpInfo.setPizhuyanxuderenzhengfanwei(tempString);
							continue;
						}
						if (tempString.trim().equals("认证GMP版本")) {
							tempString = list.get(++i);
							gmpInfo.setRenzhengmpbanben(tempString);
							continue;
						}
						if (tempString.trim().equals("备注")) {
							tempString = list.get(++i);
							gmpInfo.setBeizhu(tempString);
							continue;
						}
					}
				}
				Thread.sleep(2000);
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
				successCount++;
			} catch (IOException e) {
				e.printStackTrace();
				successCount++;
			}
		} while (successCount < 3);

		return gmpInfo;
	}

	public static void main(String[] args) {
		DrugsInfoTools drugsInfoTools = new DrugsInfoTools();
		drugsInfoTools.parseCertificatesInfo(57897);
	}

	public CompanyInfo getCompanyInfo(List<String> companyInfoList) {
		String companyInfoString = "";
		CompanyInfo companyInfo = new CompanyInfo();
		for (int i = 0; i < companyInfoList.size(); i++) {
			companyInfoString = companyInfoList.get(i);
			if (companyInfoString.equals("编号")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setBianhao(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("分类码")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setFenleima(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("省市")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setShengshi(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("企业名称")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setQiyemingcheng(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("法定代表人")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setFadingdaibiaoren(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("企业负责人")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setQiyefuzeren(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("注册地址")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setZhucedizhi(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("生产地址")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setShengchandizhi(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("生产范围")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setShechanfanwei(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("发证日期")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setFazhengriqi(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("有效期至")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setYouxiaoqizhi(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("发证机关")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setFazhengjiguan(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("签发人")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setQianfaren(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("日常监管机构")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setRichangjianguanjigou(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("日常监管人员")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setRichangjianguanrenyuan(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("社会信用代码/组织机构代码")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setShehuixinyongdaima(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("监督举报电话")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setJiandujubaodianhua(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("备注")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setBeizhu(companyInfoString);
				continue;
			}
			if (companyInfoString.equals("质量负责人")) {
				companyInfoString = companyInfoList.get(++i);
				companyInfo.setZiliangfuzeren(companyInfoString);
				break;
			}
		}
		return companyInfo;
	}

	public DrugsInfo getDrugsInfo(List<String> drugsInfoList, int serverid) {
		DrugsInfo drugsInfo = new DrugsInfo();
		String drugsInfoString = "";
		drugsInfo.setCfdaid(serverid);
		drugsInfo.setUrl(url+serverid);
		for (int i = 0; i < drugsInfoList.size(); i++) {
			drugsInfoString = drugsInfoList.get(i);
			System.out.println("drugsInfo:" + drugsInfoString);
			if (drugsInfoString.equals("批准文号")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setPizhuwenhao(drugsInfoString);
				continue;
			}
			if (drugsInfoString.equals("产品名称")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setDrugsname(drugsInfoString);
				continue;
			}
			if (drugsInfoString.equals("英文名称")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setYingwenmingchen(drugsInfoString);
				continue;
			}
			if (drugsInfoString.equals("商品名")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setShangpingming(drugsInfoString);
				continue;
			}
			if (drugsInfoString.equals("剂型")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setJixing(drugsInfoString);
				continue;
			}
			if (drugsInfoString.equals("规格")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setGuige(drugsInfoString);
				continue;
			}
			if (drugsInfoString.equals("生产单位")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setShengchangdanweiname(drugsInfoString);
				continue;
			}
			if (drugsInfoString.equals("生产地址")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setShengchandizhi(drugsInfoString);
				continue;
			}
			if (drugsInfoString.equals("产品类别")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setChanpinleixing(drugsInfoString);
				continue;
			}
			if (drugsInfoString.equals("批准日期")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setPizhunriqi(drugsInfoString);
				continue;
			}
			if (drugsInfoString.equals("原批准文号")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setYuanpizhuwenhao(drugsInfoString);
				continue;
			}
			if (drugsInfoString.equals("药品本位码")) {
				drugsInfoString = drugsInfoList.get(++i);
				drugsInfo.setYaopinbenweima(drugsInfoString);
				continue;
			}
			if (drugsInfoString.equals("药品本位码备注")) {
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
