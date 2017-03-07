package com.demo.dao;

import java.util.List;

import com.demo.to.StudentBean;

public interface StudentDao {
	StudentBean getStudentById(long studentId);

	boolean saveStudent(StudentBean bean);

	StudentBean updateStudent(StudentBean bean, long studentId);

	StudentBean deleteStudent(long studentId);

	List<StudentBean> getAllStudent();
}
