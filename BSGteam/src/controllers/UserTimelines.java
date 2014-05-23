package controllers;

import models.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import twitter4j.*;

/**
 * UserTimelines.java
 * 
 * This servlet is used to display the timeline of a user
 * 
 * @author BSG Team
 */
public class UserTimelines extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String screenName = request.getParameter("screenName");

		System.out.println("screenName " + screenName);
		
		HttpSession session = request.getSession();
		
		// gets timeline request
			// creates a TwitterBean object for a connection to the twitter API
			try {
				TwitterBean twitterConnection = new TwitterBean();

				Twitter twitter = twitterConnection.init(session.getAttribute("customer_key").toString(), session.getAttribute("customer_secret").toString(), session.getAttribute("token_access").toString(), session.getAttribute("token_secret").toString());
				// gets the timeline of the user with the screen name and passes
				// the
				// tweets to the view
				List<Status> results = twitterConnection
						.getTimeline(screenName, session.getAttribute("customer_key").toString(), session.getAttribute("customer_secret").toString(), session.getAttribute("token_access").toString(), session.getAttribute("token_secret").toString());

				request.setAttribute("timelines", results);
				request.setAttribute("user", screenName);

				// also passes the screen name (twitter id) of the user to the
				// view
				// gets the date from which previous tweets should be retrieved
				// and
				// inspected for checkins
				int days = 5;
				long DAY_IN_MS = 1000 * 60 * 60 * 24;
				Date date = new Date(System.currentTimeMillis()
						- (days * DAY_IN_MS));
				SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
						"yyyy-MM-dd");
				String since = dateformatyyyyMMdd.format(date);
				String user = "from:" + screenName;
				Query query = new Query(user);
				query.setSince(since); // YYYY-MM-DD
				QueryResult result;
				result = twitter.search(query);
				// creates a foursquare object and inspects the user's tweet for
				// foursquare checkins
				Foursquare foursquare = new Foursquare();
				request.setAttribute("userVisits", foursquare.checkins(result, session.getAttribute("clientID").toString(), session.getAttribute("clinetSec").toString(), session.getAttribute("redirectURL").toString(), session.getAttribute("accessToken").toString()));

				List<Status> contacters = new ArrayList<Status>();
				List<Status> contactees = new ArrayList<Status>();
				List<Status> subResults = new ArrayList<Status>();
				
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

				}
				else
				{
					System.out.println("Exceeded retweet limit");
				}
				//System.out.println(twitter.getRateLimitStatus());

				// Get people user retweeted
				Set<String> contacts = new TreeSet<String>();
				if (contacters.size() > 0) {
					for (Status status : contacters) {
						contacts.add(status.getUser().getScreenName());
					}
				}
				System.out.println("1=" + contacts);
				request.setAttribute("allcontacts", contacts);

				Set<String> contacts2 = new TreeSet<String>();
				if (contactees.size() > 0) {
					for (Status status : contactees) {
						contacts2.add(status.getUser().getScreenName());
					}
				}
				System.out.println("2=" + contacts2);
				request.setAttribute("allcontacts2", contacts2);
			} catch (TwitterException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("views/timeline.jsp").forward(request,
					response);
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// retrieve the parameter(s) passed from the form
		String screenName = request.getParameter("screenname");
		String tweetID = request.getParameter("tweetID");

		HttpSession session = request.getSession();
		
		// gets timeline request
		if (screenName != null) {
			// creates a TwitterBean object for a connection to the twitter API
			try {
				TwitterBean twitterConnection = new TwitterBean();

				Twitter twitter = twitterConnection.init(session.getAttribute("customer_key").toString(), session.getAttribute("customer_secret").toString(), session.getAttribute("token_access").toString(), session.getAttribute("token_secret").toString());
				// gets the timeline of the user with the screen name and passes
				// the
				// tweets to the view
				List<Status> results = twitterConnection
						.getTimeline(screenName, session.getAttribute("customer_key").toString(), session.getAttribute("customer_secret").toString(), session.getAttribute("token_access").toString(), session.getAttribute("token_secret").toString());

				request.setAttribute("timelines", results);
				request.setAttribute("user", screenName);

				// also passes the screen name (twitter id) of the user to the
				// view
				// gets the date from which previous tweets should be retrieved
				// and
				// inspected for checkins
				int days = 5;
				long DAY_IN_MS = 1000 * 60 * 60 * 24;
				Date date = new Date(System.currentTimeMillis()
						- (days * DAY_IN_MS));
				SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
						"yyyy-MM-dd");
				String since = dateformatyyyyMMdd.format(date);
				String user = "from:" + screenName;
				Query query = new Query(user);
				query.setSince(since); // YYYY-MM-DD
				QueryResult result;
				result = twitter.search(query);
				// creates a foursquare object and inspects the user's tweet for
				// foursquare checkins
				Foursquare foursquare = new Foursquare();
				request.setAttribute("userVisits", foursquare.checkins(result, session.getAttribute("clientID").toString(), session.getAttribute("clinetSec").toString(), session.getAttribute("redirectURL").toString(), session.getAttribute("accessToken").toString()));

				List<Status> contacters = new ArrayList<Status>();
				List<Status> contactees = new ArrayList<Status>();
				List<Status> subResults = new ArrayList<Status>();
				
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

				}
				else
				{
					System.out.println("Exceeded retweet limit");
				}
				//System.out.println(twitter.getRateLimitStatus());

				// Get people user retweeted
				Set<String> contacts = new TreeSet<String>();
				if (contacters.size() > 0) {
					for (Status status : contacters) {
						contacts.add(status.getUser().getScreenName());
					}
				}
				System.out.println("1=" + contacts);
				request.setAttribute("allcontacts", contacts);

				Set<String> contacts2 = new TreeSet<String>();
				if (contactees.size() > 0) {
					for (Status status : contactees) {
						contacts2.add(status.getUser().getScreenName());
					}
				}
				System.out.println("2=" + contacts2);
				request.setAttribute("allcontacts2", contacts2);
			} catch (TwitterException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("views/timeline.jsp").forward(request,
					response);
		} else if (tweetID != null) {
			long id = Long.parseLong(tweetID);

			// gets retweets of a particular tweet
			TwitterBean twitterConnection = new TwitterBean();
			try {
				// gets the tweet which was retweeted and passes it to the view
				Twitter connection = twitterConnection.init(session.getAttribute("customer_key").toString(), session.getAttribute("customer_secret").toString(), session.getAttribute("token_access").toString(), session.getAttribute("token_secret").toString());
				Status status = connection.showStatus(Long.parseLong(tweetID));
				request.setAttribute("tweet", status.getText());

				// gets a list of the retweet(ers) and passes it to the view
				request.setAttribute("retweeters",
						twitterConnection.getRetweeters(id, session.getAttribute("customer_key").toString(), session.getAttribute("customer_secret").toString(), session.getAttribute("token_access").toString(), session.getAttribute("token_secret").toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("views/retweeters.jsp").forward(
					request, response);
		} else {
			// goes to homepage
			request.getRequestDispatcher("views/queryInterface.jsp").forward(
					request, response);
		}
	}
}