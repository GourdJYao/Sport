package com.yaojian.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTest {

	private static final String URL_STRING = "http://localhost:8080/yuelian/service";

	public static void main(String[] args) {
		URL url = null;
		HttpURLConnection connetion = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			url = new URL(URL_STRING);
			connetion = (HttpURLConnection) url.openConnection();
			connetion.setDoInput(true);
			connetion.setDoOutput(true);
			out = connetion.getOutputStream();
//			out.write(getRegisterBody().getBytes("UTF-8"));
			out.write(getLoginBody().getBytes("UTF-8"));
//			out.write(getUpdateUserBody().getBytes("UTF-8"));
//			out.write(getUpdateStudentBody().getBytes("UTF-8"));
//			out.write(getUpdateCoachBody().getBytes("UTF-8"));
			
			  
			
			in = connetion.getInputStream();
			int i = -1;
			String s = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf8")); 
			
			System.out.println("=====================" + br.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connetion != null) {
				connetion.disconnect();
				connetion = null;
			}
			try {
				if (out != null) {
					out.close();
					out = null;
				}
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String getRegisterBody() {
		String body = "{\"version\":\"v1.0\",\"messagetype\":\"REGISTER_REQ\",\"params\":{\"username\":\"yaojian1\",\"password\":\"yaojian1\",\"usertype\":1}}";
		return body;
	}
	private static String getLoginBody() {
		String body = "{\"version\":\"v1.0\",\"messagetype\":\"LOGIN_REQ\",\"params\":{\"username\":\"yaojian\",\"password\":\"yaojian\"}}";
		return body;
	}
	
	private static String getUpdateUserBody() {
		String body = "{\"version\":\"v1.0\",\"messagetype\":\"UPDATEUSER_REQ\",\"params\":{\"token\":\"PjprO6Tks/SFghwKLS0EKg==\",\"address\":\"重庆市巴南区红光大道8号\","
					+ "\"birthday\":\"1987-01-02 12:23:23\",\"bloodtype\":1,\"email\":\"yaojian@gdtm.com\",\"headimageurl\":\"http://img5.imgtn.bdimg.com/it/u=1017606633,46849118&fm=11&gp=0.jpg\""
					+ ",\"hobby\":\"我去\",\"nickname\":\"乱世英豪\",\"sex\":1}}";
		return body;
	}
	
	private static String getUpdateStudentBody() {
		String body = "{\"version\":\"v1.0\",\"messagetype\":\"UPDATESTUDENT_REQ\",\"params\":{\"token\":\"PjprO6Tks/SFghwKLS0EKg==\","
					+ "\"studentname\":\"GourdJYao\",\"studenttype\":1,\"studentidcard\":\"500109198902150110\","
					+ "\"studentidcardimg\":\"http://img3.imgtn.bdimg.com/it/u=1183223528,3058066243&fm=206&gp=0.jpg\","
					+ "\"studentcardno\":\"GX23121212a\",\"studentcardimg\":\"http://img3.imgtn.bdimg.com/it/u=1183223528,3058066243&fm=206&gp=0.jpg\","
					+ "\"studentdrivercardno\":\"渝A19312199\",\"studentdrivercardnoimg\":\"http://img3.imgtn.bdimg.com/it/u=1183223528,3058066243&fm=206&gp=0.jpg\"}}";
		return body;
	}
	
	private static String getUpdateCoachBody() {
		String body = "{\"version\":\"v1.0\",\"messagetype\":\"UPDATECOACH_REQ\",\"params\":{\"token\":\"PjprO6Tks/SFghwKLS0EKg==\","
					+ "\"coachname\":\"GourdJYao01\",\"isreview\":1,\"coachidcardnumber\":\"500109198902150110\","
					+ "\"coachcardimage\":\"http://img3.imgtn.bdimg.com/it/u=1183223528,3058066243&fm=206&gp=0.jpg\","
					+ "\"coachcardnumber\":\"GX23121212\",\"coachidcardimage\":\"http://img3.imgtn.bdimg.com/it/u=1183223528,3058066243&fm=206&gp=0.jpg\","
					+ "\"coachiddrivercarno\":\"渝A19312199\",\"coachiddrviercarimg\":\"http://img3.imgtn.bdimg.com/it/u=1183223528,3058066243&fm=206&gp=0.jpg\""
					+ ",\"reviewdesc\":\"资料齐全通过\",\"coachdesc\":\"还可以~\"}}";
		return body;
	}

}
