package com.org.kap.dto;

import java.sql.Timestamp;

public class CourseEntry {

	private Integer tcmId;
	private Integer courseId;
	private String courseName;
	private String Description;
	private int insertedBy;
	private Timestamp insertedOn;

	
	
	
	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getTcmId() {
		return tcmId;
	}

	public void setTcmId(Integer tcmId) {
		this.tcmId = tcmId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	
	
	
	public int getInsertedBy() {
		return insertedBy;
	}

	public void setInsertedBy(int insertedBy) {
		this.insertedBy = insertedBy;
	}

	public Timestamp getInsertedOn() {
		return insertedOn;
	}

	public void setInsertedOn(Timestamp insertedOn) {
		this.insertedOn = insertedOn;
	}

	@Override
	public String toString() {
		return "CourseEntry [tcmId=" + tcmId + ", courseName=" + courseName + ", Description=" + Description
				+ ", insertedBy=" + insertedBy + ", insertedOn=" + insertedOn + "]";
	}

	
}
