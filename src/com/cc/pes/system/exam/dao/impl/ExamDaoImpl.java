package com.cc.pes.system.exam.dao.impl;


import org.springframework.stereotype.Repository;

import com.cc.pes.system.exam.dao.ExamDao;
import com.cc.pes.system.exam.entity.Exam;
import com.cc.pes.system.simple.dao.hibernate.impl.HibernateDaoSupport;

@Repository("examDao")
public class ExamDaoImpl extends HibernateDaoSupport<Exam, Integer> implements ExamDao<Exam, Integer> {
	
	public ExamDaoImpl(){
		this.getEntityClass();
	}
	
}
