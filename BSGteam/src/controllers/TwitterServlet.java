package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import models.*;
import twitter4j.*;

//import java.io.PrintWriter;

/**
 * TwitterServlet.java
 * 
 * This is a servlet controller class for tracking public discussions on
 * specific topics
 * 
 * @author BSG Team
 * 
 */
public class TwitterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("views/queryInterface.jsp").forward(
				request, response);
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

			// gets the required topic from the input webform
			String tweet = request.getParameter("tweetData");
			// creates a new twitter query
			Query query = new Query(tweet);

			// gets the longitude and latitude of the geographical location if
			// specified
			String longitude = request.getParameter("long");
			String latitude = request.getParameter("lat");
			String area = request.getParameter("area");

			QueryResult result = null;
			// if location is empty, return results based on just the topic of
			// discussion
			if (longitude.isEmpty() || latitude.isEmpty() || area.isEmpty()) {
				// search twitter for the query
				result = twitter.search(query);
			} else {
				double log = Double.parseDouble(longitude);
				double lat = Double.parseDouble(latitude);
				double radius = Double.parseDouble(area);
				// restrict the query to the specified location coordinates
				query.setGeoCode(new GeoLocation(lat, log), radius,
						Query.KILOMETERS);
				// search twitter for the query
				result = twitter.search(query);
			}

			// get the tweets returned in the query result
			List<Status> tweets = result.getTweets();
			// loop through the results and save the users and tweets to the
			// database
			for (Status tweet1 : tweets) {
				Database.twitterDB(tweet1.getUser()
						.getOriginalProfileImageURL(), tweet1.getUser()
						.getScreenName(), tweet1.getUser().getLocation(),
						tweet1.getUser().getDescription(), tweet1.getText(),
						tweet1.getRetweetCount());
				
				response.getWriter().println("<tr><td><img src='" + tweet1.getUser().getOriginalProfileImageURL() + "' height='100' width='100'>" + 
						"<td>" + tweet1.getUser().getScreenName() +
						"<td>" + tweet1.getUser().getLocation() +
						"<td>" + tweet1.getUser().getDescription() +
						"<td>" + tweet1.getText() +
						"<td>" + tweet1.getRetweetCount() + "</td></tr>");
			}

			// sends the list of tweets to the display interface
			//request.setAttribute("statuses", tweets);
			//request.getRequestDispatcher("views/queryInterface.jsp").forward(request, response);
		} catch (Exception err) {
			//System.out.println("Error while tweeting: " + err.getMessage());
			//request.setAttribute("statuses", err.getMessage());
			//request.getRequestDispatcher("views/queryInterface.jsp").forward(
				//	request, response);
		}
	}
}
