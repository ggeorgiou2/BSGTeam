package controllers;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Jena;
import models.Venue;

/**
 * VenueSearch.java
 * 
 * This class is used to connect to the triple store for venues and is used to
 * retrieve records and pass them to the views for display
 * 
 * @author BSG Team
 * 
 */
public class VenueSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String imageFor = request.getParameter("imageFor");
		// gets pictures for a particular venue
		if (imageFor != null) {
			String root = getServletContext().getRealPath("/");
			File path = new File(root + "/Triple_store");
			if (!path.exists()) {
				path.mkdirs();
			}

			String filePath = path + "/";
			Jena jena = new Jena(filePath);
			ArrayList<Venue> results = jena.queryVenues(imageFor);
			String[] photos = null;
			if (!results.isEmpty()) {
				photos = results.get(0).getPhotos();
			}
			request.setAttribute("images", photos);
			request.getRequestDispatcher("views/tripleStoreImages.jsp")
					.forward(request, response);
		} else {
			request.getRequestDispatcher("views/tripleStore.jsp").forward(
					request, response);
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// gets the venue name to be searched for
		String venue = request.getParameter("venue");
		String root = getServletContext().getRealPath("/");
		File path = new File(root + "/Triple_store");
		if (!path.exists()) {
			path.mkdirs();
		}

		String filePath = path + "/";
		Jena jena = new Jena(filePath);
		ArrayList<Venue> results = jena.queryVenues(venue);

		if (results.get(0).getVenueName()==null) {
			request.setAttribute("error",
					"Sorry your search yielded no results");
		} else {
			request.setAttribute("venue_results", results);
		}
		request.getRequestDispatcher("views/tripleStore.jsp").forward(request,
				response);
	}
}