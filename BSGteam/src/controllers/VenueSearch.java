package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * VenueSearch.java
 * 
 * This class is used to connect to the team database and is used to retrieve records from the
 * database and pass them to the views for display
 * 
 * @author BSG Team
 * 
 */
public class VenueSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("views/databaseSearch.jsp").forward(
				request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// defines the connection variables
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
			String venue = request.getParameter("venue");
			ArrayList resultList = null;
			ArrayList venueList = new ArrayList();
			String query;

			if (venue.isEmpty() || (venue.equals("*"))) {
				query = "select * from userVisits";
			} else {
				query = "select * from userVisits where venue LIKE '%" + venue +"%' ";
			}
			statement = conn.createStatement();
			ResultSet result = statement.executeQuery(query);

			while (result.next()) {
				resultList = new ArrayList();
				resultList.add(result.getString(1));
				resultList.add(result.getString(2));
				resultList.add(result.getString(3));
				venueList.add(resultList);
			}

			request.setAttribute("venueList", venueList);
			RequestDispatcher view = request
					.getRequestDispatcher("views/databaseSearch.jsp");
			view.forward(request, response);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}