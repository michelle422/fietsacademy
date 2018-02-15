package be.vdab.servlets.docenten;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.services.DocentService;

/**
 * Servlet implementation class DocentVerwijderenServlet
 */
@WebServlet("/docenten/verwijderen.htm")
public class DocentVerwijderenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final transient DocentService docentService = new DocentService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		docentService.delete(Long.parseLong(request.getParameter("id")));
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath()));
	}

}
