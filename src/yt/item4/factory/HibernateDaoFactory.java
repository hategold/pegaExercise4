package yt.item4.factory;

import yt.item4.dao.BrandDao;
import yt.item4.dao.HibernateBrandDaoImpl;
import yt.item4.dao.HibernateShoesDaoImpl;
import yt.item4.dao.ShoesDao;

public class HibernateDaoFactory implements IDaoFactory {

	@Override
	public BrandDao createBrandDao() {
		return new HibernateBrandDaoImpl();
	}

	@Override
	public ShoesDao createShoesDao() {
		return new HibernateShoesDaoImpl();
	}

}
