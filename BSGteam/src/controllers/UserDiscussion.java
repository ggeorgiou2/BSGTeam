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

/**
 * UserDiscussion.java
 *
 *This class find what a user is discussing in a specific location and within a range of days
 *	 
 * @author BSG Team
 * 
 * 
 *
 */
public class UserDiscussion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Get the result from the server
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("views/queryInterface.jsp").forward(request,response);
	}
	
	//This method post the query to the server for process
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		try {
			//Instantiate the twitterBean
			TwitterBean tt = new TwitterBean();
			Twitter twitter = tt.init();
			
			//get the user input variables of the form
			int days = Integer.parseInt(request.getParameter("days"));
			int keywords = Integer.parseInt(request.getParameter("keywords"));
			double radius = Double.parseDouble(request.getParameter("radius"));
			double lat = Double.parseDouble(request.getParameter("lat"));
			double log = Double.parseDouble(request.getParameter("long"));

			//calculate the number of days
			long DAY_IN_MS = 1000 * 60 * 60 * 24;
			
			//convert the number of days to date in form yyy-mm-dd
			Date date = new Date(System.currentTimeMillis() - (days * DAY_IN_MS));
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

			String since = dateformatyyyyMMdd.format(date);
			
			//Instantiate the Query object
			Query query = new Query(" ");
			
			//sets the date
			query.setSince(since);
			
			//the number of result to display
			query.setCount(100);
			
			//set the location and the radius
			query.setGeoCode(new GeoLocation(lat, log), radius, Query.KILOMETERS);
			
			//Searches for the query
			QueryResult result = twitter.search(query);
			
			//Initialize the frequentWord object
			FrequentWord w = new FrequentWord();
			String text = null;
			for (Status status : result.getTweets()) {
				text += status.getText();
			}

			List<Map.Entry<String, Integer>> wordlist = w.countWord(text);
			
			
			request.setAttribute("words", wordlist.subList(0, keywords));
			request.getRequestDispatcher("views/queryInterface.jsp").forward(request, response);

		} catch (Exception err) {
			err.printStackTrace();
			System.out.println("Error while tweeting" + err.getMessage());
		}

	}

}
