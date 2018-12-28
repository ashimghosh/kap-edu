package com.org.kap.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tab_user_master database table.
 * 
 */
@Entity
@Table(name="tab_user_master")
@NamedQuery(name="TabUserMaster.findAll", query="SELECT t FROM TabUserMaster t")
public class TabUserMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tum_id")
	@GeneratedValue
	private int tumId;

	private String password;

	@Column(name="ph_no")
	private int phNo;

	private int status;

	@Column(name="user_id")
	private String userId;

	@Column(name="user_type")
	private String userType;

	public TabUserMaster() {
	}

	public int getTumId() {
		return this.tumId;
	}

	public void setTumId(int tumId) {
		this.tumId = tumId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPhNo() {
		return this.phNo;
	}

	public void setPhNo(int phNo) {
		this.phNo = phNo;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}