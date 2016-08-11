package yt.item4;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.ws.util.StringUtils;

import yt.item4.bean.EntityInterface;
import yt.item4.dao.GenericDao;
import yt.item4.dao.HibernateGenericDaoImpl;

public abstract class AbstractTableController<T extends EntityInterface, PK extends Serializable> extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected GenericDao<T, PK> genericDao;

	public final Class<T> classType;

	protected final String INSERT_OR_EDIT_PAGE;

	protected final String LIST_PAGE;

	public AbstractTableController(String listPage, String editPage, Class<T> classType) {
		this.INSERT_OR_EDIT_PAGE = editPage;
		this.LIST_PAGE = listPage;
		this.classType = classType;
		this.genericDao = new HibernateGenericDaoImpl<T, PK>(classType);
	}

	public abstract PK parsePkFromReq(HttpServletRequest request);

	public abstract T buildEntityByReq(HttpServletRequest request);

	public abstract String getListPage();

	public String dispatchToList(HttpServletRequest request) {
		request.setAttribute(classType.getSimpleName().toLowerCase() + "List", genericDao.findAll());
		return getListPage();//TODO check 
	}

	public String dispatchToUpdate(HttpServletRequest request, T entity) {
		if (entity != null)
			request.setAttribute(entity.getClass().getSimpleName().toLowerCase(), entity);
		return INSERT_OR_EDIT_PAGE;
	};

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward = excuteAction(request.getParameter("action"), request);
		if (null == forward) {
			response.sendRedirect("/webExercise4/index.jsp"); // set to main page
			return;
		}
		request.getRequestDispatcher(forward).forward(request, response);
	}

	protected String excuteAction(String action, HttpServletRequest request) {
		//findbyWHEREstatement
		try {
			switch (ActionEnum.valueOf(action.toUpperCase())) {
				case DELETE:
					genericDao.deleteById(parsePkFromReq(request));
					return dispatchToList(request);
				case EDIT:

					T entity = genericDao.getById(parsePkFromReq(request));

					if (entity == null) //got no data
						return dispatchToList(request);

					return dispatchToUpdate(request, entity);
				case INSERT:
					return dispatchToUpdate(request, null);
				default:
					break;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return dispatchToList(request);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		T entity = buildEntityByReq(request);

		if (isCreate(entity.getId())) {
			genericDao.insert(entity);
			response.sendRedirect(buildListUrl(request)); //end post
			return;
		}

		genericDao.update(entity);
		response.sendRedirect(buildListUrl(request));
	}

	public String buildListUrl(HttpServletRequest request) throws IOException {
		//request.getContextPath() => /webExercise4/
		return "/webExercise4/" + this.getClass().getSimpleName() + "?action=list";
	}

	private boolean isCreate(int id) {
		return id == 0;
	}

	protected int checkString2Int(String s) {
		if (s != null && s.trim().length() > 0)
			return Integer.valueOf(s);

		return 0;
	}
}
