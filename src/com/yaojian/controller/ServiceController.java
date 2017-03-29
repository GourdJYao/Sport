package com.yaojian.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yaojian.service.UserService;
import com.yaojian.utils.ClazzOperator;
import com.yaojian.utils.MessageUtils;
import com.yaojian.utils.PropertiesUtils;
import com.yaojian.utils.ResponseUtils;

import net.sf.json.JSONObject;

@Controller
public class ServiceController {
	@Autowired
	private UserService userService;

	private static ResourceBundle serviceNameResource;

	static {
		serviceNameResource = PropertiesUtils.getServiceMethod();
	}



	@ResponseBody
	@RequestMapping("/ServiceManager")
	public Map<String, Object> service(HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
		JSONObject resultJSONObject = getRequestParamsString(request);
		if (resultJSONObject == null) {
			return ResponseUtils.getResponseMessage(MessageUtils.MESSAGEERROR_RSP_STRING, MessageUtils.MESSAGEERROR_MESSAGE_STRING,
					MessageUtils.VERSION_STRING, MessageUtils.MESSAGEERROR_CODE_STRING, null);
		} else {
			String messageType = resultJSONObject.getString(MessageUtils.MESSAGETYPE_PARAMS_STRING);
			if (messageType == null) {
				return ResponseUtils.getResponseMessage(MessageUtils.MESSAGEERROR_RSP_STRING, MessageUtils.MESSAGEERROR_MESSAGE_STRING,
						MessageUtils.VERSION_STRING, MessageUtils.MESSAGEERROR_CODE_STRING, null);
			} else {
				String servcieNameString = serviceNameResource.getString(messageType);
				if (servcieNameString == null || servcieNameString.trim().length() == 0) {
					return ResponseUtils.getResponseMessage(MessageUtils.MESSAGEERROR_RSP_STRING, MessageUtils.MESSAGEERROR_MESSAGE_STRING,
							MessageUtils.VERSION_STRING, MessageUtils.MESSAGEERROR_CODE_STRING, null);
				} else {
					String[] serviceNameArray = servcieNameString.split("\\|");
					Map<String, Object> resultMap = ClazzOperator.getExecutionMethod(serviceNameArray[1],
							serviceNameArray[2], resultJSONObject);
					return resultMap;
				}
			}
		}
	}

	private JSONObject getRequestParamsString(HttpServletRequest request) {
		// 取消对请求串的UTF-8编码，防止不同浏览器中发送请求时进行默认编码处理导致的转码失败
		JSONObject result = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream in = null;
		try {
			baos = new ByteArrayOutputStream();
			in = request.getInputStream();
			byte[] buffer = new byte[512];
			for (int len = 0; (len = in.read(buffer)) > 0;) {
				baos.write(buffer, 0, len);
			}
			String content = new String(baos.toByteArray(), MessageUtils.DEFAULT_ENCODING);
			result = JSONObject.fromObject(content);
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				baos = null;
				in = null;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		if (serviceNameResource != null) {
			System.out.println("MSG_LOGIN_REQ========" + serviceNameResource.getString("MSG_LOGIN_REQ"));
			String[] serviceNameArray = (serviceNameResource.getString("MSG_LOGIN_REQ")).split("\\|");
//			ClazzOperator.getExecutionMethod(serviceNameArray[1], serviceNameArray[2], "{json}");
		}
	}
}
