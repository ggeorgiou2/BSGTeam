package controllers;

import models.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.*;

public class UserDiscussion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// request.getRequestDispatcher("UserDisussion4a.html").forward(request,response);
		request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
				response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			TwitterBean tt = new TwitterBean();
			Twitter twitter = tt.init();

			int days = Integer.parseInt(request.getParameter("days"));
			int keywords = Integer.parseInt(request.getParameter("keywords"));

			double radius = Double.parseDouble(request.getParameter("radius"));
			double lat = Double.parseDouble(request.getParameter("lat"));
			double log = Double.parseDouble(request.getParameter("long"));

			long DAY_IN_MS = 1000 * 60 * 60 * 24;
			Date date = new Date(System.currentTimeMillis() - (days * DAY_IN_MS));
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

			String since = dateformatyyyyMMdd.format(date);
			//PrintWriter out = response.getWriter();
			Query query = new Query(" ");
			query.setSince(since);
			query.setCount(100);
			query.setGeoCode(new GeoLocation(lat, log), radius, Query.KILOMETERS);
			QueryResult result = twitter.search(query);
			FrquentWord w = new FrquentWord();
			String text = null;
			for (Status status : result.getTweets()) {
				//out.println("@" + status.getUser().getScreenName() + ":"
					//	+ status.getText() + "<br />");
				text += status.getText();
			}

			List<Map.Entry<String, Integer>> wordlist = w.countWord(text);
			System.out.println(wordlist);
			
			request.setAttribute("words", wordlist.subList(0, keywords));
			request.getRequestDispatcher("views/queryInterface.jsp").forward(
					request, response);

		} catch (Exception err) {
			err.printStackTrace();
			System.out.println("Error while tweeting" + err.getMessage());
		}

	}

}
