package be.vdab.servlets.campussen;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.services.CampusService;

/**
 * Servlet implementation class CampusDocentenServlet
 */
@WebServlet("/campussen/docenten.htm")
public class CampusDocentenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/campussen/docenten.jsp";
	private final transient CampusService campusService = new CampusService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("campussen", campusService.findAll());
		String id = request.getParameter("id");
		if (id != null) {
			campusService.read(Long.parseLong(id))
				.ifPresent(campus -> request.setAttribute("campus", campus));
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
