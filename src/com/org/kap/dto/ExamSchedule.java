package com.org.kap.dto;

public class ExamSchedule {

	private Integer tesId;
	private String QuestionPaper;
	private String startDate;
	private String endDate;
	private String masks;
	private String totalMasks;
	private String duration;
	private String examScheduleCode;
	private int inserted_by;
	private String examcode;
	
	
	public String getExamcode() {
		return examcode;
	}
	public void setExamcode(String examcode2) {
		this.examcode = examcode2;
	}
	public int getInserted_by() {
		return inserted_by;
	}
	public void setInserted_by(int inserted_by) {
		this.inserted_by = inserted_by;
	}
	public Integer getTesId() {
		return tesId;
	}
	public void setTesId(Integer tesId) {
		this.tesId = tesId;
	}
	public String getQuestionPaper() {
		return QuestionPaper;
	}
	public void setQuestionPaper(String questionPaper) {
		QuestionPaper = questionPaper;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getMasks() {
		return masks;
	}
	public void setMasks(String masks) {
		this.masks = masks;
	}
	public String getTotalMasks() {
		return totalMasks;
	}
	public void setTotalMasks(String totalMasks) {
		this.totalMasks = totalMasks;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getExamScheduleCode() {
		return examScheduleCode;
	}
	public void setExamScheduleCode(String examScheduleCode) {
		this.examScheduleCode = examScheduleCode;
	}
	@Override
	public String toString() {
		return "ExamSchedule [tesId=" + tesId + ", QuestionPaper=" + QuestionPaper + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", Masks=" + masks + ", totalMasks=" + totalMasks + ", Duration=" + duration
				+ ", examScheduleCode=" + examScheduleCode + "]";
	}
	
	
}
