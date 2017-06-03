package com.cc.pes.system.simple.dao.hibernate.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cc.pes.system.simple.dao.hibernate.HibernateDao;
import com.cc.pes.system.simple.entity.BaseEntity;

import javassist.bytecode.LineNumberAttribute;

public class HibernateDaoSupport<T, ID extends Serializable> implements HibernateDao<T, ID> {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	//	获取java泛型参数类型
	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	protected String getEntityName() {
		return getEntityClass().getSimpleName();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> find() {
		return getSession().createQuery(buildHQLByClass(this.getEntityClass())).list();
	}

	@SuppressWarnings("unchecked")
	public ID save(T entity) {
		if (entity instanceof BaseEntity) {
			((BaseEntity) entity).setCreateDate(new Date());
			((BaseEntity) entity).setVersion(0);
			((BaseEntity) entity).setModifyDate(new Date());
			((BaseEntity) entity).setCreateUserId(-1);
			((BaseEntity) entity).setModifyUserId(-1);
		}
		return (ID) getSession().save(entity);
	}

	@Override
	public void delete(List<T> entityList) {
		for (T t : entityList) {
			delete(t);
		}
	}
	
	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}

	public void delete(ID... ids) {
		for (ID id : ids) {
			this.delete(this.get(id));
		}
	}

	@Override
	public void update(T entity) {
		if (entity instanceof BaseEntity) {
			((BaseEntity) entity).setModifyDate(new Date());
			((BaseEntity) entity).setModifyUserId(-1);
		}
		this.getSession().update(entity);
	}

	@SuppressWarnings("unchecked")
	public T get(ID id) {
		return (T) getSession().get(this.getEntityClass(), id);
	}

	@Override
	public Object findUniqueBy(Class<?> entityClass, String propertyName,
			Object value) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq(propertyName, value));
		return criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByHQL(String hql) {
		return getSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findWithWhere(String where) {
		return getSession().createQuery(buildHQLByClass(this.getEntityClass()) + " where" + where).list();
	}

//	按页查找
	@Override
	public List<T> findPageByHql(String hql, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<T> findPageBySql(String sql, Object... params) {
		return null;
	}
	
	protected Query setParameters(Query query, Object... params) {
		
		int i = 0;
		for (Object object : params) {
			query.setParameter(i, object);
			i++;
		}
		
		return query;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByHQL(String hql, Object... params) {
		return createHQLQuery(hql, params).list();
	}
	
	@SuppressWarnings("unchecked")
	public void commitByHQL(String hql, Object... params) {
		Query query = createHQLQuery(hql, params);
		query.executeUpdate();
	}
	
	//	查找从begin到end的实体
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findSomeByHql(String hql, int begin, int length, Object... params) {
		Query query = createHQLQuery(hql, params);
		query.setFirstResult(begin);
		query.setMaxResults(length);
		return query.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findSomeByHql(String hql, int begin, int length) {
		Query query = getSession().createQuery(hql);
		query.setFirstResult(begin);
		query.setMaxResults(length);
		
		return query.list();
	}

	public SQLQuery createSQLQuery(String sql, Object... params) {
		return (SQLQuery) setParameters(getSession().createSQLQuery(sql), params);
	}

	public Query createHQLQuery(String hql, Object... params) {
		return setParameters(getSession().createQuery(hql),params);
	}

	public static String buildHQLByClass(Class<?> clazz) {
		return "from " + clazz.getSimpleName() + " t";
	}

	public static String removeSelect(String str) {
		int pos = str.toLowerCase().indexOf("from");
		if (pos != -1) {
			str.substring(pos);
		}
		return str;
	}

	@Override
	public int getCount(String tableName) {
		String hqlString = "select count(*) from " + tableName;
		Query query = this.getSession().createQuery(hqlString);
		return ((Number)query.uniqueResult()).intValue();
	}

	
}
