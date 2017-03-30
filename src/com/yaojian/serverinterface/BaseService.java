package com.yaojian.serverinterface;

import java.util.HashMap;

public  class BaseService {
	protected static final String RESPONSE_STRING = "_RSP";
	protected static final String REQUEST_STRING = "_REQ";
	protected static final String MESSAGE_TYPE_PARAMS_STRING = "message_type";
	protected static final String VERSION_PARAMS_STRING = "version";
	protected static final String RESULT_PARAMS_STRING = "result";
	protected static final String VERSION_STRING = "v1.0";
	protected HashMap<String, Object> resultMap = new HashMap<String, Object>();
	

	public HashMap<String, Object> getResposeBody(String messageType,
			HashMap<String, Object> result) {
		if (messageType.contains(REQUEST_STRING)) {
			messageType = messageType.replaceAll(REQUEST_STRING,
					RESPONSE_STRING);
		} else {
			messageType += RESPONSE_STRING;
		}
		resultMap.put(MESSAGE_TYPE_PARAMS_STRING, messageType);
		resultMap.put(VERSION_PARAMS_STRING, VERSION_STRING);
		if (result != null) {
			resultMap.put(RESULT_PARAMS_STRING, result);
		}
		return resultMap;
	}

}
