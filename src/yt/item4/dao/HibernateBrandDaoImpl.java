package yt.item4.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import yt.item4.bean.Brand;

public class HibernateBrandDaoImpl implements BrandDao {

	private SessionFactory sessionFactory;

	public HibernateBrandDaoImpl() {
		Configuration config = new Configuration().configure();
		this.sessionFactory = config.buildSessionFactory();
	}

	public HibernateBrandDaoImpl(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	@Override
	public Brand selectBrandById(int brandId) {
		Session session = sessionFactory.openSession();
		Brand selectedBrand = (Brand) session.get(Brand.class, brandId);
		session.close();
		return selectedBrand;
	}

	@Override
	public List<Brand> readFullBrands() {

		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		Query<Brand> query = session.createQuery("From Brand");
		List<Brand> brandList = query.getResultList();
		session.close();

		return brandList;

	}

	@Override
	public boolean deleteBrand(int brandId) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.delete(selectBrandById(brandId));
		} catch (IllegalArgumentException e) {
			System.out.println("delete Null entity");
		}
		tx.commit();
		session.close();

		return true;

	}

	@Override
	public boolean updateBrand(Brand brand) {

		Session session = sessionFactory.openSession();
		Brand updateBrand = selectBrandById(brand.getBrandId());
		updateBrand.setBrandName(brand.getBrandName()).setCountry(brand.getCountry()).setWebsite(brand.getWebsite()).setShoesGroup(brand.getShoesGroup());

		Transaction tx = session.beginTransaction();
		session.update(updateBrand);
		tx.commit();
		session.close();
		return true;
	}

	@Override
	public boolean insertBrand(Brand brand) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(brand);
		tx.commit();
		session.close();
		return true;

	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
