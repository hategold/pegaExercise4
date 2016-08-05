package yt.item4;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yt.item4.bean.Brand;
import yt.item4.dao.BrandDao;
import yt.item4.factory.HibernateDaoFactory;
import yt.item4.factory.IDaoFactory;
import yt.item4.factory.CountryMapFactory;

/**
 * Servlet implementation class BrandTableController
 */
@WebServlet("/BrandTableController")
public class BrandTableController extends HttpServlet {

	private IDaoFactory daoFactory;

	private BrandDao brandDao;

	private Map<CountryCode, String> countryMap;

	private static final long serialVersionUID = 1L;

	public static final String LIST_BRANDS = "/listBrands.jsp";

	public static final String INSERT_OR_EDIT = "/modifyBrand.jsp";

	public static final String COUNTRY_CODE_FILE = "/countryCodeToFullName";

	public BrandTableController() {
		this.daoFactory = new HibernateDaoFactory();
		this.brandDao = daoFactory.createBrandDao();
		this.countryMap = (new CountryMapFactory()).createCountryMap(COUNTRY_CODE_FILE);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher(excuteAction(request.getParameter("action"), request)).forward(request, response);
	}

	private String dispatchToList(HttpServletRequest request) {
		request.setAttribute("brandList", brandDao.readFullBrands());
		return LIST_BRANDS;
	}

	private String dispatchToUpdate(HttpServletRequest request) {
		request.setAttribute("countryCodeMap", countryMap);
		return INSERT_OR_EDIT;
	}

	private String excuteAction(String action, HttpServletRequest request) {

		try {
			if (ActionEnum.DELETE.name().equalsIgnoreCase(action)) {

				brandDao.deleteBrand(Integer.valueOf(request.getParameter("brandId")));
				return dispatchToList(request);
			}
			if (ActionEnum.EDIT.name().equalsIgnoreCase(action)) {
				Brand brand = brandDao.selectBrandById(Integer.valueOf(request.getParameter("brandId")));
				if (brand == null) //got no data
					return dispatchToList(request);
				request.setAttribute("brand", brand);
				return dispatchToUpdate(request);
			}
			if (ActionEnum.INSERT.name().equalsIgnoreCase(action)) {
				return dispatchToUpdate(request);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return dispatchToList(request);
	}

	private boolean isCreate(String id) {
		try {
			Integer.valueOf(id);
		} catch (NumberFormatException e) {
			return true;
		}
		return false;
	}

	public void redirectList(HttpServletResponse response) throws IOException {
		response.sendRedirect("/webExercise4/BrandTableController?action=list");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Brand brand = new Brand(request.getParameter("brandName"));

		if (isCreate(request.getParameter("brandId"))) {
			brand.setCountry(request.getParameter("country")).setWebsite(request.getParameter("website"));
			brandDao.insertBrand(brand);
			redirectList(response); //end post
			return;
		}

		brand.setBrandId(Integer.valueOf(request.getParameter("brandId"))).setCountry(request.getParameter("country"))
				.setWebsite(request.getParameter("website"));
		brandDao.updateBrand(brand);
		redirectList(response);

	}

}
