package yt.item4;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yt.item4.bean.Brand;
import yt.item4.bean.Shoes;
import yt.item4.dao.BrandDao;
import yt.item4.dao.ShoesDao;
import yt.item4.factory.CountryMapFactory;
import yt.item4.factory.HibernateDaoFactory;
import yt.item4.factory.IDaoFactory;

/**
 * Servlet implementation class ShoesTableController
 */
@WebServlet("/ShoesTableController")
public class TablesController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IDaoFactory daoFactory;

	private ShoesDao shoesDao;

	private Map<CountryCode, String> countryMap;

	public static final String COUNTRY_CODE_FILE = "/countryCodeToFullName";

	public static final String LIST_BRANDS = "/listBrands.jsp";

	public static final String INSERT_OR_EDIT_BRANDS = "/modifyBrand.jsp";

	public static final String LIST_SHOESS = "/listShoes.jsp";

	public static final String INSERT_OR_EDIT_SHOES = "/modifyShoes.jsp";

	public TablesController() {
		this.daoFactory = new HibernateDaoFactory();
		this.shoesDao = daoFactory.createShoesDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String forward = excuteAction(request.getParameter("action"), request);
		if (null == forward) {
			response.sendRedirect("/webExercise4/index.jsp"); // set to main page
			return;
		}
		request.getRequestDispatcher(forward).forward(request, response);
	}

	private String dispatchToList(HttpServletRequest request, Brand brand) {

		request.setAttribute("brand", brand);
		request.setAttribute("shoesList", shoesDao.readFullByBrand(brand.getBrandId()));
		return LIST_SHOESS;
	}

	private String dispatchToUpdate(HttpServletRequest request, Shoes shoes) {
		request.setAttribute("shoes", shoes);
		request.setAttribute("brand", shoes.getBrand());
		return INSERT_OR_EDIT_SHOES;
	}

	private String excuteAction(String action, HttpServletRequest request) {
		BrandDao brandDao = daoFactory.createBrandDao();
		Brand brand;
		try {
			brand = brandDao.selectBrandById(Integer.valueOf(request.getParameter("brandId")));
			if (null == brand)// no this brand
				return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}

		try {
			switch (ActionEnum.valueOf(action.toUpperCase())) {
				case DELETE:
					shoesDao.delete(Integer.valueOf(request.getParameter("shoesId")));
					return dispatchToList(request, brand);
				case EDIT:
					Shoes UpdateShoes = shoesDao.selectById(Integer.valueOf(request.getParameter("shoesId")));
					if (null == UpdateShoes || !isShoesMapToBrand(UpdateShoes, brand)) //got no data or value mapping failed
						return dispatchToList(request, brand);

					return dispatchToUpdate(request, UpdateShoes);
				case INSERT:
					Shoes InsertShoes = new Shoes().setBrand(brand);
					return dispatchToUpdate(request, InsertShoes);
				default:
					break;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return dispatchToList(request, brand);
	}

	private boolean isShoesMapToBrand(Shoes shoes, Brand brand) {
		return shoes.getBrand().getBrandId() == brand.getBrandId();
	}

	private boolean isCreate(String id) {
		return checkString2Int(id) == 0;
	}

	private int checkString2Int(String s) {
		if (s != null && s.trim().length() > 0) {
			return Integer.valueOf(s);
		}
		return 0;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Shoes shoes = new Shoes(request.getParameter("shoesName"));

		if (isCreate(request.getParameter("shoesId"))) {
			shoes.setCategory(request.getParameter("category")).setPrice(checkString2Int(request.getParameter("price")))
					.setSeries(request.getParameter("series")).setBrandById(Integer.valueOf(request.getParameter("brandId")));
			shoesDao.insert(shoes);
			response.sendRedirect("/webExercise4/ShoesTableController?action=list&brandId=" + request.getParameter("brandId")); //end post
			return;
		}

		shoes.setShoesId(Integer.valueOf(request.getParameter("shoesId"))).setCategory(request.getParameter("category"))
				.setPrice(Integer.valueOf(request.getParameter("price"))).setSeries(request.getParameter("series"))
				.setBrandById(Integer.valueOf(request.getParameter("brandId")));
		shoesDao.update(shoes);
		response.sendRedirect("/webExercise4/ShoesTableController?action=list&brandId=" + request.getParameter("brandId"));

	}

	public Map<CountryCode, String> getCountryMap() {
		return countryMap;
	}

	public void setCountryMap() {
		this.countryMap = (new CountryMapFactory()).createCountryMap(COUNTRY_CODE_FILE);
	}
}
