package com.yjx.service;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.yjx.entity.Clas;
import com.yjx.entity.Teacher;

public class ManyToMany {

	public static void main(String[] args) {
		EntityManagerFactory eFactory = Persistence.createEntityManagerFactory("JPA_Eclipselink_MTM");
		EntityManager entityManager =eFactory.createEntityManager();
		entityManager.getTransaction().begin();

	  	//Create Clas Entity
	   	Clas clas1=new Clas(0,"1st",null);
	   	Clas clas2=new Clas(0,"2nd",null);
	   	Clas clas3=new Clas(0,"3rd",null);
	   	
	   	entityManager.persist(clas1);
	   	entityManager.persist(clas2);
	   	entityManager.persist(clas3);
	   	
	   	Set<Clas> classSet1 = new HashSet<>();
	   	classSet1.add(clas1);
	   	classSet1.add(clas2);
	   	classSet1.add(clas3);
	   	
	   	Set<Clas> classSet2 = new HashSet<>();
	   	classSet2.add(clas1);
	   	classSet2.add(clas2);
	   	classSet2.add(clas3);
	   	
	   	Set<Clas> classSet3 = new HashSet<>();
	   	classSet3.add(clas1);
	   	classSet3.add(clas2);
	   	classSet3.add(clas3);
	   	//Create Teacher Entity
	   	
	   	Set<Clas>  classSet4 = new HashSet<>();
	   	classSet4.add(clas1);
	   	classSet4.add(clas2);
	   	classSet4.add(clas3);
	   	
	   	Teacher teacher1 = new Teacher(0,"Satish","Java",classSet1);
	   	Teacher teacher2 = new Teacher(0,"Krishna","Adv Java",classSet2);
	   	Teacher teacher3 = new Teacher(0,"Masthanvali","DB2",classSet3);
	   	
	   	//Store Teacher
	   	entityManager.persist(teacher1);
	   	entityManager.persist(teacher2);
	   	entityManager.persist(teacher3);
	   	
	   	entityManager.getTransaction( ).commit( );
	   	entityManager.close( );
	   	eFactory.close();
	}

}
