package controllers;

import models.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fi.foyt.foursquare.api.*;
import fi.foyt.foursquare.api.entities.*;

/**
 * Venue.java
 * 
 * This is the servlet used to retrieve venues in a particular location and also
 * display their images (if present)
 * 
 */
public class Venue extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//gets the particular venue id for which images are requested
		String id = request.getParameter("id");
		//creates a new foursquare object, calls getImages and passes the obtained image urls to the view
		Foursquare fsq = new Foursquare();
		request.setAttribute("images", fsq.getImages(id));
		request.getRequestDispatcher("views/venueImages.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String log = request.getParameter("long");
		String lat = request.getParameter("lat");
		// String area =request.getParameter("area");
		String result = lat + "," + log;
		FoursquareApi foursquareApi = new FoursquareApi(
				"KTSRNGJZY4BGFSZAQYGKP2BBTZGGJAMXWKQSYTOSTV5WC31H",
				"4KYFXNFEMT5RAIO3DEXMBC52ALUQG3AIXJHGBDBNYISGTO1H",
				"https://twitter.com/Bbash184");
		foursquareApi
				.setoAuthToken("KCUQXPVAHFTVEJJC1JTZ3ETPIUYRN1JNA0CFZGY0S0310WUH");

		Result<VenuesSearchResult> result2 = null;
		try {
			result2 = foursquareApi.venuesSearch(result, null, null, null, null,
					null, null, null, null, null, null);

			if (result2.getMeta().getCode() == 200) {
				request.setAttribute("venues", result2.getResult().getVenues());

				request.getRequestDispatcher("views/queryInterface.jsp").forward(
						request, response);
			}
		} catch (FoursquareApiException e) {
			e.printStackTrace();
		}
	}
}
