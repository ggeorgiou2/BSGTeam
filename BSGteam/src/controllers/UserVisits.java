package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.*;
import models.*;

public class UserVisits extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("views/queryInterface.jsp").forward(
				request, response);		//doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			TwitterBean tt = new TwitterBean();
			Twitter twitter = tt.init();
			String userID = request.getParameter("userID");
			int days = Integer.parseInt(request.getParameter("days"));
			
			long DAY_IN_MS = 1000 * 60 * 60 * 24;
			Date date = new Date(System.currentTimeMillis() - (days * DAY_IN_MS));
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
			
			String since = dateformatyyyyMMdd.format(date);
			//System.out.println("date into yyyyMMdd format: " + since);
			String user = "from:"+userID;
			//PrintWriter out = response.getWriter();
			Query query = new Query(user);
			query.setSince(since); //YYYY-MM-DD
			QueryResult result = twitter.search(query);
			Foursquare foursquare = new Foursquare();
			
			request.setAttribute("userVisits", foursquare.checkins(result));
			request.getRequestDispatcher("views/queryInterface.jsp").forward(
					request, response);
		} catch (Exception err) {
			System.out.println("Error while tweeting" + err.getMessage());
		}
	}

}
