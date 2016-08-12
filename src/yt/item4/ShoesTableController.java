package yt.item4;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import yt.item4.bean.Brand;
import yt.item4.bean.Shoes;
import yt.item4.dao.GenericDao;
import yt.item4.dao.HibernateGenericDaoImpl;

@WebServlet("/ShoesTableController")
public class ShoesTableController extends AbstractTableController<Shoes, Integer> {

	private static final long serialVersionUID = 1L;

	private boolean isShoesMapToBrand(Shoes shoes, Brand brand) {
		return shoes.getBrand().getBrandId() == brand.getBrandId();
	}

	public Integer parsePkFromReq(HttpServletRequest request) {
		return checkString2Int(request.getParameter("shoesId"));
	}

	@Override
	public Shoes buildEntityByReq(HttpServletRequest request) {

		return super.buildEntityByReq(request).setBrand(buildBrand(request));
	}

	@Override
	public String dispatchToUpdate(HttpServletRequest request, Shoes shoes) {
		Brand brand = buildBrand(request);

		if (brand == null && shoes == null)
			return null;

		if (shoes == null)
			shoes = new Shoes().setBrand(brand);

		if (brand == null)
			brand = shoes.getBrand();

		if (!isShoesMapToBrand(shoes, brand))
			return dispatchToList(request);

		request.setAttribute("brand", shoes.getBrand());
		return super.dispatchToUpdate(request, shoes);
	}

	@Override
	public String dispatchToList(HttpServletRequest request) {
		Brand brand = buildBrand(request);
		if (brand == null)
			return super.dispatchToList(request);
		request.setAttribute("brand", brand);
		request.setAttribute("shoesList", genericDao.findByFk("brand", brand.getBrandId()));
		return LIST_PAGE;
	}

	@Override
	public String buildListUrl(HttpServletRequest request) throws IOException {
		return super.buildListUrl(request) + "&brandId=" + request.getParameter("brandId");
	}

	private Brand buildBrand(HttpServletRequest request) {
		GenericDao<Brand, Integer> brandDao = new HibernateGenericDaoImpl<Brand, Integer>(Brand.class);
		Brand brand = null;
		try {
			brand = brandDao.getById(Integer.valueOf(request.getParameter("brandId")));
			if (null == brand)
				return null;

		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
		return brand;
	}

}
