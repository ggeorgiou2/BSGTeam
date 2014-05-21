package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fi.foyt.foursquare.api.*;
import fi.foyt.foursquare.api.entities.*;

/**
 * NearVenue.java
 * 
 * This servlet queries venues that are within or near a particular venue
 * 
 * @author BSG Team
 * 
 */
public class NearVenue extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("views/additional_features.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// retrieves information passed from the form
		String venueId = request.getParameter("venueID");
		// creates a connection with the foursquare api
		HttpSession session = request.getSession();

		FoursquareApi foursquareApi = new FoursquareApi(
				session.getAttribute("clientID").toString(),
				session.getAttribute("clinetSec").toString(),
				session.getAttribute("redirectURL").toString());
		foursquareApi
				.setoAuthToken(session.getAttribute("accessToken").toString());
		
		Double latitude = 0.0;
		Double longitude = 0.0;
		try {
			// gets the location of the venue passed
			Location location = foursquareApi.venue(venueId).getResult()
					.getLocation();
			// gets the longitude and latitude and converts them to
			// <code>String</code> to be used by the foursquare API
			latitude = location.getLat();
			longitude = location.getLng();
			String stringLat = Double.toString(latitude);
			String stringLong = Double.toString(longitude);
			String result = stringLat + "," + stringLong;
			// searches for other venues within the geographical location
			Result<VenuesSearchResult> result2 = null;
			result2 = foursquareApi.venuesSearch(result, null, null, null,
					null, null, null, null, null, null, null);
			// if result code is 200 (OK) proceed
			if (result2.getMeta().getCode() == 200) {
				// passes the venue results to the view for display to the user
				CompactVenue[] nearVenues = result2.getResult().getVenues();
				if (nearVenues.length < 0) {
					request.setAttribute("error",
							"Sorry, your search returned no results");
				}
				request.setAttribute("nearVenues", nearVenues);
			} else {
				request.setAttribute("error", "Sorry, try again later");
			}
			request.setAttribute("nearVenues_result", "false");
			request.getRequestDispatcher("views/additional_features.jsp").forward(
					request, response);
		} catch (Exception e) {
			request.setAttribute("nearVenues_result", "false");
			request.setAttribute("error",
					"Sorry, your search yielded no results");
			request.getRequestDispatcher("views/additional_features.jsp").forward(
					request, response);
		}
	}
}
