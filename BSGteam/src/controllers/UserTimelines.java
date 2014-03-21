package controllers;

import models.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// retrieve the parameter(s) passed from the form
		String screenName = request.getParameter("screenname");
		String tweetID = request.getParameter("tweetID");

		// gets timeline request
		if (screenName != null) {
			// creates a TwitterBean object for a connection to the twitter API
			TwitterBean twitterConnection = new TwitterBean();
			// gets the timeline of the user with the screen name and passes the
			// tweets to the view
			request.setAttribute("timelines",
					twitterConnection.getTimeline(screenName));
			// also passes the screen name (twitter id) of the user to the view
			request.setAttribute("user", screenName);
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
			request.getRequestDispatcher("views/retweeters.jsp").forward(request,
					response);
		} else {
			// goes to homepage
			request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
					response);
		}
	}
}
