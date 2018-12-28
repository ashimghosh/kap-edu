package com.org.kap.dto;

public class Exam_Result {
	private int totalmarks;
	private int passmarks;
	private  int obtainedmarks;
	private int studentid;
	private String papercode;
	private String Remarks;
	private String resultXml;
	
	public String getResultXml() {
		return resultXml;
	}
	public void setResultXml(String resultXml) {
		this.resultXml = resultXml;
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
	public int getStudentid() {
		return studentid;
	}
	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}
	public String getPapercode() {
		return papercode;
	}
	public void setPapercode(String papercode) {
		this.papercode = papercode;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	@Override
	public String toString() {
		return "Exam_Result [totalmarks=" + totalmarks + ", passmarks=" + passmarks + ", obtainedmarks=" + obtainedmarks
				+ ", studentid=" + studentid + ", papercode=" + papercode + ", Remarks=" + Remarks + "]";
	}
	

}
