package controllers;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TwitterSearch.java
 * 
 * This class connect to the team database and some mysql commands are perform
 * on the data, That is, the tweets of the user are search from the database and
 * display them as result
 * 
 * @author BSG Team
 * 
 */
public class TwitterSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Get the result from the server
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("views/databaseSearch.jsp").forward(request,
				response);
	}

	// This method post the query to the server for process
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// sets the header
		response.setContentType("text/html");

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
			conn = DriverManager.getConnection(url + dbName, userName, password);
			System.out.println("Connected!");
			String pid = request.getParameter("pid");

			ArrayList<String> twitterList = null;
			//ArrayList<String> tid_list = new ArrayList<String>();
			String query;

			// validates before select from the table
			if (pid.isEmpty() || (pid.equals("*"))) {
				query = "select * from twitter";
			} else {
				query = "select * from twitter where id ='" + pid + "' ";
			}

			System.out.println("query " + query);
			statement = conn.createStatement();
			ResultSet result = statement.executeQuery(query);

			while (result.next()) {
				twitterList = new ArrayList<String>();

				twitterList.add(result.getString(1));
				twitterList.add(result.getString(2));
				twitterList.add(result.getString(3));
				twitterList.add(result.getString(4));
				twitterList.add(result.getString(5));
				twitterList.add(result.getString(6));
				twitterList.add(result.getString(7));
				System.out.println("tl :: " + twitterList);
				//tid_list.add(twitterList);
			}

			request.setAttribute("twList", twitterList);
			RequestDispatcher view = request
					.getRequestDispatcher("views/databaseSearch.jsp");
			view.forward(request, response);
			conn.close();
			System.out.println("Disconnected!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}