package com.org.kap.dto;

public class GetQuestionList {
	private int questionId;
	private String papercodeName;
	private String paperName;
	private String questionxml;
	private String language;
	private int insertedBy;
	
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getPapercodeName() {
		return papercodeName;
	}
	public void setPapercodeName(String papercodeName) {
		this.papercodeName = papercodeName;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public String getQuestionxml() {
		return questionxml;
	}
	public void setQuestionxml(String questionxml) {
		this.questionxml = questionxml;
	}
	public int getInsertedBy() {
		return insertedBy;
	}
	public void setInsertedBy(int insertedBy) {
		this.insertedBy = insertedBy;
	}
	
	

}
