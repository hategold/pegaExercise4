package yt.item4.dao;

import java.util.List;

import yt.item4.bean.Brand;

public interface BrandDao {

	public Brand selectBrandById(int brandId);

	public boolean deleteBrand(int brandId);

	public boolean updateBrand(Brand brand);

	public boolean insertBrand(Brand brand);

	public List<Brand> readFullBrands();
	
}
