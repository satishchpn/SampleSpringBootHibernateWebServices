package com.demo.service;

import java.util.List;

import com.demo.to.StudentBean;

public interface StudentService {
	StudentBean getStudentById(long StudentId);

	boolean saveStudent(StudentBean bean);

	StudentBean updateStudent(StudentBean bean, long studentId);

	StudentBean deleteStudent(long studentId);

	List<StudentBean> getAllStudent();
}
