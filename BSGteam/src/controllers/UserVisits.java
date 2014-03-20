package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import models.*;

/**
 * UserVisits.java
 * 
 *
 */
public class UserVisits extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
				response); // doPost(request, response);
	}

	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException, IOException {
		try {
			//establishes a twitter connection
			TwitterBean tt = new TwitterBean();
			Twitter twitter = tt.init();
			String userID = request.getParameter("userID");
			long useridd = twitter.showUser(userID).getId();
			int days = Integer.parseInt(request.getParameter("days"));
			final List<String> streams = new ArrayList<String>();
			int ccc = streams.size();


			if (days > 0) {	
				long DAY_IN_MS = 1000 * 60 * 60 * 24;
				Date date = new Date(System.currentTimeMillis() - (days * DAY_IN_MS));
				SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

				String since = dateformatyyyyMMdd.format(date);
				String user = "from:" + userID;
				Query query = new Query(user);
				query.setSince(since); // YYYY-MM-DD
				QueryResult result = twitter.search(query);
				Foursquare foursquare = new Foursquare();

				request.setAttribute("userVisits", foursquare.checkins(result));
			} else {
				//use streaming api to get results
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
						// ex.printStackTrace();

					}

					@Override
					public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
						// System.out.println("Got a status deletion notice id:" +
						// statusDeletionNotice.getStatusId());

					}

					@Override
					public void onScrubGeo(long userId, long upToStatusId) {
						// System.out.println("Got scrub_geo event userId:" + userId +
						// " upToStatusId:" + upToStatusId);

					}

					@Override
					public void onStallWarning(StallWarning warning) {
						// System.out.println("Got stall warning:" + warning);
					}

					@Override
					public void onStatus(Status status) {
						// System.out.println("@" + status.getUser().getScreenName() + " - "
						// + status.getText());
						// System.out.println(status.getCreatedAt());

						// String user = status.getUser().getLocation();

						// gets Username
						// long username = status.getUser().getId();
						// System.out.println("location "+ user + " " + status.getText() );
						try {

							String newtext = status.getText();
						streams.add(newtext);
						System.out.println("sta= " + newtext);
						
						request.setAttribute("userVisits2", streams);
							request.getRequestDispatcher("views/ggmaps.html").forward(request,
									response);
						} catch (ServletException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
						// System.out.println("Got track limitation notice:" +
						// numberOfLimitedStatuses);
					}
				};

				twitterStream.addListener(listener);
				// twitterStream.sample();

				int count = 0;
				long[] idToFollow = new long[1];
				idToFollow[0] = useridd;
				//System.out.println(userID + " = " + useridd);
				String[] stringsToTrack = new String[0];
				// stringsToTrack[0] = "foursquare";
				double[][] locationsToTrack = new double[0][0];

				twitterStream.filter(new FilterQuery(count, idToFollow, stringsToTrack,
						locationsToTrack));
				streams.add("Tracking user");
				
				request.setAttribute("userVisits2", streams);
				
				
			}
			if(streams.size()> ccc)
			{
				System.out.println("Chang");
				request.setAttribute("userVisits2", streams);
				
			}
			request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
					response);
		} catch (Exception err) {
			//err.printStackTrace();
			System.out.println("Error while tweeting " + err.getMessage());
			request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
					response);
		}
	}

}
