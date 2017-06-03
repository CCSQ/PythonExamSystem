package com.cc.pes.system.papers.dao.impl;




import org.springframework.stereotype.Repository;

import com.cc.pes.system.exam.dao.ExamDao;
import com.cc.pes.system.exam.entity.Exam;
import com.cc.pes.system.papers.dao.PapersDao;
import com.cc.pes.system.papers.entity.Papers;
import com.cc.pes.system.simple.dao.hibernate.impl.HibernateDaoSupport;

@Repository("papersDao")
public class PapersDaoImpl extends HibernateDaoSupport<Papers, Integer> implements PapersDao<Papers, Integer> {
	
	public PapersDaoImpl(){
		this.getEntityClass();
	}
	
}
