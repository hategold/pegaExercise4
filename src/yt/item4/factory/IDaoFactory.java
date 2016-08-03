package yt.item4.factory;

import yt.item4.dao.BrandDao;
import yt.item4.dao.ShoesDao;

public interface IDaoFactory {

	public BrandDao createBrandDao();

	public ShoesDao createShoesDao();
}
