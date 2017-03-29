package com.yaojian.utils;

import java.util.ResourceBundle;

public class PropertiesUtils {
	public static ResourceBundle getServiceMethod() {
		ResourceBundle resource=null;
		try {
			// 读取属性文件a.properties
			resource = ResourceBundle.getBundle("serviceconfig");
		} catch (Exception e) {
			System.out.println(e);
		} 
		return resource;
	}
}
