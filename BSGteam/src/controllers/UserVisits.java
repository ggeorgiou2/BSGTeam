package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.CompactVenue;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import models.*;

/**
 * UserVisits.java
 * 
 * This class tracks the locations which a user has visited
 * 
 * @author BSG Team
 * 
 */
public class UserVisits extends HttpServlet {
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
	protected void doPost(final HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			// establishes a twitter connection
			TwitterBean twitterConnection = new TwitterBean();
			HttpSession session = request.getSession();
			Twitter twitter = twitterConnection.init(session.getAttribute("customer_key").toString(), session.getAttribute("customer_secret").toString(), session.getAttribute("token_access").toString(), session.getAttribute("token_secret").toString());

			// retrieves the parameters passed from the web form
			// gets the id of the required twitter user to be tracked

			String userName = request.getParameter("userID");
			long userid = twitter.showUser(userName).getId();
			int days = Integer.parseInt(request.getParameter("days"));
			final List<String> streams = new ArrayList<String>();
			// if number of days requested is greater than 0, then the twitter
			// database is
			// queried
			if (days > 0) {
				// gets the date from which previous tweets should be retrieved
				// and
				// inspected for checkins
				int remaining = twitter.getRateLimitStatus()
						.get("/search/tweets").getRemaining();
				if (remaining > 1) {
					long DAY_IN_MS = 1000 * 60 * 60 * 24;
					Date date = new Date(System.currentTimeMillis()
							- (days * DAY_IN_MS));
					SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
							"yyyy-MM-dd");

					String since = dateformatyyyyMMdd.format(date);
					String user = "from:" + userName;
					Query query = new Query(user);
					query.setSince(since); // YYYY-MM-DD
					QueryResult result = twitter.search(query);
					// creates a foursquare object and inspects the user's tweet
					// for
					// foursquare checkins

					Foursquare foursquare = new Foursquare();
					Map<Date, CompactVenue> userVisits = foursquare.checkins(result, session.getAttribute("clientID").toString(), session.getAttribute("clinetSec").toString(), session.getAttribute("redirectURL").toString(), session.getAttribute("accessToken").toString());
					if (userVisits.isEmpty()) {
						request.setAttribute("error",
								"Sorry, your search returned no results");
					}
					request.setAttribute("user", userName);
					request.setAttribute("userVisits", userVisits);
				} else {
					request.setAttribute("error", "Sorry, limit exceeded");
				}

				// for (CompactVenue venue : foursquare.checkins(result)) {
				// //saves the venue query results to the database
				// Database.userVisitsDB(userName, venue.getName());
				//
				// }

			} else {
				// use streaming api to get results for days = 0
				String token_access = session.getAttribute("token_access").toString();
				String token_secret = session.getAttribute("token_secret").toString();
				String customer_key = session.getAttribute("customer_key").toString();
				String customer_sercet = session.getAttribute("customer_sercet").toString();

				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.setDebugEnabled(true).setOAuthConsumerKey(customer_key)
						.setOAuthConsumerSecret(customer_sercet)
						.setOAuthAccessToken(token_access)
						.setOAuthAccessTokenSecret(token_secret);

				TwitterStreamFactory twitter2 = new TwitterStreamFactory(
						cb.build());
				final TwitterStream twitterStream = twitter2.getInstance();
				final String user = userName;
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
						HttpSession session = request.getSession();
						String newtext = status.getText();
						System.out.println("sta= " + newtext);
						Foursquare foursquare = new Foursquare();
						Map<Date, CompactVenue> userVisits = foursquare
								.checkins(status, session.getAttribute("clientID").toString(), session.getAttribute("clinetSec").toString(), session.getAttribute("redirectURL").toString(), session.getAttribute("accessToken").toString());
						for (Entry<Date, CompactVenue> entry : userVisits
								.entrySet()) {
							Database.userTweetsDB(user, entry.getValue()
									.getName(), entry.getKey().toString());
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

				twitterStream.addListener(listener);

				// sets the variables required to track
				int count = 0;
				long[] idToFollow = new long[1];
				idToFollow[0] = userid;
				String[] stringsToTrack = null;
				// stringsToTrack[0] = "foursquare";
				double[][] locationsToTrack = new double[0][0];

				twitterStream.filter(new FilterQuery(count, idToFollow,
						stringsToTrack, locationsToTrack));

				streams.add("Tracking user");
				System.out.println("Started");
				request.setAttribute("userVisits2", streams);
				request.setAttribute("userVisits_result", "true");

				response.sendRedirect("http://localhost:8080/BSGteam/streams?user="
						+ userName);
			}
			request.setAttribute("userVisits_result", "true");

		} catch (Exception err) {
			request.setAttribute("userVisits_result", "false");
			request.setAttribute("error",
					"Sorry, your search returned no results");
			System.out.println("Error " + err.getStackTrace());
			request.getRequestDispatcher("views/queryInterface.jsp").forward(
					request, response);
		}
	}

	private void stopStream(TwitterStream twitterStream) {
		twitterStream.cleanUp();
		twitterStream = null;
	}
}
