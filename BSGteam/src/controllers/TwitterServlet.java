package controllers;

import java.io.File;
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

			HttpSession session = request.getSession();
			// if (session.getAttribute("twitterToken") != null) {
			// System.out.println("3");
			String token_access = (String) session.getAttribute("token_access");
			String token_secret = (String) session.getAttribute("token_secret");
			String customer_key = (String) session.getAttribute("customer_key");
			String customer_secret = (String) session
					.getAttribute("customer_secret");

			// instantiates a new object of the <code>TwitterBean</code> class
			TwitterBean twitterConnection = new TwitterBean();
			Twitter twitter = twitterConnection.init(customer_key,
					customer_secret, token_access, token_secret);

			// gets the required topic from the input webform
			String tweet = request.getParameter("tweetData");
			// creates a new twitter query
			Query query = new Query(tweet);
			query.setCount(25);

			// gets the longitude and latitude of the geographical location if
			// specified
			String longitude = request.getParameter("long");
			String latitude = request.getParameter("lat");
			String area = request.getParameter("area");
			// System.out.println(twitter.getRateLimitStatus().get("/statuses/retweets/:id").getRemaining());
			QueryResult result = null;
			List<Status> tweets = null;
			// if location is empty, return results based on just the topic of
			// discussion
			int remaining = twitter.getRateLimitStatus().get("/search/tweets")
					.getRemaining();
			if (remaining > 1) {
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
				tweets = result.getTweets();
				if (tweets.isEmpty()) {
					request.setAttribute("error",
							"Sorry, your search returned no results");
				}
				// loop through the results and save the users and tweets to the
				// database

				String root = getServletContext().getRealPath("/");
				File path = new File(root + "/Triple_store");
				if (!path.exists()) {
					path.mkdirs();
				}

				String filePath = path + "/";
				System.out.println(path.getAbsolutePath());
				Jena jena = new Jena(filePath);
				for (Status tweet1 : tweets) {
					jena.saveUser(tweet1.getUser().getName(), tweet1.getUser()
							.getScreenName(), tweet1.getUser().getLocation(),
							tweet1.getUser().getProfileImageURL(), tweet1
									.getUser().getDescription(), null, null);
				}

			} else {
				request.setAttribute("error", "Sorry limit exceeded");
			}
			request.setAttribute("statuses_result", "true");
			request.setAttribute("statuses", tweets);
		} catch (Exception err) {
			System.out.println("Error while tweeting: " + err.getMessage());
			request.setAttribute("error",
					"Sorry, an error occurred. Please try again later");
		}
		request.getRequestDispatcher("views/queryInterface.jsp").forward(
				request, response);
	}
}
