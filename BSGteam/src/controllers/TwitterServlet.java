package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import models.*;
import twitter4j.*;

//import java.io.PrintWriter;

public class TwitterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("views/queryInterface.jsp").forward(
				request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Initiating connection");
		try {

			TwitterBean tt = new TwitterBean();
			Twitter twitter = tt.init();
			// System.out
			// .println(twitter.getRateLimitStatus().keySet().toString());
			// System.out.println(twitter.getRateLimitStatus().toString());
			// System.out.println(twitter.getRateLimitStatus()
			// .get("/search/tweets").getRemaining());

			String tweet = request.getParameter("tweetData");
			Query query = new Query(tweet);
			String longitude = request.getParameter("long");
			String latitude = request.getParameter("lat");
			String area = request.getParameter("area");
			QueryResult result;
			String resultString = "";

			if (longitude.isEmpty() || latitude.isEmpty() || area.isEmpty()) {
				result = twitter.search(query);
			} else {
				double log = Double.parseDouble(longitude);
				double lat = Double.parseDouble(latitude);
				double radius = Double.parseDouble(area);
				query.setGeoCode(new GeoLocation(lat, log), radius,
						Query.KILOMETERS);
				result = twitter.search(query);
			}

			List<Status> tweets = result.getTweets();
			
			for (Status tweet1 : tweets) { // /gets the user
				models.database.twitterDB(tweet1.getUser().getOriginalProfileImageURL(), tweet1.getUser().getName(), tweet1.getUser().getLocation(), tweet1.getUser().getDescription(), tweet1.getText(), tweet1.getRetweetCount());
				//System.out.println(tweet1.getUser().getOriginalProfileImageURL());
			}
			
			request.setAttribute("statuses", tweets);
			request.getRequestDispatcher("views/queryInterface.jsp").forward(
					request, response);
			
	

			// int i =
			// twitter.getRateLimitStatus().get("/statuses/retweets/:id").getRemaining();
			// System.out.println(i);


		} catch (Exception err) {
			System.out.println("Error while tweeting: " + err.getMessage());
			request.setAttribute("statuses", err.getMessage());
			request.getRequestDispatcher("views/queryInterface.jsp").forward(
					request, response);
		}
	}
}
