package com.yaojian.utils;

public class MessageUtils {
	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final String REQUEST_PARAMS_STRING = "_REQ";
	public static final String RESPONSE_PARAMS_STRING = "_RSP";
	public static final String MESSAGETYPE_PARAMS_STRING = "messagetype";
	public static final String VERSION_PARAMS_STRING = "version";
	public static final String RESULTCODE_PARAMS_STRING = "resultcode";
	public static final String RESULT_PARAMS_STRING = "result";
	public static final String PARAMS_STRING = "params";
	public static final String RESULTMESSAGE_PARAMS_STRING = "resultmessage";
	public static final String VERSION_STRING = "v1.0";
	public static final String MESSAGEERROR_RSP_STRING = "MESSAGE_ERROR_RSP";
	public static final String MESSAGEERROR_MESSAGE_STRING = "消息格式错误~";
	// 消息格式错误
	public static final String MESSAGEERROR_CODE_STRING = "0x000001";
	// 接口访问成功
	public static final String MESSAGEERROR_SUCCESS_CODE_STRING = "0x000000";
	// 用户存在
	public static final String MESSAGEERROR_USEREXISTS_CODE_STRING = "0x100001";
	// 未注册
	public static final String MESSAGEERROR_USERLONIN_CODE_STRING = "0x100002";
	// 登录失效
	public static final String MESSAGEERROR_USERLONIN_TIMEOUT_CODE_STRING = "0x100003";

	public static final String REGISTER_PARAMS_STRING = "REGISTER_REQ";
	public static final String LOGIN_PARAMS_STRING = "LOGIN_REQ";
	public static final String UPDATEUSER_PARAMS_STRING = "UPDATEUSER_REQ";
	public static final String UPDATESTUDENT_PARAMS_STRING = "UPDATESTUDENT_REQ";
	public static final String UPDATECOACH_PARAMS_STRING = "UPDATECOACH_REQ";
}
