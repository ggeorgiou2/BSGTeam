package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fi.foyt.foursquare.api.FoursquareApi;
import twitter4j.Twitter;
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
		System.out.println("11");

		String clientID = request.getParameter("clientID");
		String clinetSec = request.getParameter("clinetSec");
		String redirectURL = request.getParameter("redirectURL");
		String accessToken = request.getParameter("accessToken");
		
		FoursquareObject foursquareObject = new FoursquareObject(clientID, clinetSec, redirectURL, accessToken);

		System.out.println("22");

		HttpSession session = request.getSession();
		
		if (request.getParameter("foursquareToken") == null) {
			System.out.println("3");
			try {
				FoursquareApi fsAPI = new FoursquareApi(
						clientID,
						clinetSec,
						redirectURL);
				fsAPI.setoAuthToken(accessToken);
		
				//check it again
		if (fsAPI.getAuthenticationUrl() != null) {
			session.setAttribute("foursquareToken", "foursquareToken");
			session.setAttribute("clientID", foursquareObject.getClientID());
			session.setAttribute("clinetSec", foursquareObject.getClinetSec());
			session.setAttribute("redirectURL", foursquareObject.getRedirectURL());
			session.setAttribute("accessToken",	foursquareObject.getAccessToken());
			request.setAttribute("success",
					"You have been successfully login on frousquare");
		}
		else{
			request.setAttribute("error",
					"Sorry, your token is invalid");
		}
			} catch (Exception e) {
				// e.printStackTrace();
				request.setAttribute("error", "Sorry, your token is invalid");
			}
		}
		System.out.println("33");
		
		request.getRequestDispatcher("views/queryInterface.jsp").forward(
				request, response);
	}

}
