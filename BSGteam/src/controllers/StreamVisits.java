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
	private static int id;
	private static ArrayList<Stream> streams = new ArrayList<Stream>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		id = 0;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Refresh", "5");
		String user = request.getParameter("user");
		String venue = request.getParameter("venue");
		PrintWriter out = response.getWriter();

		if (user != null) {
			streams.addAll(Database.readStream(user, id));
			//out.println("<h1>Tracking user: " + user + " </h1>");
		    request.setAttribute("user", user);
			//for (Stream stream : streams) {
			    request.setAttribute("streams", streams);
				//out.println("<br>" + stream.getVenue() + " " + stream.getDate()	+ "<br/>");
				//if (id < stream.getId())
					//id = stream.getId(); ??????
			//}
		} else if (venue != null) {
			streams.addAll(Database.readVenueStream(venue, id));
			out.println("<h1>Tracking locations: </h1>");
			for (Stream stream : streams) {
				//out.println("<br>" + stream.getUserId() + " " + stream.getDate() + "<br/>");
			    request.setAttribute("userID_Date", stream.getUserId() + " " + stream.getDate());
				if (id < stream.getId())
					id = stream.getId();
			}
		}
				request.getRequestDispatcher("views/streams.jsp").forward(request, response);

		/*out.println("<form action='streams' method='post'>");
		out.println("<button type='submit'>Stop Streaming</button>");
		out.println("</form>");*/
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Database.setStreamStop();
		response.setContentType("text/html");
		response.sendRedirect("twitter#UserVisits");
	}
}
