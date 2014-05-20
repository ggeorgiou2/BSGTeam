package controllers;

import models.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("views/queryInterface.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			// Instantiate the twitterBean
			
			
			HttpSession session = request.getSession();
			// if (session.getAttribute("twitterToken") != null) {
			//System.out.println("3");
			String token_access = (String) session.getAttribute("token_access");
			String token_secret = (String) session.getAttribute("token_secret");
			String customer_key = (String) session.getAttribute("customer_key");
			String customer_secret = (String) session
					.getAttribute("customer_secret");
			System.out.println(token_access);

			// instantiates a new object of the <code>TwitterBean</code> class
			TwitterBean twitterConnection = new TwitterBean();
			Twitter twitter = twitterConnection.init(customer_key, customer_secret, token_access,
					token_secret);		
			
			int remaining = twitter.getRateLimitStatus().get("/search/tweets")
					.getRemaining();
			if (remaining > 1) {
				// get the user input variables of the form
				int days = Integer.parseInt(request.getParameter("days"));
				int keywords = Integer.parseInt(request
						.getParameter("keywords"));
				double radius = Double.parseDouble(request
						.getParameter("radius"));
				double lat = Double.parseDouble(request.getParameter("lat"));
				double log = Double.parseDouble(request.getParameter("long"));

				// calculate the number of days
				long DAY_IN_MS = 1000 * 60 * 60 * 24;

				// convert the number of days to date in form yyyy-mm-dd
				Date date = new Date(System.currentTimeMillis()
						- (days * DAY_IN_MS));
				SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
						"yyyy-MM-dd");

				String since = dateformatyyyyMMdd.format(date);
				// Instantiate the Query object
				Query query = new Query(" ");
				// sets the date
				query.setSince(since);
				// the number of result to display
				query.setCount(100);
				// set the location and the radius
				query.setGeoCode(new GeoLocation(lat, log), radius,
						Query.KILOMETERS);
				// Searches for the query
				QueryResult result = twitter.search(query);
				if (result.getTweets().isEmpty()) {
					request.setAttribute("error",
							"Sorry, your search returned no results");
				} else {
					// Initialize the frequentWord object
					FrequentWord w = new FrequentWord();
					String text = null;
					for (Status status : result.getTweets()) {
						text += status.getText();
					}
					String root = getServletContext().getRealPath("/");
					File words = new File(root + "common_words.txt");
					String[] data = null;
					try {
						Scanner in = new Scanner(words);
						String line; // thus skip duplicate records
						while (in.hasNextLine()) {
							line = in.nextLine();
							System.out.println(line);
							data = line.split(",");
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					HashSet<String> commonWords = new HashSet<String>(
							Arrays.asList(data));
					List<Map.Entry<String, Integer>> wordlist = w.countWord(
							text, commonWords);
					request.setAttribute("words", wordlist.subList(0, keywords));
				}
			} else {
				request.setAttribute("error", "Sorry limit exceeded");
			}
			request.setAttribute("words_result", "true");

		} catch (Exception err) {
			request.setAttribute("words_result", "false");
			request.setAttribute("error",
					"Sorry, an error occurred. Please try again later");
			System.out.println("Error while tweeting" + err.getMessage());
		}
		request.getRequestDispatcher("views/queryInterface.jsp").forward(
				request, response);
	}
}
