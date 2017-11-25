package com.sjsu.backbenchers.vBless.hibernatecore;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class HibernateDao<E, K extends Serializable> implements GenericDao<E, K> {
	
	@Autowired
	private SessionFactory sessionFactory;
	private Class<?> daoType;
	
	@PersistenceContext
	protected EntityManager em;

	public HibernateDao() {
		daoType = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public void add(E entity) {
		sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void update(E entity) {
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

	@Override
	public void remove(E entity) {

	}

	@Override
	public E find(K key) {
		return (E) sessionFactory.getCurrentSession().get(daoType, key);
	}

	@Override
	public List<E> getList() {
		return null;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return the daoType
	 */
	public Class<?> getDaoType() {
		return daoType;
	}

	/**
	 * @param daoType
	 *            the daoType to set
	 */
	public void setDaoType(Class<?> daoType) {
		this.daoType = daoType;
	}

}
