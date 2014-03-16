package controllers;

import java.io.IOException;
import java.util.ArrayList;
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
			/*System.out.println(twitter.getRateLimitStatus()
					.get("/statuses/retweets/:id").getRemaining());
			System.out.println(twitter.getRateLimitStatus()
					.get("/search/tweets").getRemaining());*/

			String tweet = request.getParameter("tweetData");
			Query query = new Query(tweet);
			String longitude = request.getParameter("long");
			String latitude = request.getParameter("lat");
			String area = request.getParameter("area");
			QueryResult result;
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
			//int i = twitter.getRateLimitStatus().get("/statuses/retweets/:id").getRemaining();
			//System.out.println(i);
//			
//				// Foursquare temp = new Foursquare();
//				// for(Status status : tweets){
//
//				// int index = status.getText().indexOf("http");
//				// String aaa = "nan";
//				// if (index >= 0)
//				// {
//				// aaa = status.getText().substring(index);
//				// //temp.authenticate();
//				// //String abb = temp.expandUrl(aaa);
//				// //System.out.println(aaa + " = " + abb);
//				// System.out.println("Initiating foursquare");
//				// temp.getLocationInformation(aaa);
//				// }
//				// }
//			} else {
//				System.out.println("Exceeded your limit");
//			}

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
