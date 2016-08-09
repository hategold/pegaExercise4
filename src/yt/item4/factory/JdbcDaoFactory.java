package yt.item4.factory;

import yt.item4.dao.BrandDao;
import yt.item4.dao.GenericDao;
import yt.item4.dao.JdbcBrandDaoImpl;
import yt.item4.dao.JdbcShoesDaoImpl;
import yt.item4.dao.ShoesDao;

public class JdbcDaoFactory implements IDaoFactory {

	@Override
	public BrandDao createBrandDao() {
		return new JdbcBrandDaoImpl();
	}

	@Override
	public ShoesDao createShoesDao() {
		return new JdbcShoesDaoImpl();
	}

	@Override
	public GenericDao<?, ?> createGenericDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
