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
import models.TwitterBean;
import twitter4j.*;
//import twitter4j.conf.ConfigurationBuilder;

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
			// Instantiate the twitterBean
			TwitterBean tt = new TwitterBean();
			Twitter twitter = tt.init();

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
			
			List<Map.Entry<String, Integer>> wordlist = null;
			if (text != null) {
				wordlist = w.countWord(text, commonWords);
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
			
//			System.out.printf("\t");
//			for(Map.Entry<String, Integer> s : frequentWordList.get(0))
//			{
//				System.out.printf("\t %s", s.getKey());
//			}
//			System.out.printf("\n");
//			for(List<Entry<String, Integer>> list : frequentWordList)
//			{
//				System.out.printf("name");
//				for(Map.Entry<String, Integer> s : list)
//				{
//					System.out.printf("\t %d", s.getValue());
//				}
//				System.out.printf("\n");
//			}

			request.getRequestDispatcher("views/queryInterface.jsp").forward(
					request, response);
		} catch (Exception err) {
			System.out.println(err.getStackTrace());
		}

	}
}
