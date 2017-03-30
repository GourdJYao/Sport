package com.yaojian.controller;

import java.util.HashMap;
import java.util.Map;

import com.yaojian.controller.request.BaseRequest;

public class UserOperator {
	public Map<String,Object> login(BaseRequest baseRequest){
		System.out.println("baseRequest:"+baseRequest.getMessagetype());
		Map<String,Object> resultMap=new HashMap<String,Object>();
		String username=null;
		String password=null;
		String token=null;
		return resultMap;
	}
}
