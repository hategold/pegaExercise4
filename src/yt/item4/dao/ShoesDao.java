package yt.item4.dao;

import java.util.List;

import yt.item4.bean.Shoes;

public interface ShoesDao {

	public Shoes selectById(int shoesId);

	public boolean delete(int shoesId);

	public boolean update(Shoes shoes);

	public boolean insert(Shoes shoes);

	public List<Shoes> readFullByBrand(int brandId);
}
