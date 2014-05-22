package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Database;
import models.Stream;

/**
 * Servlet implementation class StreamVisits
 */
public class StreamVisits extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<Stream> streams;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		streams = new ArrayList<Stream>();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Refresh", "3");
		String user = request.getParameter("user");
		String venue = request.getParameter("venue");
		PrintWriter out = response.getWriter();
		streams = new ArrayList<Stream>();

		if (user != null) {
			streams.addAll(Database.readStream(user));
			request.setAttribute("user", user);
			request.setAttribute("streams", streams);
			request.getRequestDispatcher("views/streams.jsp").forward(request,
					response);
		} else if (venue != null) {
			streams.addAll(Database.readVenueStream(venue));
			out.println("<h1>Tracking locations: </h1>");
			request.setAttribute("venue", venue);
			request.setAttribute("streams", streams);
			request.getRequestDispatcher("views/venueStreams.jsp").forward(request,
					response);
		}
		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Database.setStreamStop();
		String venue = request.getParameter("venue");
		response.setContentType("text/html");
		if (venue != null) {
			response.sendRedirect("twitter#VenueVisits");
		} else {
			response.sendRedirect("twitter#UserVisits");
		}
	}
}
