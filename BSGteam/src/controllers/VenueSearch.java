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
 * This class connect to the team database and some mysql commands are perform
 * on the data, That is, the venue list from the foursquare is search from the
 * database and display them as result
 * 
 * @author BSG Team
 * 
 */
public class VenueSearch extends HttpServlet {

	// Get the result from the server
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("views/databaseSearch.jsp").forward(
				request, response);
	}

	// This method post the query to the server for process
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// sets the header
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// defines the connection variable
		Connection conn = null;

		String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/";
		String dbName = "team003";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "team003";
		String password = "20ec79a9";

		Statement st;
		try {
			// loads the drivers
			Class.forName(driver).newInstance();

			// connects to the database
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			System.out.println("Connected!");
			String pid = request.getParameter("pid");

			ArrayList al = null;
			ArrayList pid_list = new ArrayList();
			String query;

			// checks for validate input
			if (pid.isEmpty() || (pid.equals("*"))) {
				query = "select * from venues";

			} else {
				query = "select * from venues where VenueName LIKE '%" + pid
						+ "%' ";
			}

			//System.out.println("query " + query);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				al = new ArrayList();

				al.add(rs.getString(1));
				al.add(rs.getString(2));
				al.add(rs.getString(3));
				al.add(rs.getString(4));
				al.add(rs.getString(5));

				//System.out.println("al :: " + al);
				pid_list.add(al);
			}

			request.setAttribute("piList", pid_list);
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