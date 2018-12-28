package com.org.kap.dto;

import java.sql.Timestamp;


public class TabQuestionMaster {

	private int tqmId;
	private int insertedBy;
	private Timestamp insertedOn;
	private String questionAnswerXml;
	private String questionPaperCode;
	private String questionPaperName;
	private int courseid;
	private String courseName;
	private String language;
	
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getCourseid() {
		return courseid;
	}

	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public TabQuestionMaster() {
	}

	public int getTqmId() {
		return this.tqmId;
	}

	public void setTqmId(int tqmId) {
		this.tqmId = tqmId;
	}

	public int getInsertedBy() {
		return this.insertedBy;
	}

	public void setInsertedBy(int insertedBy) {
		this.insertedBy = insertedBy;
	}

	public Timestamp getInsertedOn() {
		return this.insertedOn;
	}

	public void setInsertedOn(Timestamp insertedOn) {
		this.insertedOn = insertedOn;
	}

	public String getQuestionAnswerXml() {
		return this.questionAnswerXml;
	}

	public void setQuestionAnswerXml(String questionAnswerXml) {
		this.questionAnswerXml = questionAnswerXml;
	}

	public String getQuestionPaperCode() {
		return this.questionPaperCode;
	}

	public void setQuestionPaperCode(String questionPaperCode) {
		this.questionPaperCode = questionPaperCode;
	}

	public String getQuestionPaperName() {
		return this.questionPaperName;
	}

	public void setQuestionPaperName(String questionPaperName) {
		this.questionPaperName = questionPaperName;
	}

	@Override
	public String toString() {
		return "TabQuestionMaster [tqmId=" + tqmId + ", insertedBy=" + insertedBy + ", insertedOn=" + insertedOn
				+ ", questionAnswerXml=" + questionAnswerXml + ", questionPaperCode=" + questionPaperCode
				+ ", questionPaperName=" + questionPaperName + ", courseName=" + courseName + "]";
	}

	
	
	

}