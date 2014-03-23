package controllers;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.FrequentWord;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Discussions.java This servlet class handles the tracking of what multiple
 * users (up to 10) are discussing about
 * 
 * @author BSGTeam
 * 
 */
public class Discussions extends HttpServlet {
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
		// System.out.println("Am caling the doGet method from  login twitter");

		try {
			String token_access = "263885132-oDic38nO96k91obUMBypD2V7gBkd664DPCSszpHa";
			String token_secret = "7XPXklqAiP18xdw0kfQImShEWYf06fmVVveIfboAghRvT";
			String customer_key = "MXH39rOd9mOxRWh9Exma7g";
			String customer_sercet = "789P2oTcZL9liV2DhGjiDX8J7ZGXPwZRCCoWrSeVkoo";

			ConfigurationBuilder cb = new ConfigurationBuilder();

			cb.setDebugEnabled(true).setOAuthConsumerKey(customer_key)
					.setOAuthConsumerSecret(customer_sercet)
					.setOAuthAccessToken(token_access)
					.setOAuthAccessTokenSecret(token_secret);

			TwitterFactory tf = new TwitterFactory(cb.build());
			twitter4j.Twitter twitter = tf.getInstance();

			// retrieve number of days data from form and convert to date format
			// for twitter query
			int days = Integer.parseInt(request.getParameter("days"));
			long DAY_IN_MS = 1000 * 60 * 60 * 24;
			Date date = new Date(System.currentTimeMillis()
					- (days * DAY_IN_MS));
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
					"yyyy-MM-dd");
			String since = dateformatyyyyMMdd.format(date);

			// retrieve the user IDs and separate them by comma
			String user = request.getParameter("userIDs");
			String[] splitUser = user.split(",");

			// string to store the entire set of tweets from all users
			String text = "";
			QueryResult result = null;
			Query query = null;
			// store each user's set of tweets
			ArrayList<String> userWordTweets = new ArrayList<String>();
			FrequentWord w = new FrequentWord();
			for (int i = 0; i < splitUser.length; i++) {
				// retrieve each user's tweets
				String queryText = "from:" + splitUser[i];
				query = new Query(queryText);
				query.setSince(since);
				query.count(100);
				result = twitter.search(query);
				// string to keep track of each user's original set of tweets
				String temptext = "";
				for (Status status : result.getTweets()) {
					temptext += status.getText() + " ";
					text += status.getText() + " ";
				}
				userWordTweets.add(temptext);
			}

			List<Map.Entry<String, Integer>> wordlist = null;
			if (text != null) {
				wordlist = w.countWord(text);
				System.out.println(wordlist.toString());
			}
			// retrieve required number of keywords and create a sub list
			int keywords = Integer.parseInt(request.getParameter("keywords"));
			List<Map.Entry<String, Integer>> subWordList = null;
			if (wordlist.size() > keywords) {
				subWordList = wordlist.subList(0, keywords);
			} else {
				subWordList = wordlist;
			}

			List<List<Map.Entry<String, Integer>>> frequentWordList = new ArrayList<List<Entry<String, Integer>>>();
			for (String wrd : userWordTweets) {
				List<Map.Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>();
				Map<String, Integer> finalWords = new HashMap<String, Integer>();
				List<String> list = Arrays.asList(wrd.split(" "));
				// get each user's contribution to the total number of keywords
				for (Entry<String, Integer> ent : subWordList) {
					int rsult = Collections.frequency(list, ent.getKey());
					finalWords.put(ent.getKey(), rsult);
				}
				entries.addAll(finalWords.entrySet());
				frequentWordList.add(entries);
			}

			// pass the results to the views for display
			request.setAttribute("finalList", frequentWordList);
			request.setAttribute("users", splitUser);
			request.setAttribute("totalList", subWordList);

			request.getRequestDispatcher("views/queryInterface.jsp").forward(
					request, response);
		} catch (Exception err) {
			System.out.println(err.getStackTrace());
		}

	}
}
