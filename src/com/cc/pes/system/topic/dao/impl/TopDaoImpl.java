package com.cc.pes.system.topic.dao.impl;



import org.springframework.stereotype.Repository;

import com.cc.pes.system.simple.dao.hibernate.impl.HibernateDaoSupport;
import com.cc.pes.system.topic.dao.TopDao;
import com.cc.pes.system.topic.entity.Top;

@Repository("topDao")
public class TopDaoImpl extends HibernateDaoSupport<Top, Integer> implements TopDao<Top, Integer> {
	
	public TopDaoImpl(){
		this.getEntityClass();
	}
	
}
