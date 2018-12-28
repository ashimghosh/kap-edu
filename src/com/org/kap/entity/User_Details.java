package com.org.kap.entity;

public class User_Details {
	
	private String usertype;
	private String fullname;
	private String gender;
	private String mobno;
	private String email;
	private String dob;
	private String password;
	private String address;
	private String pin;
	private int logintype;
	private int balance_amount;
	private int registered_by;
	
	public int getLogintype() {
		return logintype;
	}
	public void setLogintype(int logintype) {
		this.logintype = logintype;
	}
	public int getBalance_amount() {
		return balance_amount;
	}
	public void setBalance_amount(int balance_amount) {
		this.balance_amount = balance_amount;
	}
	public int getRegistered_by() {
		return registered_by;
	}
	public void setRegistered_by(int registered_by) {
		this.registered_by = registered_by;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobno() {
		return mobno;
	}
	public void setMobno(String mobno) {
		this.mobno = mobno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	
	
	@Override
	public String toString() {
		return "User_Details [usertype=" + usertype + ", fullname=" + fullname
				+ ", gender=" + gender + ", mobno=" + mobno + ", email="
				+ email + ", dob=" + dob + ", password=" + password
				+ ", address=" + address + ", pin=" + pin + ", logintype="
				+ logintype + ", balance_amount=" + balance_amount
				+ ", registered_by=" + registered_by + "]";
	}

}
