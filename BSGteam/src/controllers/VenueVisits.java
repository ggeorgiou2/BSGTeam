package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import models.*;
import twitter4j.*;

//import java.io.PrintWriter;

/**
 * TwitterServlet.java
 * 
 * This is a servlet controller class for tracking public
 * discussions on specific topics
 * 
 * @author BSG Team
 * 
 */
public class VenueVisits extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//request.getRequestDispatcher("views/queryInterface.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			// instantiates a new object of the <code>TwitterBean</code> class
			TwitterBean twitterConnection = new TwitterBean();
			Twitter twitter = twitterConnection.init();
			
			System.out.println("1");
			
			// searches for tweets that contain foursquare checkins
			String tweet = "Foursquare";
			
			System.out.println("1.1");
			
			// creates a new twitter query
			Query query = new Query(tweet);
			
			
			System.out.println("1.2");
			
			// gets the longitude and latitude of the geographical location
			String longitude = request.getParameter("long");
			String latitude = request.getParameter("lat");
			
			System.out.println("1.3");
			

			
			int days = Integer.parseInt(request.getParameter("days"));

			System.out.println(longitude  + " | " + latitude  + " | " + days);

			
			System.out.println("1.4");
			
			
			long DAY_IN_MS = 1000 * 60 * 60 * 24;
			Date date = new Date(System.currentTimeMillis()
					- (days * DAY_IN_MS));
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
					"yyyy-MM-dd");

			String since = dateformatyyyyMMdd.format(date);
			query.setSince(since); // YYYY-MM-DD

			QueryResult result = null;
			// if location is empty, return results based on just the topic of
			// discussion
			double log = Double.parseDouble(longitude);
			double lat = Double.parseDouble(latitude);
			double radius = 100;
			// restrict the query to the specified location coordinates
			query.setGeoCode(new GeoLocation(lat, log), radius,
					Query.KILOMETERS);
			// search twitter for the query
			result = twitter.search(query);
			//get checkin information from Foursquare
			Foursquare foursquare = new Foursquare();
/*			request.setAttribute("checkins", foursquare.venueCheckins(result));
			request.getRequestDispatcher("views/queryInterface.jsp").forward(
					request, response);*/
			System.out.println("2");
			response.getWriter().println("<tr><td> test </td></tr>");
			
			// get the tweets returned in the query result
		} catch (Exception err) {
			System.out.println("Error while tweeting: " + err.getMessage());
			response.getWriter().println("<tr><td> error occured </td></tr>");
		}
	}
}