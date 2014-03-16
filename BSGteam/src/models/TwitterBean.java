package models;

import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterBean {
		public String getSimpleTimeLine(Twitter twitter){
		String resultString= "";
		try {
		// it creates a query and sets the geocode 		//requirement
		Query query= new Query("#sheffield");
		query.setGeoCode(new GeoLocation(53.383, -1.483), 2,
		Query.KILOMETERS);
		//it fires the query
		QueryResult result = twitter.search(query);

		//it cycles on the tweets
		List<Status> tweets = result.getTweets();
		for (Status tweet : tweets) { ///gets the user
		User user = tweet.getUser();
		Status status= (user.isGeoEnabled())?user.getStatus():null;
		if (status==null)
			resultString+="@" + tweet.getText() + " ("
			+ user.getLocation()
			+ ") - " + tweet.getText() + "\n";
			else resultString+="@" + tweet.getText()
			+ " (" + ((status!=null&&status.getGeoLocation()!=null)?
			status.getGeoLocation().getLatitude()
			+","+status.getGeoLocation().getLongitude()
			:user.getLocation())
			+ ") - " + tweet.getText() + "\n";
			}
			} catch (Exception te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets:" +
			te.getMessage());
			System.exit(-1);
			}
			return resultString;
			}
		
		public Twitter init() throws Exception{
			String token_access = "2365765327-36scbtLWy1hLTnyBOZeF3nDOaW5yNpVs6cIH0iw";
			String token_secret = "2ZXFfrspEpf6MUEIuXKGMaWsMO5v5LziLqliGrcOKB7Wh";
			String customer_key = "Ne9MoF5eq2KyO5KEvWog";
			String customer_secret = "3S9GGpIZpkMDfJxW5fhHi4u3w45VDuIQIaEEpYNqM";

			Twitter twitterConnection = initTwitter(customer_key,
			customer_secret, token_access, token_secret);
			
			return twitterConnection;
		}
		
			private Twitter initTwitter(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret)
			throws Exception {
				
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			.setOAuthConsumerKey(consumerKey)
			.setOAuthConsumerSecret(consumerSecret)
			.setOAuthAccessToken(accessToken)
			.setOAuthAccessTokenSecret(accessTokenSecret)
			.setJSONStoreEnabled(true);
			return (new TwitterFactory(cb.build()).getInstance());
			}
			
			public List<Status> getTimeline(String name)
			{
				Twitter twitterConnection = null;
				Paging page = new Paging (1, 100);//page number, number per page
				List<Status> timeline = null;
				
				try {
					twitterConnection= init();
					String user="from:"+name;
					Query query= new Query(user);
					query.setCount(100);
					QueryResult result = twitterConnection.search(query);
					timeline= result.getTweets();
					//timeline = twitterConnection.getUserTimeline(name, page);				
				} catch (Exception e) {
				System.out.println("Cannot initialise Twitter");
				e.printStackTrace();			
				}
				return timeline;
			}		
}
