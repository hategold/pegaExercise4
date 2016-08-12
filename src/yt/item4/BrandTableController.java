package yt.item4;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import yt.item4.bean.Brand;

/**
 * Servlet implementation class BrandTableController
 */
@WebServlet("/BrandTableController")
public class BrandTableController extends AbstractTableController<Brand, Integer> {

	private static final long serialVersionUID = 1L;

	@Override
	public Integer parsePkFromReq(HttpServletRequest request) {
		return checkString2Int(request.getParameter("brandId"));
	}

}
