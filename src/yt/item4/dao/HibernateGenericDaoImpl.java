package yt.item4.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

public class HibernateGenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

	private SessionFactory sessionFactory;

	private Session session;

	private Transaction transaction;

	private Class<T> entityType;

	public HibernateGenericDaoImpl(Class<T> entityType) {
		Configuration config = new Configuration().configure();
		this.sessionFactory = config.buildSessionFactory();
		this.entityType = entityType;

	}

	protected void startTransaction() throws HibernateException {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}

	protected void startSessionOnly() {
		session = sessionFactory.openSession();
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
		System.out.println(t);
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

	@Override
	public List<T> findByFk(String fkFieldName, Object fkId) {
		startSessionOnly();//session deprecated criteria
		DetachedCriteria dCriteria = DetachedCriteria.forClass(entityType);
		Criteria criteria = dCriteria.getExecutableCriteria(session);
		@SuppressWarnings("unchecked")
		List<T> entityList = criteria.createCriteria(fkFieldName).add(Restrictions.idEq(fkId)).list();
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
