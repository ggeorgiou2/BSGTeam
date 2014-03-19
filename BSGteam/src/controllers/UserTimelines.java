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
 */
public class UserTimelines extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//retrieve the parameter(s) passed from the form
		String param = request.getParameter("screenname");
		String tweetID = request.getParameter("tweetID");

		// get user timelines
		if (param != null) {
			TwitterBean tt = new TwitterBean();
			request.setAttribute("timelines", tt.getTimeline(param));
//			List<Status> tweets = tt.getTimeline(param);
//			List<User> myrtws = new ArrayList<User>();
//			// List<User> rtwme = new ArrayList<User>();
//			for (Status st : tweets) {
//				if (st.isRetweeted()) {
//					myrtws.add(st.getUser());
//				}
//			}
			request.setAttribute("user", param);
			request.getRequestDispatcher("views/timeline.jsp").forward(request,
					response);
		}

		// get retweets of a particular tweet
		if (tweetID != null) {
			TwitterBean tt = new TwitterBean();
			Twitter connection;
			try {
				connection = tt.init();

				Status status = connection.showStatus(Long.parseLong(tweetID));
				request.setAttribute("retweeters", tt.getRetweeters(tweetID));
				request.setAttribute("tweet", status.getText());

			} catch (Exception e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("views/retweeters.jsp").forward(request,
					response);
		}
	}
}
