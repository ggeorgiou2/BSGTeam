package controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fi.foyt.foursquare.api.*;
import fi.foyt.foursquare.api.entities.*;

/**
 * Servlet implementation class NearVenue
 */
public class NearVenue extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.getRequestDispatcher("views/nearvenue.html").forward(request,response);
		request.getRequestDispatcher("views/queryInterface.jsp").forward(
				request, response);
		//doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();		
		String venueId = request.getParameter("venueID");	
		FoursquareApi foursquareApi = new FoursquareApi(
				"KTSRNGJZY4BGFSZAQYGKP2BBTZGGJAMXWKQSYTOSTV5WC31H",
				"4KYFXNFEMT5RAIO3DEXMBC52ALUQG3AIXJHGBDBNYISGTO1H",
				"https://twitter.com/Bbash184");
		foursquareApi.setoAuthToken("KCUQXPVAHFTVEJJC1JTZ3ETPIUYRN1JNA0CFZGY0S0310WUH");		
		Double lat = 0.0;
		Double log = 0.0;
		String country = null;
		try {
		Location location = foursquareApi.venue(venueId).getResult().getLocation();
			lat = location.getLat();
			log = location.getLng();
			country = location.getCountry();	
			
			out.println("lat: " + lat + " log: " + log + " country: "+country +"<br />");
			out.println("<br />");
		
			String stringLat = Double.toString(lat);
			String stringLog = Double.toString(log);
			
			String result = stringLat +","+stringLog;
			Result<VenuesSearchResult> result2 = null;
			//String venueSearch = null;
			
			result2 = foursquareApi.venuesSearch(result,null, null, null, null, null, null, null, null, null, null);

			if (result2.getMeta().getCode() == 200 ) {
				
				request.setAttribute("nearVenues", result2.getResult().getVenues());
				request.getRequestDispatcher("views/queryInterface.jsp").forward(
						request, response);
//				
//				for (CompactVenue venue : result2.getResult().getVenues()) {
//					venueSearch = venue.getName();
//					out.println(venueSearch +"\t\t venue Id:  "+  venue.getId()+ "<br />");
//					out.println("<br />");
//				}
//			} else {	
//				System.out.println("Error occured: ");
//				out.println("  code: " + result2.getMeta().getCode());
//			    out.println("  type: " + result2.getMeta().getErrorType());
//				out.println("  detail: " + result2.getMeta().getErrorDetail());
//				
//			}
			}
		} catch (FoursquareApiException e) {
			//request.setAttribute("statuses", e.getMessage());
			request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
					response);
			//e.printStackTrace();
		} catch (NullPointerException err) {
			request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
					response);
		}
	}

}
