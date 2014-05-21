package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Jena;
import models.TwitterUser;

/**
 * TwitterSearch.java
 * 
 * This class is used to connect to the team database and retrieve records of
 * tweets
 * 
 * @author BSG Team
 * 
 */
public class TwitterSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("views/databaseSearch.jsp").forward(
				request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// sets the header
		response.setContentType("text/html");
		String twitterId = request.getParameter("twitterID");
		String root = getServletContext().getRealPath("/");
		File path = new File(root + "/Triple_store");
		if (!path.exists()) {
			path.mkdirs();
		}

		String filePath = path + "/";
		Jena jena = new Jena(filePath);
		ArrayList<TwitterUser> results = jena.queryUsers(twitterId);
		if (results.isEmpty()) {
			request.setAttribute("error", "Sorry your search yielded no results");
		} else {
			request.setAttribute("user_results", results);
		}
		request.getRequestDispatcher("views/databaseSearch.jsp").forward(
				request, response);
	}

}