package yt.item4;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yt.item4.bean.Brand;
import yt.item4.bean.Shoes;
import yt.item4.dao.BrandDao;
import yt.item4.dao.ShoesDao;
import yt.item4.factory.HibernateDaoFactory;
import yt.item4.factory.IDaoFactory;

/**
 * Servlet implementation class ShoesTableController
 */
@WebServlet("/ShoesTableController")
public class ShoesTableController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IDaoFactory daoFactory;

	private ShoesDao shoesDao;

	public static final String LIST_SHOESS = "/listShoes.jsp";

	public static final String INSERT_OR_EDIT = "/modifyShoes.jsp";

	public ShoesTableController() {
		this.daoFactory = new HibernateDaoFactory();
		this.shoesDao = daoFactory.createShoesDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		String forward = excuteAction(action, request);
		if (null == forward) {
			response.sendRedirect("/webExercise4/index.jsp");
			return;
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	private String dispatchToList(HttpServletRequest request, Brand brand) {

		request.setAttribute("brand", brand);
		List<Shoes> shoesList = shoesDao.readFullByBrand(brand.getBrandId());
		request.setAttribute("shoesList", shoesList);
		return LIST_SHOESS;
	}

	private String dispatchToUpdate(HttpServletRequest request, Shoes shoes) {
		request.setAttribute("shoes", shoes);
		request.setAttribute("brand", shoes.getBrand());
		return INSERT_OR_EDIT;
	}

	private String excuteAction(String action, HttpServletRequest request) {
		BrandDao brandDao = daoFactory.createBrandDao();
		Brand brand;
		try {
			brand = brandDao.selectBrandById(Integer.valueOf(request.getParameter("brandId")));
			if (null == brand)
				return null;
		} catch (NumberFormatException | SQLException e1) {
			e1.printStackTrace();
			return null;
		}

		try {
			if (ActionEnum.DELETE.name().equalsIgnoreCase(action)) {

				shoesDao.delete(Integer.valueOf(request.getParameter("shoesId")));
				return dispatchToList(request, brand);
			}
			if (ActionEnum.EDIT.name().equalsIgnoreCase(action)) {
				Shoes shoes = shoesDao.selectById(Integer.valueOf(request.getParameter("shoesId")));
				if (shoes == null || shoes.getBrand().getBrandId() != brand.getBrandId()) //got no data
					return dispatchToList(request, brand);

				return dispatchToUpdate(request, shoes);
			}

			if (ActionEnum.INSERT.name().equalsIgnoreCase(action)) {
				Shoes shoes = new Shoes().setBrand(brand);
				return dispatchToUpdate(request, shoes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
		}
		return dispatchToList(request, brand);

	}

	private boolean isCreate(String id) {
		if (checkDoString2Int(id) == 0)
			return true;

		return false;
	}

	private int checkDoString2Int(String s) {
		if (s != null && s.trim().length() > 0) {
			return Integer.valueOf(s);
		}
		return 0;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Shoes shoes = new Shoes(request.getParameter("shoesName"));

		try {
			if (isCreate(request.getParameter("shoesId"))) {
				shoes.setCategory(request.getParameter("category")).setPrice(checkDoString2Int(request.getParameter("price")))
						.setSeries(request.getParameter("series"));
				shoes.setBrandById(Integer.valueOf(request.getParameter("brandId")));
				shoesDao.insert(shoes);
				response.sendRedirect("/webExercise4/ShoesTableController?action=list&brandId=" + request.getParameter("brandId")); //end post
				return;
			}

			shoes.setShoesId(Integer.valueOf(request.getParameter("shoesId"))).setCategory(request.getParameter("category"))
					.setPrice(Integer.valueOf(request.getParameter("price"))).setSeries(request.getParameter("series"));
			shoes.setBrandById(Integer.valueOf(request.getParameter("brandId")));
			shoesDao.update(shoes);
			response.sendRedirect("/webExercise4/ShoesTableController?action=list&brandId=" + request.getParameter("brandId"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
