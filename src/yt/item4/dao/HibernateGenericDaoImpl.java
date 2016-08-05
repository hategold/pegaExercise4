package yt.item4.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class HibernateGenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

	private SessionFactory sessionFactory;

	private Session session;

	private Transaction transaction;

	private Class<T> entityType;

	public HibernateGenericDaoImpl() {
		Configuration config = new Configuration().configure();
		this.sessionFactory = config.buildSessionFactory();
	}

	protected void startTransaction() throws HibernateException {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}

	protected void startSessionOnly() {
		session = sessionFactory.openSession();
	}

	public HibernateGenericDaoImpl(Class<T> entityType) {
		this.entityType = entityType;
	}

	@Override
	public T getById(PK Id) {
		startSessionOnly();
		T selectedEntity = (T) session.get(entityType, Id);
		session.close();
		return selectedEntity;
	}

	@Override
	public boolean deleteById(PK Id) {
		startTransaction();
		try {
			session.delete(getById(Id));
		} catch (IllegalArgumentException e) {
			System.out.println("delete Null entity");
			return false;
		}
		transaction.commit();
		session.close();
		return true;
	}

	@Override
	public boolean delete(T t) {
		startTransaction();
		try {
			session.delete(t);
		} catch (IllegalArgumentException e) {
			System.out.println("delete Null entity");
			return false;
		}
		transaction.commit();
		session.close();
		return true;
	}

	@Override
	public boolean update(T t) {
		startTransaction();
		session.update(t);
		transaction.commit();
		session.close();
		return true;
	}

	@Override
	public boolean insert(T t) {
		startTransaction();
		session.save(t);
		transaction.commit();
		session.close();
		return true;
	}

	@Override
	public List<T> findAll() {
		startSessionOnly();
		@SuppressWarnings("unchecked")
		Query<T> query = session.createQuery("From " + entityType.getName());
		List<T> entityList = query.getResultList();
		session.close();

		return entityList;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

}
