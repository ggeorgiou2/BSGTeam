package controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Foursquare;
import fi.foyt.foursquare.api.*;
import fi.foyt.foursquare.api.entities.*;

/**
 * Servlet implementation class Venue
 */
public class Venue extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

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
		//String venueSearch = null;
		//String address = null;
		//String id = null;
		try {
			result2 = foursquareApi.venuesSearch(result, null, null, null,
					null, null, null, null, null, null, null);

		} catch (FoursquareApiException e) {
			e.printStackTrace();
		}
		if (result2.getMeta().getCode() == 200) {
			request.setAttribute("venues", result2.getResult().getVenues());
			request.getRequestDispatcher("views/queryInterface.jsp").forward(
					request, response);
		}
		System.out.println("fdsf");
			for (CompactVenue venue : result2.getResult().getVenues()) {
				models.database.venuesDB(venue.getName(),  venue.getLocation().getAddress(), venue.getUrl(), "description??");	
			}
//				venueSearch = venue.getName();
//				address = venue.getLocation().getAddress();
//				id = venue.getId();
//				
//				/*
//				 * Result<PhotoGroup> venuephoto = null; try { venuephoto =
//				 * foursquareApi.venuesPhotos(id, null, null, null); } catch
//				 * (FoursquareApiException e) { 
//				 * block e.printStackTrace(); }
//				 */
//				// Photo[] image =venuephoto.getResult()..getItems();
//				// out.println("<image src="image">");
//				out.println("Venue Name: " + venueSearch + "<br />");
//				out.println("venue Id: " + id + "<br />");
//				out.println(" address: " + address + "<br />");
//				out.println("post code: " + venue.getLocation().getPostalCode()
//						+ "<br />");
//				out.println("<br />");
			
	
//		} else {
//			System.out.println("Error occured: ");
//			out.println("  code: " + result2.getMeta().getCode());
//			out.println("  type: " + result2.getMeta().getErrorType());
//			out.println("  detail: " + result2.getMeta().getErrorDetail());
//		}
	}
}
