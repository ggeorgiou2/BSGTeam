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

	public static ArrayList<Stream> readStream(String user) {
		ArrayList<Stream> streams = new ArrayList<Stream>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager
					.getConnection(url, "team003", "20ec79a9");
			statement = connection
					.prepareStatement("SELECT * FROM userVisits where username=? order by id desc");
			statement.setString(1, user);
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

	public static ArrayList<Stream> readVenueStream(String venue) {
		ArrayList<Stream> streams = new ArrayList<Stream>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager
					.getConnection(url, "team003", "20ec79a9");
			statement = connection
					.prepareStatement("SELECT * FROM userVisits where venue=?");
			statement.setString(1, venue);

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