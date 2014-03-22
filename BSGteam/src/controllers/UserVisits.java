package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.CompactVenue;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import models.*;

/**
 * UserVisits.java This class tracks the locations which a user has visited
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// establishes a twitter connection
			TwitterBean twitterConnection = new TwitterBean();
			Twitter twitter = twitterConnection.init();
			// retrieves the parameters passed from the web form
			//gets the id of the required twitter user to be tracked
			String userName = request.getParameter("userID");
			long userid = twitter.showUser(userName).getId();
			
			int days = Integer.parseInt(request.getParameter("days"));
			final List<String> streams = new ArrayList<String>();
			// if number of days requested is greater than 0, then the twitter database is
			// queried
			if (days > 0) {
				// gets the date from which previous tweets should be retrieved and
				// inspected for checkins
				long DAY_IN_MS = 1000 * 60 * 60 * 24;
				Date date = new Date(System.currentTimeMillis() - (days * DAY_IN_MS));
				SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

				String since = dateformatyyyyMMdd.format(date);
				String user = "from:" + userName;
				Query query = new Query(user);
				query.setSince(since); // YYYY-MM-DD
				QueryResult result = twitter.search(query);
				// creates a foursquare object and inspects the user's tweet for
				// foursquare checkins
				Foursquare foursquare = new Foursquare();
				request.setAttribute("userVisits", foursquare.checkins(result));			
				
				for (CompactVenue venue :  foursquare.checkins(result)) {
					//retrieves the category(ies) of each venue
					String category = "";
					Category[] categoryList = venue.getCategories();
					for (Category cat:categoryList)
					{
						category = cat.getName();
					}
					//saves the venue query results to the database
					Database.userVisitsDB(userName, venue.getName());

				}
				
				
			} else {
				// use streaming api to get results for days = 0
				String token_access = "263885132-oDic38nO96k91obUMBypD2V7gBkd664DPCSszpHa";
				String token_secret = "7XPXklqAiP18xdw0kfQImShEWYf06fmVVveIfboAghRvT";
				String customer_key = "MXH39rOd9mOxRWh9Exma7g";
				String customer_sercet = "789P2oTcZL9liV2DhGjiDX8J7ZGXPwZRCCoWrSeVkoo";

				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.setDebugEnabled(true).setOAuthConsumerKey(customer_key)
						.setOAuthConsumerSecret(customer_sercet)
						.setOAuthAccessToken(token_access)
						.setOAuthAccessTokenSecret(token_secret);

				TwitterStreamFactory twitter2 = new TwitterStreamFactory(cb.build());
				TwitterStream twitterStream = twitter2.getInstance();
				StatusListener listener = new StatusListener() {

					@Override
					public void onException(Exception ex) {
						ex.printStackTrace();
					}

					@Override
					public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
						System.out.println("Got a status deletion notice id:"
								+ statusDeletionNotice.getStatusId());
					}

					@Override
					public void onScrubGeo(long userId, long upToStatusId) {
						System.out.println("Got scrub_geo event userId:" + userId
								+ " upToStatusId:" + upToStatusId);
					}

					@Override
					public void onStallWarning(StallWarning warning) {
						System.out.println("Got stall warning:" + warning);
					}

					@Override
					public void onStatus(Status status) {
						String newtext = status.getText();
						streams.add(newtext);
						System.out.println("sta= " + newtext);
						//To-do: inspect tweet for fourquare checkins
					}

					@Override
					public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
						System.out.println("Got track limitation notice:"
								+ numberOfLimitedStatuses);
					}
				};

				twitterStream.addListener(listener);

				// sets the variables required to track
				int count = 0;
				long[] idToFollow = new long[1];
				idToFollow[0] = userid;
				String[] stringsToTrack = new String[1];
				stringsToTrack[0] = "foursquare";
				double[][] locationsToTrack = new double[0][0];

				twitterStream.filter(new FilterQuery(count, idToFollow, stringsToTrack,
						locationsToTrack));
				streams.add("Tracking user");
				request.setAttribute("userVisits2", streams);
			}
			request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
					response);
		} catch (Exception err) {
			System.out.println("Error " + err.getStackTrace());
			request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
					response);
		}
	}

}
