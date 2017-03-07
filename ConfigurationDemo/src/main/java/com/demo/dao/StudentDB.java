package com.demo.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;

import com.demo.to.StudentBean;

public class StudentDB {
	private static Vector<StudentBean> studentTable = new Vector<>(50);

	private static AtomicLong idGenerator = new AtomicLong(0);

	static public boolean add(StudentBean StudentBean) {
		StudentBean.setStudentId(idGenerator.incrementAndGet());
		return studentTable.add(new StudentBean(StudentBean.getStudentId(), StudentBean.getStudentName()));
	}

	static public StudentBean update(StudentBean StudentBean, long StudentId) {
		StudentBean older = studentTable.stream().filter(p -> p.getStudentId() == StudentId).findAny().orElse(null);
		if (older != null) {
			older.setStudentName(StudentBean.getStudentName());
		}
		return new StudentBean(older.getStudentId(), older.getStudentName());
	}

	static public StudentBean get(long StudentId) {
		StudentBean older = studentTable.stream().filter(p -> p.getStudentId() == StudentId).findAny().orElse(null);
		return new StudentBean(older.getStudentId(), older.getStudentName());
	}

	static public StudentBean delete(long StudentId) {
		StudentBean older = studentTable.stream().filter(p -> p.getStudentId() == StudentId).findAny().orElse(null);
		studentTable.remove(older);
		return older;
	}

	static public List<StudentBean> getAll() {
		if (studentTable.isEmpty())
			return Collections.emptyList();
		List<StudentBean> beanList = new ArrayList<>();
		synchronized (studentTable) {
			for (StudentBean bean : studentTable) {
				beanList.add(new StudentBean(bean.getStudentId(), bean.getStudentName()));
			}
		}
		return beanList;
	}
}
