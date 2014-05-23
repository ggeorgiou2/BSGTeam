package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.StreamingData;
import models.Stream;

/**
 * This class is used to manage the streaming of checkins of particular users or
 * within a location
 * 
 * @author BSGTeam
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
		//refreshes the page to check for new data
		response.addHeader("Refresh", "3");
		String user = request.getParameter("user");
		String venue = request.getParameter("venue");
		PrintWriter out = response.getWriter();
		streams = new ArrayList<Stream>();
		//user checkin is being streamed
		if (user != null) {
			streams.addAll(StreamingData.readStream(user));
			request.setAttribute("user", user);
			request.setAttribute("streams", streams);
			request.getRequestDispatcher("views/streams.jsp").forward(request,
					response);
		} 
		// location checkins are being streamed
		else if (venue != null) {
			streams.addAll(StreamingData.readVenueStream(venue));
			out.println("<h1>Tracking locations: </h1>");
			request.setAttribute("venue", venue);
			request.setAttribute("streams", streams);
			request.getRequestDispatcher("views/venueStreams.jsp").forward(
					request, response);
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		StreamingData.setStreamStop();
		String venue = request.getParameter("venue");
		response.setContentType("text/html");
		if (venue != null) {
			response.sendRedirect("twitter#VenueVisits");
		} else {
			response.sendRedirect("twitter#UserVisits");
		}
	}
}
