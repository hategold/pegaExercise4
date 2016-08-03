package yt.item4.dao;

import java.sql.SQLException;
import java.util.List;

import yt.item4.bean.Brand;

public interface BrandDao {

	public Brand selectBrandById(int brandId) throws SQLException;

	public boolean deleteBrand(int brandId) throws SQLException;

	public boolean updateBrand(Brand brand) throws SQLException;

	public boolean insertBrand(Brand brand) throws SQLException;

	public List<Brand> readFullBrands();
}
