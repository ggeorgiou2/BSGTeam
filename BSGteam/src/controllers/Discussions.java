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

public class Discussions extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// doPost(request, response);

	}

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

			int days = Integer.parseInt(request.getParameter("days"));
			String user = request.getParameter("userIDs");
			long DAY_IN_MS = 1000 * 60 * 60 * 24;
			Date date = new Date(System.currentTimeMillis()
					- (days * DAY_IN_MS));
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat(
					"yyyy-MM-dd");
			String since = dateformatyyyyMMdd.format(date);

			String[] splitUser = user.split(",");
			String text = "";
			QueryResult result = null;
			Query query = null;
			ArrayList<QueryResult> allResults = new ArrayList<QueryResult>();
			ArrayList<String> words = new ArrayList<String>();
			FrequentWord w = new FrequentWord();
			for (int i = 0; i < splitUser.length; i++) {
				String qu = "from:" + splitUser[i];
				System.out.println(qu);
				query = new Query(qu);
				query.setSince(since);
				query.count(50);
				result = twitter.search(query);
				allResults.add(result);
				String temptext = "";
				for (Status status : result.getTweets()) {
					temptext += status.getText() + " ";
					text += status.getText() + " ";
				}
				words.add(temptext);
			}
			List<Map.Entry<String, Integer>> wordlist = null;
			if (text != null) {
				wordlist = w.countWord(text);
				System.out.println(wordlist.toString());
			}
			int keywords = Integer.parseInt(request.getParameter("keywords"));
			List<Map.Entry<String, Integer>> sss = null;
			if (wordlist.size() > keywords) {
				sss = wordlist.subList(0, keywords);
			} else {
				sss = wordlist;
			}
			int i = 0;
			//List<Map<String, Integer>> finalList = new ArrayList<Map<String,Integer>>();
			List<List<Map.Entry<String,Integer>>> fin = new ArrayList<List<Entry<String, Integer>>>();
			for (String wrd : words) {
				List<Map.Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>();
				Map<String, Integer> finalWords = new HashMap<String, Integer>();
				//System.out.println("1- " + wrd);
				List<String> list = Arrays.asList(wrd.split(" "));
				for (Entry<String, Integer> ent : sss) {
					int rsult = Collections.frequency(list, ent.getKey());
					finalWords.put(ent.getKey(), rsult);
				}
				i++;
				entries.addAll(finalWords.entrySet());
				fin.add(entries);
			}	

			System.out.println(fin);
			request.setAttribute("final", fin);	
			request.setAttribute("users", splitUser);
			request.setAttribute("total", sss);
			
			request.getRequestDispatcher("views/queryInterface.jsp").forward(
					request, response);
		} catch (Exception err) {
			System.out.println(err.getStackTrace());
		}

	}
}
