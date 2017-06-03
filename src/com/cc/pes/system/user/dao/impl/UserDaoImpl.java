package com.cc.pes.system.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.cc.pes.system.simple.dao.hibernate.impl.HibernateDaoSupport;
import com.cc.pes.system.user.dao.UserDao;
import com.cc.pes.system.user.entity.User;

@Repository("userDao")
public class UserDaoImpl extends HibernateDaoSupport<User, Integer> implements UserDao<User, Integer> {
	
	public UserDaoImpl(){
		this.getEntityClass();
	}
	
}
