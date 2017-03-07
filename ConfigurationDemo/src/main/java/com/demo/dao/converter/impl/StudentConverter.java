package com.demo.dao.converter.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.demo.dao.converter.ToEntityConverter;
import com.demo.entity.Student;
import com.demo.to.StudentBean;

@Component("StudentConverter")
@Scope("singleton")
public class StudentConverter implements ToEntityConverter<StudentBean, Student> {

	@Override
	public Student getAsEntity(StudentBean to) {
		Student Student = new Student();
		Student.setStudentId(to.getStudentId());
		Student.setStudentName(to.getStudentName());
		return Student;
	}

	@Override
	public StudentBean getAsTo(Student entity) {
		StudentBean bean = new StudentBean(entity.getStudentId(), entity.getStudentName());
		return bean;
	}
}
