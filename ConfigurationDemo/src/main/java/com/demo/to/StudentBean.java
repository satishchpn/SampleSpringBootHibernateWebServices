package com.demo.to;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Satish Keshri
 *
 */

public class StudentBean {
	@JsonProperty(value = "studentId")

	private long studentId;

	@JsonProperty(value = "studentName")

	private String studentName;

	public StudentBean() {

	}

	public StudentBean(long studentId) {
		super();
		this.studentId = studentId;
	}

	public StudentBean(long studentId, String studentName) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof StudentBean))
			return false;
		StudentBean bean = (StudentBean) obj;
		return this.getStudentId() == bean.getStudentId();
	}

	@Override
	public int hashCode() {
		if (this.studentId == 0)
			return super.hashCode();
		else
			return (int) this.studentId;
	}

	@Override
	public String toString() {
		return studentId + " " + studentName;
	}
}
