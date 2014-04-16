package controllers;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TwitterSearch.java
 * 
 * This class is used to connect to the team database and retrieve records of
 * tweets
 * 
 * @author BSG Team
 * 
 */
public class AdditionalFeatures extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("views/additional_features.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// sets the header

		
		// define the variable connection
		Connection conn = null;
		String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/";
		String dbName = "team003";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "team003";
		String password = "20ec79a9";
		Statement statement;

		try {
			// loads the drivers
			Class.forName(driver).newInstance();
			// connects to the database
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			String pid = request.getParameter("twitterID");
			ArrayList twitterList = null;
			ArrayList tweetList = new ArrayList();
			String query = "";

			// validates before select from the table
			if (pid.isEmpty() || (pid.equals("*"))) {
				query = "select * from twitter";
			} else {
				query = "select * from twitter where username ='" + pid + "' ";
			}

			statement = conn.createStatement();
			ResultSet result = statement.executeQuery(query);

			while (result.next()) {
				
				response.getWriter().println("<tr><td><img src='" + result.getString(2) + "' height='100' width='100'>" + 
						"<td>" + result.getString(3) +
						"<td>" + result.getString(4) +
						"<td>" + result.getString(5) +
						"<td>" + result.getString(6) +
						"<td>" + result.getString(7) + "</td></tr>");
				

			}
			//request.setAttribute("tweetList", tweetList);
			//request.getRequestDispatcher("views/databaseSearch.jsp").forward(request, response);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}