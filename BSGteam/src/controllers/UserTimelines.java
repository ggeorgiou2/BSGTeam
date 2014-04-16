package controllers;

import models.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fi.foyt.foursquare.api.entities.CompactVenue;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// retrieve the parameter(s) passed from the form
		String screenName = request.getParameter("screenname");
		String tweetID = request.getParameter("tweetID");
		// gets timeline request
		if (screenName != null) {
			// creates a TwitterBean object for a connection to the twitter API
			try {
				TwitterBean twitterConnection = new TwitterBean();

				Twitter twitter = twitterConnection.init();
				// gets the timeline of the user with the screen name and passes
				// the
				// tweets to the view
				request.setAttribute("timelines",
						twitterConnection.getTimeline(screenName));
				
				// also passes the screen name (twitter id) of the user to the
				// view
				request.setAttribute("user", screenName);
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
				request.setAttribute("userVisits", foursquare.checkins(result));
			} catch (TwitterException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("views/timeline.jsp").forward(request,
					response);
		} else if (tweetID != null) {
			// gets retweets of a particular tweet
			TwitterBean twitterConnection = new TwitterBean();
			try {
				// gets the tweet which was retweeted and passes it to the view
				Twitter connection = twitterConnection.init();
				Status status = connection.showStatus(Long.parseLong(tweetID));
				request.setAttribute("tweet", status.getText());
				
				// gets a list of the retweet(ers) and passes it to the view
				request.setAttribute("retweeters",
						twitterConnection.getRetweeters(tweetID));
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
