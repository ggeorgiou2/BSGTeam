package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.*;
import models.*;

/**
 * Servlet implementation class UserTimelines
 */
public class UserTimelines extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserTimelines() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String param = request.getParameter("screenname");
		String tweetID = request.getParameter("tweetID");

		if (param != null) {

			TwitterBean tt = new TwitterBean();
			request.setAttribute("timelines", tt.getTimeline(param));
			request.setAttribute("user", param);
			request.getRequestDispatcher("views/timeline.jsp").forward(request,
					response);
		}

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
