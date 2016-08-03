package yt.item4.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import yt.item4.bean.Brand;
import yt.item4.bean.Shoes;

public class ShoesDaoImpl implements ShoesDao {

	private SessionFactory sessionFactory;

	public ShoesDaoImpl() {
		Configuration config = new Configuration().configure();
		this.setSessionFactory(config.buildSessionFactory());
	}

	@Override
	public Shoes selectById(int shoesId) throws SQLException {
		Session session = sessionFactory.openSession();
		Shoes selectedShoes = (Shoes) session.get(Shoes.class, shoesId);
		session.close();
		return selectedShoes;
	}

	@Override
	public boolean delete(int shoesId) throws SQLException {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(selectById(shoesId));
		tx.commit();
		session.close();

		return true;
	}

	@Override
	public boolean update(Shoes shoes) throws SQLException {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Shoes updateShoes = selectById(shoes.getShoesId());
		updateShoes.setCategory(shoes.getCategory()).setPrice(shoes.getPrice()).setSeries(shoes.getSeries()).setShoesName(shoes.getShoesName());
		updateShoes.setBrand((Brand) session.get(Brand.class, shoes.getBrand().getBrandId()));
		session.update(updateShoes);
		tx.commit();
		session.close();
		return true;
	}

	@Override
	public boolean insert(Shoes shoes) throws SQLException {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		shoes.setBrand((Brand) session.get(Brand.class, shoes.getBrand().getBrandId()));
		session.save(shoes);
		tx.commit();
		session.close();
		return true;
	}

	@Override
	public List<Shoes> readFullByBrand(int brandId) {
		Session session = sessionFactory.openSession();

		@SuppressWarnings("unchecked")
		Query<Shoes> query = session.createQuery("from Shoes where brandId = ?");
		query.setParameter(0, brandId);
		List<Shoes> shoesList = query.getResultList();
		session.close();

		return shoesList;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
