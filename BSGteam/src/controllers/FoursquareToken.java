package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.*;

/**
 * Servlet implementation class FoursquareToken
 */
public class FoursquareToken extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		request.getRequestDispatcher("views/queryInterface.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("1");

		String fsAPI = request.getParameter("fsAPI");
		String authToken = request.getParameter("authToken");
		FoursquareToken foursquareToken = new FoursquareToken();

		System.out.println("2");

		HttpSession session = request.getSession();
		if (request.getParameter("frousquareToken") == null) {
			System.out.println("3");

			// try {
			request.setAttribute("error", "Sorry, your token is invalid");
			// }
			// } catch (Exception e) {
			// e.printStackTrace();
			// request.setAttribute("error", "Sorry, your token is invalid");
			// }
		}
		request.getRequestDispatcher("views/queryInterface.jsp").forward(
				request, response);
	}

}
