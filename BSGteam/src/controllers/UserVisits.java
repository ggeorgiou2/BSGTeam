package controllers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fi.foyt.foursquare.api.entities.*;
import fi.foyt.foursquare.api.entities.Location;
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
			Twitter twitter = twitterConnection.init(
					session.getAttribute("customer_key").toString(), session
							.getAttribute("customer_secret").toString(),
					session.getAttribute("token_access").toString(), session
							.getAttribute("token_secret").toString());

			// retrieves the parameters passed from the web form
			// gets the id of the required twitter user to be tracked

			String userName = request.getParameter("userID");
			User thisUser = twitter.showUser(userName);
			long userid = thisUser.getId();
			int days = Integer.parseInt(request.getParameter("days"));
			final List<String> streams = new ArrayList<String>();
			// if number of days requested is greater than 0, then the twitter
			// database is
			// queried
			String root = getServletContext().getRealPath("/");
			File path = new File(root + "/Triple_store");
			if (!path.exists()) {
				path.mkdirs();
			}
			String filePath = path + "/";
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
					request.setAttribute("userVisits", foursquare.checkins(
							result,
							session.getAttribute("clientID").toString(),
							session.getAttribute("clinetSec").toString(),
							session.getAttribute("redirectURL").toString(),
							session.getAttribute("accessToken").toString()));

					List<Status> contacters = new ArrayList<Status>();
					List<Status> contactees = new ArrayList<Status>();
					List<Status> subResults = new ArrayList<Status>();

					List<Status> results = twitterConnection.getTimeline(
							userName, session.getAttribute("customer_key")
									.toString(),
							session.getAttribute("customer_secret").toString(),
							session.getAttribute("token_access").toString(),
							session.getAttribute("token_secret").toString());

					for (Status status : results) {
						if (status.getRetweetCount() > 0) {
							System.out.println("sure?" + status.getText());
							if (status.getRetweetedStatus() != null) {
								contacters.add(status.getRetweetedStatus());
							}
						}
					}

					for (Status status : results) {
						if (status.getRetweetCount() > 0) {
							subResults.add(status);
						}
					}
					if (subResults.size() > 10) {
						subResults = subResults.subList(0, 10);
					}
					int i = twitter.getRateLimitStatus()
							.get("/statuses/retweets/:id").getRemaining();
					if (i > 10) {
						for (Status status : subResults) {
							if (status.getRetweetCount() > 0) {
								System.out.println("sure?" + status.getText());
								contactees.addAll(twitter.getRetweets(status
										.getId()));
							}
						}

					} else {
						System.out.println("Exceeded retweet limit");
					}
					// System.out.println(twitter.getRateLimitStatus());

					// Get people user retweeted
					Set<String> contacts = new TreeSet<String>();
					if (contacters.size() > 0) {
						for (Status status : contacters) {
							contacts.add(status.getUser().getScreenName());
						}
					}

					Set<String> contacts2 = new TreeSet<String>();
					if (contactees.size() > 0) {
						for (Status status : contactees) {
							contacts2.add(status.getUser().getScreenName());
						}
					}
					System.out.println("2=" + contacts2);

					contacts.addAll(contacts2);

					Map<Date, CompactVenue> userVisits = foursquare.checkins(
							result,
							session.getAttribute("clientID").toString(),
							session.getAttribute("clinetSec").toString(),
							session.getAttribute("redirectURL").toString(),
							session.getAttribute("accessToken").toString());

					Jena jena = new Jena(filePath);

					ArrayList<String> visited = new ArrayList<String>();

					for (Entry<Date, CompactVenue> entry : userVisits
							.entrySet()) {
						Photo[] photos = foursquare.getImages(entry.getValue()
								.getId(), session.getAttribute("clientID")
								.toString(), session.getAttribute("clinetSec")
								.toString(), session
								.getAttribute("redirectURL").toString(),
								session.getAttribute("accessToken").toString());
						
						visited.add(entry.getValue().getName());
						String venueUrl = "";
						String url = entry.getValue().getUrl();
						if (url != null) {
							venueUrl = url;
						}
						String venueAddress = "";
						Location location = entry.getValue().getLocation();
						if (location.getAddress() != null) {
							venueAddress = location.getAddress();
						} else {
							venueAddress = "Longitude: " + location.getLat()
									+ " Latitude: " + location.getLng();
						}
						jena.saveVenue(userName, entry.getValue().getName(),
								photos, entry.getValue().getCategories(),
								venueAddress, entry.getValue().getStats()
										.getUsersCount().toString(), venueUrl,
								entry.getKey().toString());

					}

					jena.saveUser(thisUser.getName(), thisUser.getScreenName(),
							thisUser.getLocation(),
							thisUser.getProfileImageURL(),
							thisUser.getDescription(), visited, contacts);
					if (userVisits.isEmpty()) {
						request.setAttribute("error",
								"Sorry, your search returned no results");
					}
					request.setAttribute("user", userName);
					request.setAttribute("userVisits", userVisits);
				} else {
					request.setAttribute("error", "Sorry, limit exceeded");
				}
				request.setAttribute("userVisits_result", "true");
				request.getRequestDispatcher("views/queryInterface.jsp")
						.forward(request, response);

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
				final Jena jena = new Jena(filePath);

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
						String newtext = status.getText();
						System.out.println("sta= " + newtext);
						Foursquare foursquare = new Foursquare();
						Map<Date, CompactVenue> userVisits = foursquare
								.checkins(status, clientID, clientSecret,
										redirectURL, accessToken);
						for (Entry<Date, CompactVenue> entry : userVisits
								.entrySet()) {
							Photo[] photos = foursquare.getImages(entry
									.getValue().getId(), clientID,
									clientSecret, redirectURL, accessToken);
							String venueUrl = "";
							String url = entry.getValue().getUrl();
							if (url != null) {
								venueUrl = url;
							}
							String venueAddress = "";
							Location location = entry.getValue().getLocation();
							if (location.getAddress() != null) {
								venueAddress = location.getAddress();
							} else {
								venueAddress = "Longitude: "
										+ location.getLat() + " Latitude: "
										+ location.getLng();
							}
							Database.userTweetsDB(user, entry.getValue()
									.getName(), entry.getKey().toString());
							jena.saveVenue(user, entry.getValue().getName(),
									photos, entry.getValue().getCategories(),
									venueAddress, entry.getValue().getStats()
											.getUsersCount().toString(),
									venueUrl, entry.getKey().toString());
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
				response.sendRedirect("streams?user=" + userName);
			}
			request.setAttribute("userVisits_result", "true");

		} catch (Exception err) {
			err.printStackTrace();
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
