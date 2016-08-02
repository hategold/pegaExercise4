package yt.item4;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yt.item4.bean.Shoes;
import yt.item4.dao.BrandDao;
import yt.item4.dao.BrandDaoImpl;
import yt.item4.dao.ShoesDao;
import yt.item4.dao.ShoesDaoImpl;

/**
 * Servlet implementation class ShoesTableController
 */
@WebServlet("/ShoesTableController")
public class ShoesTableController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ShoesDao shoesDao;

	public static final String LIST_SHOESS = "/listShoes.jsp";

	public static final String INSERT_OR_EDIT = "/modifyShoes.jsp";

	public ShoesTableController() {
		this.shoesDao = new ShoesDaoImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		RequestDispatcher view = request.getRequestDispatcher(excuteAction(action, request));
		view.forward(request, response);
	}

	private String dispatchToList(HttpServletRequest request) {
		BrandDao brandDao = new BrandDaoImpl();

		request.setAttribute("brand", brandDao.selectBrandById(Integer.valueOf(request.getParameter("brandId"))));
		request.setAttribute("shoesList", shoesDao.readFullByBrand(Integer.valueOf(request.getParameter("brandId"))));
		return LIST_SHOESS;
	}

	private String dispatchToUpdate(HttpServletRequest request) {
		BrandDao brandDao = new BrandDaoImpl();
		
		request.setAttribute("brand", brandDao.selectBrandById(Integer.valueOf(request.getParameter("brandId"))));
		return INSERT_OR_EDIT;
	}

	private String excuteAction(String action, HttpServletRequest request) {

		try {
			if (ActionEnum.DELETE.name().equalsIgnoreCase(action)) {

				shoesDao.delete(Integer.valueOf(request.getParameter("shoesId")));
				return dispatchToList(request);
			}
			if (ActionEnum.EDIT.name().equalsIgnoreCase(action)) {
				Shoes shoes = shoesDao.selectById(Integer.valueOf(request.getParameter("shoesId")));
				if (shoes == null) //got no data
					return dispatchToList(request);
				request.setAttribute("shoes", shoes);
				return dispatchToUpdate(request);
			}

			if (ActionEnum.INSERT.name().equalsIgnoreCase(action)) {
				return dispatchToUpdate(request);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Shoes shoes = new Shoes(request.getParameter("shoesName"));

		try {
			if (isCreate(request.getParameter("shoesId"))) {
				shoes.setCategory(request.getParameter("category")).setPrice(Integer.valueOf(request.getParameter("price")))
						.setSeries(request.getParameter("series"));
				shoes.setBrandById(Integer.valueOf(request.getParameter("brandId")));
				shoesDao.insert(shoes);
				response.sendRedirect("/webExercise4/ShoesTableController?action=list&brandId="+request.getParameter("brandId")); //end post
				return;
			}

			shoes.setShoesId(Integer.valueOf(request.getParameter("shoesId"))).setCategory(request.getParameter("category"))
					.setPrice(Integer.valueOf(request.getParameter("price"))).setSeries(request.getParameter("series"));
			shoes.setBrandById(Integer.valueOf(request.getParameter("brandId")));
			shoesDao.update(shoes);
			response.sendRedirect("/webExercise4/ShoesTableController?action=list&brandId="+request.getParameter("brandId"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
