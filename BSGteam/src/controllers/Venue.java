package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Foursquare;
import fi.foyt.foursquare.api.*;
import fi.foyt.foursquare.api.entities.*;

/**
 * Venue.java
 * 
 * This servlet handles venue related queries
 * 
 * @author BSG Team
 */
public class Venue extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// gets the particular venue id for which images are requested
		String id = request.getParameter("id");
		HttpSession session = request.getSession();

		if (id != null) {
			// creates a new foursquare object, calls getImages and passes the
			// obtained image urls to the view for display
			Foursquare foursquare = new Foursquare();
			request.setAttribute("images", foursquare.getImages(id, session
					.getAttribute("clientID").toString(),
					session.getAttribute("clinetSec").toString(), session
							.getAttribute("redirectURL").toString(), session
							.getAttribute("accessToken").toString()));
			request.getRequestDispatcher("views/venueImages.jsp").forward(
					request, response);
		} else {
			request.getRequestDispatcher("views/additional_features.jsp")
					.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// obtains the longitude and latitude parameters from the webform
		String log = request.getParameter("long");
		String lat = request.getParameter("lat");
		// initiates a connection to the Foursquare API
		HttpSession session = request.getSession();

		FoursquareApi foursquareApi = new FoursquareApi(session.getAttribute(
				"clientID").toString(), session.getAttribute("clinetSec")
				.toString(), session.getAttribute("redirectURL").toString());
		foursquareApi.setoAuthToken(session.getAttribute("accessToken")
				.toString());

		Result<VenuesSearchResult> result2 = null;
		String result = lat + "," + log;
		try {
			result2 = foursquareApi.venuesSearch(result, null, null, null,
					null, null, null, null, null, null, null);
		} catch (FoursquareApiException e) {
			e.printStackTrace();
		}
		// checks that response code is 200 (OK) before proceeding
		if (result2.getMeta().getCode() == 200) {

			for (CompactVenue venue : result2.getResult().getVenues()) {
				// retrieves the category(ies) of each venue
				String category = "";
				Category[] categoryList = venue.getCategories();

				for (Category cat : categoryList) {
					category = cat.getName();
				}
				// saves the venue query results to the database
				/*
				 * models.Database.venuesDB(venue.getName(), venue.getLocation()
				 * .getAddress(), venue.getUrl(), category);
				 */
			}
			CompactVenue[] venues = result2.getResult().getVenues();
			if (venues.length < 0) {
				request.setAttribute("error",
						"Sorry, your search returned no results");
			}
			// sends the list of venues as an attribute to the view for display
			request.setAttribute("venues", venues);
		} else {
			if (result2.getMeta().getCode() == 400) {
				request.setAttribute("error",
						"You have entered an invalid input");
			} else {
				request.setAttribute("error",
						"Sorry, an error has occured, please try again later");
			}
		}
		request.setAttribute("venues_result", "false");
		request.getRequestDispatcher("views/additional_features.jsp").forward(
				request, response);
	}
}
