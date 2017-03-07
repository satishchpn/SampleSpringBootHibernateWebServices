package com.demo.dao.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.demo.dao.StudentDB;
import com.demo.dao.StudentDao;
import com.demo.to.StudentBean;

@Repository(value = "temp")
@Scope("prototype")
public class StudentDaoImplHardCoded implements StudentDao {

	@Override
	public StudentBean getStudentById(long studentId) {
		return StudentDB.get(studentId);
	}

	@Override
	public boolean saveStudent(StudentBean bean) {
		return StudentDB.add(bean);
	}

	@Override
	public StudentBean updateStudent(StudentBean bean, long studentId) {
		return StudentDB.update(bean, studentId);
	}

	@Override
	public StudentBean deleteStudent(long studentId) {
		return StudentDB.delete(studentId);
	}

	@Override
	public List<StudentBean> getAllStudent() {
		return StudentDB.getAll();
	}

}
