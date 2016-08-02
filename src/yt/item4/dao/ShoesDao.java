package yt.item4.dao;

import java.sql.SQLException;
import java.util.List;

import yt.item4.bean.Shoes;

public interface ShoesDao {
	public Shoes selectById(int shoesId) throws SQLException;

	public boolean delete(int shoesId) throws SQLException;

	public boolean update(Shoes shoes) throws SQLException;

	public boolean insert(Shoes shoes) throws SQLException;

	public List<Shoes> readFullByBrand(int brandId);
}
