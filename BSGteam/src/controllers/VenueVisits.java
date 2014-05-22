package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import fi.foyt.foursquare.api.entities.Checkin;
import models.*;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

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
public class VenueVisits extends HttpServlet {
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

			HttpSession session = request.getSession();

			Twitter twitter = twitterConnection.init(
					session.getAttribute("customer_key").toString(), session
							.getAttribute("customer_secret").toString(),
					session.getAttribute("token_access").toString(), session
							.getAttribute("token_secret").toString());

			String longitude = request.getParameter("long");
			String latitude = request.getParameter("lat");

			int days = Integer.parseInt(request.getParameter("days"));

			long DAY_IN_MS = 1000 * 60 * 60 * 24;
			Date date = new Date(System.currentTimeMillis()
					- (days * DAY_IN_MS));
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
					"yyyy-MM-dd");
			double log = Double.parseDouble(longitude);
			double lat = Double.parseDouble(latitude);
			double radius = 100;
			String since = dateformatyyyyMMdd.format(date);
			if (days > 0) {
				int remaining = twitter.getRateLimitStatus()
						.get("/search/tweets").getRemaining();
				if (remaining > 1) {
					// searches for tweets that contain foursquare checkins
					String tweet = "Foursquare";
					// creates a new twitter query
					Query query = new Query(tweet);
					// gets the longitude and latitude of the geographical
					// location

					query.setSince(since); // YYYY-MM-DD

					QueryResult result = null;
					// if location is empty, return results based on just the
					// topic
					// of
					// discussion

					// restrict the query to the specified location coordinates
					query.setGeoCode(new GeoLocation(lat, log), radius,
							Query.KILOMETERS);
					// search twitter for the query
					result = twitter.search(query);

					// get checkin information from Foursquare
					Foursquare foursquare = new Foursquare();
					if (foursquare.venueCheckins(result,
							session.getAttribute("clientID").toString(),
							session.getAttribute("clinetSec").toString(),
							session.getAttribute("redirectURL").toString(),
							session.getAttribute("accessToken").toString())
							.isEmpty()) {
						request.setAttribute("error",
								"Sorry, your search returned no results");
					}
					request.setAttribute("checkins", foursquare.venueCheckins(
							result,
							session.getAttribute("clientID").toString(),
							session.getAttribute("clinetSec").toString(),
							session.getAttribute("redirectURL").toString(),
							session.getAttribute("accessToken").toString()));
				} else {
					request.setAttribute("error", "Sorry, limit exceeded");
				}
				request.setAttribute("checkins_result", "true");
				request.getRequestDispatcher("views/queryInterface.jsp")
						.forward(request, response);
				// get the tweets returned in the query result
			} else {
				// use streaming api to get results for days = 0
				String token_access = session.getAttribute("token_access")
						.toString();
				String token_secret = session.getAttribute("token_secret")
						.toString();
				String customer_key = session.getAttribute("customer_key")
						.toString();
				String customer_sercet = session
						.getAttribute("customer_secret").toString();

				final String clientID = session.getAttribute("clientID")
						.toString();
				final String clientSecret = session.getAttribute("clinetSec")
						.toString();
				final String redirectURL = session.getAttribute("redirectURL")
						.toString();
				final String accessToken = session.getAttribute("accessToken")
						.toString();

				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.setDebugEnabled(true).setOAuthConsumerKey(customer_key)
						.setOAuthConsumerSecret(customer_sercet)
						.setOAuthAccessToken(token_access)
						.setOAuthAccessTokenSecret(token_secret);

				TwitterStreamFactory twitter2 = new TwitterStreamFactory(
						cb.build());
				final TwitterStream twitterStream = twitter2.getInstance();
				StatusListener listener = new StatusListener() {

					@Override
					public void onException(Exception ex) {
						ex.printStackTrace();
					}

					@Override
					public void onDeletionNotice(
							StatusDeletionNotice statusDeletionNotice) {
						System.out.println("Got a status deletion notice id:"
								+ statusDeletionNotice.getStatusId());
					}

					@Override
					public void onScrubGeo(long userId, long upToStatusId) {
						System.out.println("Got scrub_geo event userId:"
								+ userId + " upToStatusId:" + upToStatusId);
					}

					@Override
					public void onStallWarning(StallWarning warning) {
						System.out.println("Got stall warning:" + warning);
					}

					@Override
					public void onStatus(Status status) {
						String newtext = status.getText();
						System.out.println("sta= " + newtext);
						Foursquare foursquare = new Foursquare();
						Map<Date, Checkin> userVisits = foursquare
								.venueCheckins(status, clientID, clientSecret,
										redirectURL, accessToken);
						for (Entry<Date, Checkin> entry : userVisits.entrySet()) {
							Database.userTweetsDB(entry.getValue().getUser()
									.getFirstName(), entry.getValue()
									.getVenue().getName(), entry.getKey()
									.toString());
						}
						if (Database.getStreamStop()) {
							stopStream(twitterStream);
						}
					}

					@Override
					public void onTrackLimitationNotice(
							int numberOfLimitedStatuses) {
						System.out.println("Got track limitation notice:"
								+ numberOfLimitedStatuses);
					}
				};

				Database.deleteFromStop();
				twitterStream.addListener(listener);

				double lat1 = lat - 0.1;
				double longi1 = log - 0.3;
				double lat2 = lat + 0.1;
				double longi2 = log + 0.3;

				double[][] bb = { { longi1, lat1 }, { longi2, lat2 } };

				FilterQuery fq = new FilterQuery();
				fq.locations(bb);
				twitterStream.filter(fq);
				System.out.println("Started");
				response.sendRedirect("streams?venue=Longitude:" + log
						+ ", Latitude:" + lat);
			}
		} catch (Exception err) {
			request.setAttribute("error",
					"Sorry, your search returned no results");
			System.out.println("Error while tweeting: " + err.getMessage());
		}
	}

	private void stopStream(TwitterStream twitterStream) {

		twitterStream.cleanUp();
		twitterStream = null;
	}
}
