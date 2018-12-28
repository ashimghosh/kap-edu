package com.org.kap.dto;

import java.math.BigInteger;

public class Userdata {

	private Integer tumId;
	private String userId;
	private String password;
	private String userType;
	private String phoneNo;
	private Integer status;
	private String userName;
	private int userlogintype;
	

	public int getUserlogintype() {
		return userlogintype;
	}

	public void setUserlogintype(int userlogintype) {
		this.userlogintype = userlogintype;
	}

	public Integer getTumId() {
		return tumId;
	}

	public void setTumId(Integer tumId) {
		this.tumId = tumId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Userdata [tumId=" + tumId + ", userId=" + userId + ", password=" + password + ", userType=" + userType
				+ ", phoneNo=" + phoneNo + ", status=" + status + "]";
	}

}
