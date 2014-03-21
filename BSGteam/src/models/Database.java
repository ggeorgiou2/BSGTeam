package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database.java Used to make connections to the database and insert tweets and
 * venues
 * 
 * @author BSG Team
 * 
 */
public class Database {

	static final String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/team003"; //url to the sql database server
	
	
	
	/**
	 * @param tweetPic profile picture of twitter user
	 * @param screenName twitter id of the twitter user
	 * @param Location the address (if available) of the twitter user
	 * @param Description the short description of the twitter user
	 * @param userTweet the current tweet being inserted into the database
	 * @param retweets the number of retweets of the current tweet
	 * @return 
	 */
	public void userQuery(String userName) {
				try {
			//creates the insert statement
			String query = "select * from users where user = '" + userName + "'";
			System.out.println(query);

			//gets a connection to the database
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, "team003", "20ec79a9");

			ArrayList twitterList = null;
			ArrayList tid_list = new ArrayList();
			
			//creates a prepared statement using the earlier created insert string
			Statement statement;
			statement = con.createStatement();
			ResultSet result = statement.executeQuery(query);

			while (result.next()) {

				twitterList = new ArrayList();

				twitterList.add(result.getString(1));
				twitterList.add(result.getString(2));
				twitterList.add(result.getString(3));

				System.out.println("tl :: " + twitterList);
				tid_list.add(twitterList);
			}
			
			if (twitterList.isEmpty()) {
				System.out.println("pame kala user");
				Database.newUser(userName);

			} else {
				System.out.println("en pame");
			}

			con.close();

			
					} catch (Exception ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
			Database.newUser(userName);

System.out.println("insert user " + userName);
		}
	}
	
	/**
	 * @param VenueName name of the venue
	 * @param Address the address/ location of the venue (if provided) 
	 * @param URL the url of the venue (if provided)
	 * @param Description a brief description of the venue (if provided)
	 */
	public static void newUser(String userName) {
		try {
			//creates the insert statement
			String insert = "INSERT INTO users(userID,user) VALUES (?, ?)";
			
			System.out.println(insert + "    "  + userName);

			
			//gets a connection to the database
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, "team003", "20ec79a9");

			
			//creates a prepared statement 
			PreparedStatement ps = con.prepareStatement(insert);
			//passes the required values to the prepared statement
			ps.setString(1, "ddas");
			ps.setString(2, userName);

			System.out.println(" user inserted");

			
			//executes the sql query and closes the connection
			ps.executeUpdate();
			con.close();
		} catch (Exception ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("error");

		}
	}
	
	//================
	
	/**
	 * @param tweetPic profile picture of twitter user
	 * @param screenName twitter id of the twitter user
	 * @param Location the address (if available) of the twitter user
	 * @param Description the short description of the twitter user
	 * @param userTweet the current tweet being inserted into the database
	 * @param retweets the number of retweets of the current tweet
	 */
	public static void twitterDB(String tweetPic, String screenName,
			String Location, String Description, String userTweet, int retweets) {
		try {
			//creates the insert statement
			String insert = "INSERT INTO twitter(picture,username,location,description,tweet,retweeted) VALUES (?, ?, ?, ?, ?, ?)";

			//gets a connection to the database
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, "team003", "20ec79a9");

			//creates a prepared statement using the earlier created insert string
			PreparedStatement ps = con.prepareStatement(insert);
			
			//passes the required values to the prepared statement
			ps.setString(1, tweetPic);
			ps.setString(2, screenName);
			ps.setString(3, Location);
			ps.setString(4, Description);
			ps.setString(5, userTweet);
			ps.setInt(6, retweets);
			//executes the insert statement and closes the connection
			ps.executeUpdate();
			con.close();
		} catch (Exception ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * @param VenueName name of the venue
	 * @param Address the address/ location of the venue (if provided) 
	 * @param URL the url of the venue (if provided)
	 * @param Description a brief description of the venue (if provided)
	 */
	public static void venuesDB(String VenueName, String Address, String URL,
			String Description) {
		try {
			//creates the insert statement
			String insert = "INSERT INTO venues(VenueName,Address,URL,Description) VALUES (?, ?, ?, ?)";
			
			//gets a connection to the database
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, "team003", "20ec79a9");

			//creates a prepared statement 
			PreparedStatement ps = con.prepareStatement(insert);
			//passes the required values to the prepared statement
			ps.setString(1, VenueName);
			ps.setString(2, Address);
			ps.setString(3, URL);
			ps.setString(4, Description);
			//executes the sql query and closes the connection
			ps.executeUpdate();
			con.close();
		} catch (Exception ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}