package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import fi.foyt.foursquare.api.FoursquareApi;
import models.*;

/**
 * This class  receives and verifies a user's foursquare token
 * 
 * @author BSGTeam
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

		//receives parameters from the form
		String clientID = request.getParameter("clientID");
		String clientSecret = request.getParameter("clinetSec");
		String redirectURL = request.getParameter("redirectURL");
		String accessToken = request.getParameter("accessToken");
		FoursquareObject foursquareObject = new FoursquareObject(clientID,
				clientSecret, redirectURL, accessToken);

		HttpSession session = request.getSession();
		if (request.getParameter("foursquareToken") == null) {
			try {
				FoursquareApi fsAPI = new FoursquareApi(clientID, clientSecret,
						redirectURL);
				fsAPI.setoAuthToken(accessToken);
				// checks if token is valid by searching for a venue...
				if (fsAPI
						.venuesSearch("40.7,-74", null, null, null, null,
								null, null, null, null, null, null).getMeta()
						.getCode() == 200) {
					//token is valid; session attributes are set
					session.setAttribute("foursquareToken", "foursquareToken");
					session.setAttribute("clientID",
							foursquareObject.getClientID());
					session.setAttribute("clinetSec",
							foursquareObject.getClinetSec());
					session.setAttribute("redirectURL",
							foursquareObject.getRedirectURL());
					session.setAttribute("accessToken",
							foursquareObject.getAccessToken());
					request.setAttribute("success",
							"You have been successfully logged in");
				} else {
					request.setAttribute("error",
							"Sorry, your foursquare token is invalid");
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "Sorry, your foursquare token is invalid");
			}
		}
		request.getRequestDispatcher("views/queryInterface.jsp").forward(
				request, response);	}
}
