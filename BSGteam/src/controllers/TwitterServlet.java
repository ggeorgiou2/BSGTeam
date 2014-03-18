package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import models.*;
import twitter4j.*;

/**
 * TwitterServlet.java
 * 
 * This is the controller for tracking public discussions on specific topics (e.g. via hashtags or keywords)
 *
 */
public class TwitterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
				response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//Creates a new TwitterBean object so as to initiate a connection to the twitter API 
			TwitterBean tt = new TwitterBean();
			Twitter twitter = tt.init();
			// System.out.println(twitter.getRateLimitStatus().keySet().toString());
			// System.out.println(twitter.getRateLimitStatus().toString());

			//requests the parameters submitted from the webform
			String tweet = request.getParameter("tweetData");
			Query query = new Query(tweet);
			String longitude = request.getParameter("long");
			String latitude = request.getParameter("lat");
			String area = request.getParameter("area");
			
			QueryResult result;
			
			//if user doesn't restrict results to a particular location
			if (longitude.isEmpty() || latitude.isEmpty() || area.isEmpty()) {
				result = twitter.search(query);
			} else {
				//user has requested a particular location
				double log = Double.parseDouble(longitude);
				double lat = Double.parseDouble(latitude);
				double radius = Double.parseDouble(area);
				query.setGeoCode(new GeoLocation(lat, log), radius, Query.KILOMETERS);
				result = twitter.search(query);
			}
			
			// int i =
			// twitter.getRateLimitStatus().get("/statuses/retweets/:id").getRemaining();
			// System.out.println(i);

			//get the search results and pass it to the view for display 
			List<Status> tweets = result.getTweets();
			request.setAttribute("statuses", tweets);
			request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
					response);

		} catch (Exception err) {
			System.out.println("Error while tweeting: " + err.getMessage());
			request.setAttribute("statuses", err.getMessage());
			request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
					response);
		}
	}
}
