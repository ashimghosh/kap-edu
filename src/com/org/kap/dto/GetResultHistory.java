package com.org.kap.dto;

public class GetResultHistory {
	private int resultId;
	private String examcode;
	private int studentId;
	private int totalmarks;
	private int studentcount;
	private int passmarks;
	private int obtainedmarks;
	private String remarks;
	private String completedDate;
	private String resultXml;
	
	
	public int getStudentcount() {
		return studentcount;
	}
	public void setStudentcount(int studentcount) {
		this.studentcount = studentcount;
	}
	public int getResultId() {
		return resultId;
	}
	public void setResultId(int resultId) {
		this.resultId = resultId;
	}
	public String getExamcode() {
		return examcode;
	}
	public void setExamcode(String examcode) {
		this.examcode = examcode;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getTotalmarks() {
		return totalmarks;
	}
	public void setTotalmarks(int totalmarks) {
		this.totalmarks = totalmarks;
	}
	public int getPassmarks() {
		return passmarks;
	}
	public void setPassmarks(int passmarks) {
		this.passmarks = passmarks;
	}
	public int getObtainedmarks() {
		return obtainedmarks;
	}
	public void setObtainedmarks(int obtainedmarks) {
		this.obtainedmarks = obtainedmarks;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}
	public String getResultXml() {
		return resultXml;
	}
	public void setResultXml(String resultXml) {
		this.resultXml = resultXml;
	}
	
	
	

}
