package yt.item4.dao;

import java.sql.SQLException;
import java.util.List;

import yt.item4.bean.Shoes;

public class JdbcShoesDaoImpl implements ShoesDao {

	@Override
	public Shoes selectById(int shoesId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int shoesId) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Shoes shoes) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insert(Shoes shoes) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Shoes> readFullByBrand(int brandId) {
		// TODO Auto-generated method stub
		return null;
	}

}
