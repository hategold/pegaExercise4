package yt.item4.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import yt.item4.bean.Brand;
import yt.item4.bean.Shoes;

public class HibernateShoesDaoImpl implements ShoesDao {

	private SessionFactory sessionFactory;

	public HibernateShoesDaoImpl() {
		Configuration config = new Configuration().configure();
		this.setSessionFactory(config.buildSessionFactory());
	}

	@Override
	public Shoes selectById(int shoesId) {
		Session session = sessionFactory.openSession();
		Shoes selectedShoes = (Shoes) session.get(Shoes.class, shoesId);
		session.close();
		return selectedShoes;
	}

	@Override
	public boolean delete(int shoesId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.delete(selectById(shoesId));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("delete Null entity");
		}
		tx.commit();
		session.close();

		return true;
	}

	@Override
	public boolean update(Shoes shoes) {
		Session session = sessionFactory.openSession();
		Shoes updateShoes = selectById(shoes.getShoesId());

		updateShoes.setCategory(shoes.getCategory()).setPrice(shoes.getPrice()).setSeries(shoes.getSeries()).setShoesName(shoes.getShoesName())
				.setBrand((Brand) session.get(Brand.class, shoes.getBrand().getBrandId()));

		Transaction tx = session.beginTransaction();
		session.update(updateShoes);
		tx.commit();
		session.close();
		return true;
	}

	@Override
	public boolean insert(Shoes shoes) {
		Session session = sessionFactory.openSession();
		shoes.setBrand((Brand) session.get(Brand.class, shoes.getBrand().getBrandId()));
		Transaction tx = session.beginTransaction();
		session.save(shoes);
		tx.commit();
		session.close();
		return true;
	}

	@Override
	public List<Shoes> readFullByBrand(int brandId) {
		Session session = sessionFactory.openSession();

		if (!isExistBrand(session.get(Brand.class, brandId)))
			return null;

		@SuppressWarnings("unchecked")
		Query<Shoes> query = session.createQuery("from Shoes where brandId = ?");
		query.setParameter(0, brandId);
		List<Shoes> shoesList = query.getResultList();
		session.close();

		return shoesList;
	}

	private boolean isExistBrand(Brand brand) {
		return !(null == brand);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
