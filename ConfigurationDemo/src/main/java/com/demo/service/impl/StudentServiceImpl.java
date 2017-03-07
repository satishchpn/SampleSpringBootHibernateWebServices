package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.demo.dao.StudentDao;
import com.demo.service.StudentService;
import com.demo.to.StudentBean;

@Service
@Scope("prototype")
public class StudentServiceImpl implements StudentService {

	@Autowired
	//@Qualifier("temp")
	@Qualifier("jpa") //Possible Values [temp:"Hard Coded Data"/jpa:"With Declarative Transaction and Without DataSource Conn"]
	StudentDao studentDao;

	@Override
	public StudentBean getStudentById(long studentId) {
		return studentDao.getStudentById(studentId);
	}

	@Override
	public boolean saveStudent(StudentBean bean) {
		return studentDao.saveStudent(bean);
	}

	@Override
	public StudentBean updateStudent(StudentBean bean, long studentId) {
		return studentDao.updateStudent(bean, studentId);
	}

	@Override
	public StudentBean deleteStudent(long studentId) {
		return studentDao.deleteStudent(studentId);
	}

	@Override
	public List<StudentBean> getAllStudent() {
		return studentDao.getAllStudent();
	}

}
