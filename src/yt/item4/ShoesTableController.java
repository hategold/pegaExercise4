package yt.item4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yt.item4.bean.Brand;
import yt.item4.bean.Shoes;
import yt.item4.dao.BrandDao;
import yt.item4.dao.GenericDao;
import yt.item4.dao.HibernateGenericDaoImpl;
import yt.item4.dao.ShoesDao;
import yt.item4.factory.HibernateDaoFactory;
import yt.item4.factory.IDaoFactory;

/**
 * Servlet implementation class ShoesTableController
 */
@WebServlet("/ShoesTableController")
public class ShoesTableController extends AbstractTableController<Shoes, Integer> {

	private static final long serialVersionUID = 1L;

	public static final String LIST_SHOESS = "/listShoes.jsp";

	public static final String INSERT_OR_EDIT = "/modifyShoes.jsp";

	public ShoesTableController() {
		super(LIST_SHOESS, INSERT_OR_EDIT, Shoes.class);
	}

	private boolean isShoesMapToBrand(Shoes shoes, Brand brand) {
		return shoes.getBrand().getBrandId() == brand.getBrandId();
	}

	@Override
	public Integer parsePkFromReq(HttpServletRequest request) {
		return checkString2Int(request.getParameter("shoesId"));
	}

	@Override
	public Shoes buildEntityByReq(HttpServletRequest request) {
		Shoes shoes = new Shoes(request.getParameter("shoesName"));
		shoes.setShoesId(parsePkFromReq(request)).setCategory(request.getParameter("category")).setPrice(Integer.valueOf(request.getParameter("price")))
				.setSeries(request.getParameter("series")).setBrandById(Integer.valueOf(request.getParameter("brandId")));
		return shoes;
	}

	@Override
	public String dispatchToUpdate(HttpServletRequest request, Shoes shoes) {
		Brand brand = buildBrand(request);
		if (brand == null)
			return null;
		if (!isShoesMapToBrand(shoes, brand))
			return dispatchToList(request);

		request.setAttribute("brand", shoes.getBrand());
		return super.dispatchToUpdate(request, shoes);
	}

	@Override
	public String dispatchToList(HttpServletRequest request) {
		Brand brand = buildBrand(request);
		if (brand == null)
			return null;
		request.setAttribute("brand", brand);
		request.setAttribute("shoesList", genericDao.findByCondition("brandId =" + String.valueOf(brand.getBrandId())));
		return LIST_SHOESS;
	}

	@Override
	public String buildListUrl(HttpServletRequest request) throws IOException {
		return super.buildListUrl(request) + request.getParameter("brandId");
	}

	private Brand buildBrand(HttpServletRequest request) {
		GenericDao<Brand, Integer> brandDao = new HibernateGenericDaoImpl<Brand, Integer>();
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
