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
 * UserSearch.java
 * 
 * This class is used to connect to the triple store and retrieve records of
 * users
 * 
 * @author BSG Team
 * 
 */
public class UserSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String visitor = request.getParameter("visitor");
		//used to display results of users from hyperlinks in venue search
		if (visitor != null) {
			String root = getServletContext().getRealPath("/");
			File path = new File(root + "/Triple_store");
			if (!path.exists()) {
				path.mkdirs();
			}
			//gets file path were the rdf triple store resides
			String filePath = path + "/";
			Jena jena = new Jena(filePath);
			ArrayList<TwitterUser> results = jena.queryUsers(visitor);
			if (results.isEmpty()) {
				request.setAttribute("error",
						"Sorry your search yielded no results");
			} else {
				request.setAttribute("user_results", results);
			}
		}
		request.getRequestDispatcher("views/tripleStore.jsp").forward(
				request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// sets the header
		response.setContentType("text/html");
		//gets the search term for the form
		String twitterId = request.getParameter("twitterID");
		String root = getServletContext().getRealPath("/");
		File path = new File(root + "/Triple_store");
		if (!path.exists()) {
			path.mkdirs();
		}

		String filePath = path + "/";
		Jena jena = new Jena(filePath);
		//retrieves details of results matching the search term
		ArrayList<TwitterUser> results = jena.queryUsers(twitterId);
		if (results.get(0).getUserName()==null) {
			request.setAttribute("error",
					"Sorry your search yielded no results");
		} else {
			request.setAttribute("user_results", results);
		}
		request.getRequestDispatcher("views/tripleStore.jsp").forward(
				request, response);
	}

}