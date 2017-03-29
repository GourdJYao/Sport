package com.yaojian.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
	
	private static final String VERSION_PARAMS_STRING = "version";
	
	private static final String VERSION_STRING = "v1.0";
	
	private static final String REQUEST_PARAMS_STRING = "_REQ";
	
	private static final String RESPONSE_PARAMS_STRING = "_RSP";
	
	private static final String MESSAGETYPE_PARAMS_STRING = "messagetype";
	
	private static final String MESSAGEERROR_RSP_STRING = "MESSAGE_ERROR_RSP";
	
	private static final String RESULTCODE_PARAMS_STRING = "resultcode";
	
	private static final String RESULT_PARAMS_STRING = "result";
	
	private static final String RESULTMESSAGE_PARAMS_STRING = "resultmessage";
	
	public static Map<String, Object> getResponseMessage(String messageTypeResponse, String resultMessage,
			String versionString, String resultCode, Map<String, Object> resultBody) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (versionString == null) {
			resultMap.put(VERSION_PARAMS_STRING, VERSION_STRING);
		} else {
			resultMap.put(VERSION_PARAMS_STRING, versionString);
		}
		if (messageTypeResponse != null) {
			if (messageTypeResponse.contains(REQUEST_PARAMS_STRING)) {
				messageTypeResponse = messageTypeResponse.replaceAll(REQUEST_PARAMS_STRING, RESPONSE_PARAMS_STRING);
			} else {
				messageTypeResponse += RESPONSE_PARAMS_STRING;
			}
			resultMap.put(MESSAGETYPE_PARAMS_STRING, messageTypeResponse);
		} else {
			resultMap.put(MESSAGETYPE_PARAMS_STRING, MESSAGEERROR_RSP_STRING);
		}
		resultMap.put(RESULTCODE_PARAMS_STRING, resultCode);
		resultMap.put(RESULTMESSAGE_PARAMS_STRING, resultMessage);
		if (resultBody != null) {
			resultMap.put(RESULT_PARAMS_STRING, resultBody);
		}
		return resultMap;
	}
}
