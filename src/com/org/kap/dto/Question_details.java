package com.org.kap.dto;

public class Question_details {
	private int question_id;
	private int noofquestion;
	private int inserted_by;
	private String question_paper_code;
	private String questionPaperName;
	private String question_answer_xml;
	private String end_date;
	private int total_marks;
	private int pass_marks;
	private int duration;
	
	
	public int getNoofquestion() {
		return noofquestion;
	}
	public void setNoofquestion(int noofquestion) {
		this.noofquestion = noofquestion;
	}
	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	public int getInserted_by() {
		return inserted_by;
	}
	public void setInserted_by(int inserted_by) {
		this.inserted_by = inserted_by;
	}
	public String getQuestion_paper_code() {
		return question_paper_code;
	}
	public void setQuestion_paper_code(String question_paper_code) {
		this.question_paper_code = question_paper_code;
	}
	public String getQuestionPaperName() {
		return questionPaperName;
	}
	public void setQuestionPaperName(String questionPaperName) {
		this.questionPaperName = questionPaperName;
	}
	public String getQuestion_answer_xml() {
		return question_answer_xml;
	}
	public void setQuestion_answer_xml(String question_answer_xml) {
		this.question_answer_xml = question_answer_xml;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public int getTotal_marks() {
		return total_marks;
	}
	public void setTotal_marks(int total_marks) {
		this.total_marks = total_marks;
	}
	public int getPass_marks() {
		return pass_marks;
	}
	public void setPass_marks(int pass_marks) {
		this.pass_marks = pass_marks;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
