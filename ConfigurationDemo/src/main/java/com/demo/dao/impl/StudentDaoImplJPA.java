package com.demo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.StudentDao;
import com.demo.dao.converter.ToEntityConverter;
import com.demo.entity.Student;
import com.demo.to.StudentBean;

@Repository(value = "jpa")
@Scope("prototype")
public class StudentDaoImplJPA implements StudentDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	@Qualifier("StudentConverter")
	private ToEntityConverter<StudentBean, Student> studentConverter;

	@Override
	public StudentBean getStudentById(long studentId) {
		StudentBean bean = null;
		try {
			bean = studentConverter.getAsTo(entityManager.find(Student.class, studentId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean saveStudent(StudentBean bean) {
		try {
			entityManager.persist(studentConverter.getAsEntity(bean));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public StudentBean updateStudent(StudentBean bean, long studentId) {
		try {
			Student older = entityManager.find(Student.class, studentId);
			older.setStudentName(bean.getStudentName());
			entityManager.merge(older);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public StudentBean deleteStudent(long studentId) {
		StudentBean bean = null;
		try {
			Student Student = entityManager.getReference(Student.class, studentId);
			bean = studentConverter.getAsTo(Student);
			entityManager.remove(Student);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	@Override
	public List<StudentBean> getAllStudent() {
		List<StudentBean> beanList = new ArrayList<>();
		try {
			List<Student> pList = entityManager.createQuery("FROM Student p", Student.class).getResultList();
			for (Student p : pList) {
				beanList.add(studentConverter.getAsTo(p));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beanList;
	}
	
	
	public static void main(String[] args) {
		int num=5;
		//check(num);
	        int i,k,j;
	        int total=0;
	        for(i=1;i<=num;i++)
	        {
	            k=0;
	            for(j=2;j<i;j++)
	            {
	                if(i%j==0)
	                {
	                    k=1;
	                    break;
	                }
	            }
	            if(k==0)
	            {
	                System.out.println(i);
	                total=total+1;
	            }
	        }
	        System.out.println("Total Prime"+total);
	    }


	private static void check(int num) {
		int i=2;
		int result=0;
		while(i<=num/2)
		{
			if(num%i==0)
			{
				result=1;
			}
			i++;
		}
		if(result==1)
			System.out.println("Not Prime");
		else
			System.out.println("Prime");
	}


}
