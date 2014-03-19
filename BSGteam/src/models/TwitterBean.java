package models;

import java.util.ArrayList;
import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 * TwitterBean.java
 * 
 * This is a model class to handle twitter connection and major requests
 *
 */
public class TwitterBean {
	/**
	 * @param twitter
	 * @return
	 */
	public String getSimpleTimeLine(Twitter twitter) {
		String resultString = "";
		try {
			// it creates a query and sets the geocode //requirement
			Query query = new Query("#sheffield");
			query.setGeoCode(new GeoLocation(53.383, -1.483), 2,
					Query.KILOMETERS);
			QueryResult result = twitter.search(query);
			List<Status> tweets = result.getTweets();
			for (Status tweet : tweets) { // /gets the user
				User user = tweet.getUser();
				Status status = (user.isGeoEnabled()) ? user.getStatus() : null;
				if (status == null)
					resultString += "@" + tweet.getText() + " ("
							+ user.getLocation() + ") - " + tweet.getText()
							+ "\n";
				else
					resultString += "@"
							+ tweet.getText()
							+ " ("
							+ ((status != null && status.getGeoLocation() != null) ? status
									.getGeoLocation().getLatitude()
									+ ","
									+ status.getGeoLocation().getLongitude()
									: user.getLocation()) + ") - "
							+ tweet.getText() + "\n";
			}
		} catch (Exception te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets:" + te.getMessage());
			System.exit(-1);
		}
		return resultString;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Twitter init() throws Exception {
		String token_access = "2365765327-36scbtLWy1hLTnyBOZeF3nDOaW5yNpVs6cIH0iw";
		String token_secret = "2ZXFfrspEpf6MUEIuXKGMaWsMO5v5LziLqliGrcOKB7Wh";
		String customer_key = "Ne9MoF5eq2KyO5KEvWog";
		String customer_secret = "3S9GGpIZpkMDfJxW5fhHi4u3w45VDuIQIaEEpYNqM";

		Twitter twitterConnection = initTwitter(customer_key, customer_secret,
				token_access, token_secret);

		return twitterConnection;
	}

	/**
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessToken
	 * @param accessTokenSecret
	 * @return
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
	 * @param name
	 * @return
	 */
	public List<Status> getTimeline(String name) {
		Twitter twitterConnection = null;
		//Paging page = new Paging(1, 100);// page number, number per page
		List<Status> timeline = null;

		try {
			twitterConnection = init();
			String user = "from:" + name;
			Query query = new Query(user);
			query.setCount(100);
			QueryResult result = twitterConnection.search(query);
			timeline = result.getTweets();
			// timeline = twitterConnection.getUserTimeline(name, page);
		} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();
		}
		return timeline;
	}

	/**
	 * @param tweetID
	 * @return
	 */
	public List<Status> getRetweeters(String tweetID) {
		Twitter twitterConnection = null;
		// ArrayList<String> users = new ArrayList<String>();
		List<Status> subItems = new ArrayList<Status>();

		try {
			twitterConnection = init();

			List<Status> retweets = null;
			long id = Long.parseLong(tweetID);
			retweets = twitterConnection.getRetweets(id);
			// get first 10
			if (retweets.size() > 10) {
				subItems = new ArrayList<Status>(retweets.subList(0, 10));
			} else {
				subItems = new ArrayList<Status>(retweets.subList(0,
						retweets.size()));
			}
			// for (Status rtw : subItems) {
			// System.out.println("Getting retweets: "
			// + rtw.getText() + " "
			// + rtw.getUser().getScreenName());
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subItems;
	}
}
