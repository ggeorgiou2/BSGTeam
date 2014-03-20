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

public class twitterSearch extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("views/databaseSearch.jsp").forward(
				request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Connection conn = null;
		String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/";
		String dbName = "team003";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "team003";
		String password = "20ec79a9";

		Statement st;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			System.out.println("Connected!");
			String pid = request.getParameter("pid");

			ArrayList tl = null;
			ArrayList tid_list = new ArrayList();
			String query;

			if (pid.isEmpty() || (pid.equals("*"))) {
				query = "select * from twitter";

			} else {
				query = "select * from twitter where id ='" + pid + "' ";
			}

			System.out.println("query " + query);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				tl = new ArrayList();

				tl.add(rs.getString(1));
				tl.add(rs.getString(2));
				tl.add(rs.getString(3));
				tl.add(rs.getString(4));
				tl.add(rs.getString(5));
				tl.add(rs.getString(6));
				tl.add(rs.getString(7));
				System.out.println("tl :: " + tl);
				tid_list.add(tl);
			}

			request.setAttribute("twList", tid_list);
			RequestDispatcher view = request
					.getRequestDispatcher("views/databaseSearch.jsp");
			view.forward(request, response);
			conn.close();
			System.out.println("Disconnected!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}