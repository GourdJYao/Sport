package com.yaojian.model;

import java.sql.Date;

/**
 * 用户
 * 
 * @author GourdJYao
 *
 */
public class User {

	private Integer id;
	private String username;
	private String password;
	private String email;
	private Integer usertype;
	private String token;
	private Date tokentime = new java.sql.Date(new java.util.Date().getTime());
	private String headimageurl;
	private String nickname;
	private Integer bloodtype;
	private Date birthday;
	private String hobby;
	private String address;
	private Integer sex;
	private Date updatedate = new java.sql.Date(new java.util.Date().getTime());

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getHeadimageurl() {
		return headimageurl;
	}

	public void setHeadimageurl(String headimageurl) {
		this.headimageurl = headimageurl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getBloodtype() {
		return bloodtype;
	}

	public void setBloodtype(Integer bloodtype) {
		this.bloodtype = bloodtype;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getTokentime() {
		return tokentime;
	}

	public void setTokentime(Date tokentime) {
		this.tokentime = tokentime;
	}

}
