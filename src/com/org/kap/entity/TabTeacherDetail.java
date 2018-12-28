package com.org.kap.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tab_teacher_details database table.
 * 
 */
@Entity
@Table(name="tab_teacher_details")
@NamedQuery(name="TabTeacherDetail.findAll", query="SELECT t FROM TabTeacherDetail t")
public class TabTeacherDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ttd_id")
	@GeneratedValue
	private String ttdId;

	@Column(name="account_balance")
	private int accountBalance;

	private String address;

	private String email;

	private int experience;

	@Column(name="inserted_by")
	private String insertedBy;

	@Column(name="inserted_on")
	private String insertedOn;

	private String name;

	private int pin;

	private String qualification;

	@Column(name="tcm_id")
	private int tcmId;

	/*@Column(name="tum_id")
	private int tumId;*/

	private TabUserMaster tabUserMaster;
	
	public TabTeacherDetail() {
	}

	public String getTtdId() {
		return this.ttdId;
	}

	public void setTtdId(String ttdId) {
		this.ttdId = ttdId;
	}

	public int getAccountBalance() {
		return this.accountBalance;
	}

	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getExperience() {
		return this.experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getInsertedBy() {
		return this.insertedBy;
	}

	public void setInsertedBy(String insertedBy) {
		this.insertedBy = insertedBy;
	}

	public String getInsertedOn() {
		return this.insertedOn;
	}

	public void setInsertedOn(String insertedOn) {
		this.insertedOn = insertedOn;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPin() {
		return this.pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public String getQualification() {
		return this.qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public int getTcmId() {
		return this.tcmId;
	}

	public void setTcmId(int tcmId) {
		this.tcmId = tcmId;
	}

/*	public int getTumId() {
		return this.tumId;
	}

	public void setTumId(int tumId) {
		this.tumId = tumId;
	}*/

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tum_id")
	public TabUserMaster getTabUserMaster() {
		return tabUserMaster;
	}

	public void setTabUserMaster(TabUserMaster tabUserMaster) {
		this.tabUserMaster = tabUserMaster;
	}
	
	

}