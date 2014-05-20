package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * Database.java
 * 
 * Used to make connections to the database and insert tweets and venues
 * 
 * @author BSG Team
 * 
 */
public class Database {

	// url of the host database
	static final String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/team003";

	/**
	 * @param tweetPic
	 *            profile picture of twitter user
	 * @param screenName
	 *            twitter id of the twitter user
	 * @param Location
	 *            the address (if available) of the twitter user
	 * @param Description
	 *            the short description of the twitter user
	 * @param userTweet
	 *            the current tweet being inserted into the database
	 * @param retweets
	 *            the number of retweets of the current tweet
	 */
	public static void twitterDB(String tweetPic, String screenName,
			String Location, String Description, String userTweet, int retweets) {
		try {
			// creates the insert statement
			String insert = "INSERT INTO twitter(picture,username,location,description,tweet,retweeted) VALUES (?, ?, ?, ?, ?, ?)";

			// gets a connection to the database
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, "team003",
					"20ec79a9");

			// creates a prepared statement using the earlier created insert
			// string
			PreparedStatement ps = con.prepareStatement(insert);

			// passes the required values to the prepared statement
			ps.setString(1, tweetPic);
			ps.setString(2, screenName);
			ps.setString(3, Location);
			ps.setString(4, Description);
			ps.setString(5, userTweet);
			ps.setInt(6, retweets);
			// executes the insert statement and closes the connection
			ps.executeUpdate();
			con.close();
		} catch (Exception ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	/**
	 * @param VenueName
	 *            name of the venue
	 * @param Address
	 *            the address/ location of the venue (if provided)
	 * @param URL
	 *            the url of the venue (if provided)
	 * @param Description
	 *            a brief description of the venue (if provided)
	 */
	public static void venuesDB(String VenueName, String Address, String URL,
			String Description) {
		try {
			// creates the insert statement
			String insert = "INSERT INTO venues(VenueName,Address,URL,Description) VALUES (?, ?, ?, ?)";

			// gets a connection to the database
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, "team003",
					"20ec79a9");

			// creates a prepared statement
			PreparedStatement ps = con.prepareStatement(insert);
			// passes the required values to the prepared statement
			ps.setString(1, VenueName);
			ps.setString(2, Address);
			ps.setString(3, URL);
			ps.setString(4, Description);
			// executes the sql query and closes the connection
			ps.executeUpdate();
			con.close();
		} catch (Exception ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	/**
	 * @param VenueName
	 *            name of the venue
	 * @param UserName
	 *            the twitter id of the user
	 */
	public static void userVisitsDB(String UserName, String VenueName) {
		try {
			// creates the insert statement
			String insert = "INSERT INTO userVisits(username, venue) VALUES (?, ?)";

			// gets a connection to the database
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, "team003",
					"20ec79a9");

			// creates a prepared statement
			PreparedStatement ps = con.prepareStatement(insert);
			// passes the required values to the prepared statement
			ps.setString(1, UserName);
			ps.setString(2, VenueName);
			// executes the sql query and closes the connection
			ps.executeUpdate();
			con.close();
		} catch (Exception ex) {

		}
	}

	public static void userTweetsDB(String user, String tweet, String date) {
		try {
			// creates the insert statement
			String insert = "INSERT INTO userVisits(username, venue, date) VALUES (?, ?, ?)";

			// gets a connection to the database
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, "team003",
					"20ec79a9");

			// creates a prepared statement
			PreparedStatement ps = con.prepareStatement(insert);
			// passes the required values to the prepared statement
			ps.setString(1, user);
			ps.setString(2, tweet);
			ps.setString(3, date);
			// executes the sql query and closes the connection
			ps.executeUpdate();
			con.close();
		} catch (Exception ex) {

		}
	}

	public static ArrayList<Stream> readStream(String user, int previous_id) {
		ArrayList<Stream> streams = new ArrayList<Stream>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager
					.getConnection(url, "team003", "20ec79a9");
			statement = connection
					.prepareStatement("SELECT * FROM userVisits where username=? and id >?");
			statement.setString(1, user);
			statement.setInt(2, previous_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String venue = resultSet.getString("venue");
				String date = resultSet.getString("date");
				Stream stream = new Stream(id, user, venue, date);
				streams.add(stream);
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return streams;
	}
	
	public static ArrayList<Stream> readVenueStream(String venue, int previous_id) {
		ArrayList<Stream> streams = new ArrayList<Stream>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager
					.getConnection(url, "team003", "20ec79a9");
			statement = connection
					.prepareStatement("SELECT * FROM userVisits where venue=? and id >?");
			statement.setString(1, venue);
			statement.setInt(2, previous_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String username = resultSet.getString("username");
				String date = resultSet.getString("date");
				Stream stream = new Stream(id, username, venue, date);
				streams.add(stream);
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return streams;
	}

	public static boolean getStreamStop() {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		boolean stop = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager
					.getConnection(url, "team003", "20ec79a9");
			statement = connection.prepareStatement("SELECT * FROM stopStream");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				stop = resultSet.getBoolean("stop");
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Database.deleteFromStop();
		return stop;

	}

	public static boolean deleteFromStop() {
		Connection connection = null;

		Statement statement = null;
		int resultSet = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager
					.getConnection(url, "team003", "20ec79a9");
			statement = connection.createStatement();
			resultSet = statement.executeUpdate("DELETE FROM stopStream");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		if (resultSet != 1)
			return false;
		else
			return true;

	}

	public static void setStreamStop() {
		try {
			// creates the insert statement
			String insert = "INSERT INTO stopStream(stop) VALUES (1)";
			// gets a connection to the database
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, "team003",
					"20ec79a9");
			Statement ps = con.createStatement();
			ps.execute(insert);
			con.close();
		} catch (Exception ex) {

		}
	}
}