package yt.item4;


import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


public class BrandDaoImpl implements BrandDao {

	private SessionFactory sessionFactory;

	public BrandDaoImpl() {
		Configuration config = new Configuration().configure();
		this.sessionFactory = config.buildSessionFactory();
	}

	public BrandDaoImpl(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	@Override
	public Brand selectBrandByID(int brandId) {
		Session session = sessionFactory.openSession();
		Brand selectedBrand = (Brand) session.get(Brand.class, brandId);
		session.close();
		return selectedBrand;
	}

	@Override //TODO
	public List<Brand> readFullBrands() {

		Session session = sessionFactory.openSession();
		
		@SuppressWarnings("unchecked")
		Query<Brand> query = session.createQuery("From Brand");
		List<Brand> brandList = query.getResultList();
		session.close();

		return brandList;

	}

	@Override
	public boolean deleteBrand(int brandId) throws SQLException {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(selectBrandByID(brandId));
		tx.commit();
		session.close();

		return true;

	}

	@Override //TODO
	public boolean updateBrand(Brand brand) throws SQLException {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Brand updateBrand = selectBrandByID(brand.getBrandId());
		updateBrand.setBrandName(brand.getBrandName()).setCountry(brand.getCountry()).setWebsite(brand.getWebsite()).setShoesGroup(brand.getShoesGroup());
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
