package models;

import java.util.ArrayList;
import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 * TwitterBean.java
 * 
 * This is a model class to handle the twitter connection and other major
 * twitter queries including getting a user's timeline and the retweeters of a
 * particular tweet
 * 
 * @author BSG Team
 * 
 */

public class TwitterBean {
	/**
	 * @return a connection to the twitter api
	 * @throws Exception
	 */
	public Twitter init(String customer_key, String customer_secret,
			String token_access, String token_secret) throws Exception {
		// creates a twitter connection using the above keys
		Twitter twitterConnection = initTwitter(customer_key, customer_secret,
				token_access, token_secret);
		return twitterConnection;
	}

	/**
	 * @param consumerKey
	 *            consumerKey value gotten from Twitter for OAuth
	 * @param consumerSecret
	 *            consumerSecret value gotten from Twitter for OAuth
	 * @param accessToken
	 *            accessToken value gotten from Twitter for OAuth
	 * @param accessTokenSecret
	 *            accessTokenSecret value gotten from Twitter for OAuth
	 * @return TwitterFactory configuration to initiate the connection to the
	 *         twitter API
	 * @throws Exception
	 */
	private Twitter initTwitter(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) throws Exception {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSecret)
				.setJSONStoreEnabled(false);
		return (new TwitterFactory(cb.build()).getInstance());
	}

	/**
	 * Gets the twitter timeline of a particular user
	 * 
	 * @param name
	 *            screen name/twitter id of the user
	 * @return an array list containing up to the 100 most recent tweets of the
	 *         user
	 */
	public List<Status> getTimeline(String name, String customer_key,
			String customer_secret, String token_access, String token_secret) {
		Twitter twitterConnection = null;
		List<Status> timeline = null;
		try {
			twitterConnection = init(customer_key, customer_secret,
					token_access, token_secret);
			String user = "from:" + name;
			Query query = new Query(user);
			query.setCount(100);
			QueryResult result = twitterConnection.search(query);
			timeline = result.getTweets();
		} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();
		}
		return timeline;
	}

	/**
	 * Gets the retweets of a particular tweet
	 * 
	 * @param tweetID
	 *            the id of the tweet
	 * @return an array list of the retweets (up to 10) of the tweet passed to
	 *         it
	 */
	public List<Status> getRetweeters(long id, String customer_key,
			String customer_secret, String token_access, String token_secret) {
		Twitter twitterConnection = null;
		List<Status> subItems = new ArrayList<Status>();
		try {
			// gets a twitter connection
			twitterConnection = init(customer_key, customer_secret,
					token_access, token_secret);
			int i = twitterConnection.getRateLimitStatus()
					.get("/statuses/retweets/:id").getRemaining();
			if (i > 0) {
				List<Status> retweets = null;
				// twitter query to get the retweets
				retweets = twitterConnection.getRetweets(id);
				// gets at most 10 retweets
				if (retweets.size() > 10) {
					// selects the first 10 retweets if there are more than 10
					subItems = new ArrayList<Status>(retweets.subList(0, 10));
				} else {
					// selects the available retweets (less than 10)
					subItems = new ArrayList<Status>(retweets.subList(0,
							retweets.size()));
				}
			} else {
				System.out.println("Limit exceeded");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subItems;
	}
}
