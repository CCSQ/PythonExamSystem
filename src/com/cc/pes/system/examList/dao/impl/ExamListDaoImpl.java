package com.cc.pes.system.examList.dao.impl;


import org.springframework.stereotype.Repository;

import com.cc.pes.system.exam.dao.ExamDao;
import com.cc.pes.system.exam.entity.Exam;
import com.cc.pes.system.examList.dao.ExamListDao;
import com.cc.pes.system.examList.entity.ExamList;
import com.cc.pes.system.simple.dao.hibernate.impl.HibernateDaoSupport;

@Repository("examListDao")
public class ExamListDaoImpl extends HibernateDaoSupport<ExamList, Integer> implements ExamListDao<ExamList, Integer> {
	
	public ExamListDaoImpl(){
		this.getEntityClass();
	}
	
}
