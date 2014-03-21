package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Foursquare;
import fi.foyt.foursquare.api.*;
import fi.foyt.foursquare.api.entities.*;

/**
 * Venue.java This servlet handles venue related queries
 * 
 * @author BSG Team
 */
public class Venue extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// gets the particular venue id for which images are requested
		String id = request.getParameter("id");
		if (id != null) {
			// creates a new foursquare object, calls getImages and passes the
			// obtained image urls to the view for display
			Foursquare fsq = new Foursquare();
			request.setAttribute("images", fsq.getImages(id));
			request.getRequestDispatcher("views/venueImages.jsp").forward(request,
					response);
		} else {
			request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
					response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//obtains the longitude and latitude parameters from the webform
		String log = request.getParameter("long");
		String lat = request.getParameter("lat");
		//initiates a connection to the Foursquare API
		FoursquareApi foursquareApi = new FoursquareApi(
				"KTSRNGJZY4BGFSZAQYGKP2BBTZGGJAMXWKQSYTOSTV5WC31H",
				"4KYFXNFEMT5RAIO3DEXMBC52ALUQG3AIXJHGBDBNYISGTO1H",
				"https://twitter.com/Bbash184");
		foursquareApi
				.setoAuthToken("KCUQXPVAHFTVEJJC1JTZ3ETPIUYRN1JNA0CFZGY0S0310WUH");

		Result<VenuesSearchResult> result2 = null;
		String result = lat + "," + log;
		try {
			result2 = foursquareApi.venuesSearch(result, null, null, null, null,
					null, null, null, null, null, null);
		} catch (FoursquareApiException e) {
			e.printStackTrace();
		}
		//checks that response code is 200 (OK) before proceeding
		if (result2.getMeta().getCode() == 200) {
			//saves the venue query results to the database
			for (CompactVenue venue : result2.getResult().getVenues()) {
				String category = "";
				Category[] categoryList = venue.getCategories();
				for (Category cat:categoryList)
				{
					category = cat.getName();
				}
				models.Database.venuesDB(venue.getName(), venue.getLocation()
						.getAddress(), venue.getUrl(), category);
			}
			//sends the list of venues as an attribute to the view for display
			request.setAttribute("venues", result2.getResult().getVenues());
			request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
					response);
		}
		
	}
}
