package com.yaojian.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.yaojian.controller.request.BaseRequest;

import net.sf.json.JSONObject;

public class ClazzOperator {
	
	public static Map<String,Object> getExecutionMethod(String className,String methodName,BaseRequest baseRequest){
		try {
            Class clazz = Class.forName(className);
            Method method=clazz.getDeclaredMethod(methodName, BaseRequest.class);
            return (Map<String,Object>)method.invoke(clazz.newInstance(), baseRequest);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
    }
}
