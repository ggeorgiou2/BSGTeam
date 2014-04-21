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
 * This class finds what users are discussing in a specific location and within
 * a range of days
 * 
 * @author BSG Team
 * 
 */
public class UserDiscussion extends HttpServlet {
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
			// Instantiate the twitterBean
			TwitterBean tt = new TwitterBean();
			Twitter twitter = tt.init();

			// get the user input variables of the form
			int days = Integer.parseInt(request.getParameter("days"));
			int keywords = Integer.parseInt(request.getParameter("keywords"));
			double radius = Double.parseDouble(request.getParameter("radius"));
			double lat = Double.parseDouble(request.getParameter("lat"));
			double log = Double.parseDouble(request.getParameter("long"));

			// calculate the number of days
			long DAY_IN_MS = 1000 * 60 * 60 * 24;

			// convert the number of days to date in form yyyy-mm-dd
			Date date = new Date(System.currentTimeMillis() - (days * DAY_IN_MS));
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

			String since = dateformatyyyyMMdd.format(date);
			// Instantiate the Query object
			Query query = new Query(" ");
			// sets the date
			query.setSince(since);
			// the number of result to display
			query.setCount(100);
			// set the location and the radius
			query.setGeoCode(new GeoLocation(lat, log), radius, Query.KILOMETERS);
			// Searches for the query
			QueryResult result = twitter.search(query);

			// Initialize the frequentWord object
			FrequentWord w = new FrequentWord();
			String text = null;
			for (Status status : result.getTweets()) {
				text += status.getText();
			}
			String root = getServletContext().getRealPath("/");
			File words = new File(root + "common_words.txt");
			List<String> list2 = new ArrayList<String>();			
			try {
				Scanner in = new Scanner(words);
				in.useDelimiter(","); // <br /> marks end of line
				String line; // thus skip duplicate records
				while (in.hasNextLine()) {
					line = in.nextLine();
					list2.add(line);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println(list2);
			
			HashSet<String> commonWords = new HashSet<String>(list2);
			List<Map.Entry<String, Integer>> wordlist = w.countWord(text,commonWords);
			request.setAttribute("words", wordlist.subList(0, keywords));
			request.getRequestDispatcher("views/queryInterface.jsp").forward(request,
					response);
		} catch (Exception err) {
			err.printStackTrace();
			System.out.println("Error while tweeting" + err.getMessage());
		}
	}
}
