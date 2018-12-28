package com.org.kap.dto;

public class User_Recharge {
	private String username;
	private int rechargeAmt;
	private int rechargedBy;
	private int userId;
	private String mobile;
	private String rechargetype;
	private String rechargemode;
	private int usertype;
	
	
	
	public String getRechargetype() {
		return rechargetype;
	}
	public void setRechargetype(String rechargetype) {
		this.rechargetype = rechargetype;
	}
	public String getRechargemode() {
		return rechargemode;
	}
	public void setRechargemode(String rechargemode) {
		this.rechargemode = rechargemode;
	}
	
	public int getUsertype() {
		return usertype;
	}
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return "User_Recharge [username=" + username + ", rechargeAmt=" + rechargeAmt + ", rechargedBy=" + rechargedBy
				+ ", userId=" + userId + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getRechargeAmt() {
		return rechargeAmt;
	}
	public void setRechargeAmt(int rechargeAmt) {
		this.rechargeAmt = rechargeAmt;
	}
	public int getRechargedBy() {
		return rechargedBy;
	}
	public void setRechargedBy(int rechargedBy) {
		this.rechargedBy = rechargedBy;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	

}
