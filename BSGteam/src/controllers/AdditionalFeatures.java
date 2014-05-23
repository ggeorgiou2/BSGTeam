package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AdditionalFeatures.java
 * 
 * This class is used for additional queries and APIs like Flickr and Instagram
 * 
 * @author BSG Team
 * 
 */
public class AdditionalFeatures extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		//checks which page is required and displays it
		if (action != null) {
			if (action.equals("more")) {
				request.getRequestDispatcher("views/more_apis.jsp").forward(
						request, response);
			} else if (action.equals("additional")) {
				request.getRequestDispatcher("views/additional_features.jsp")
						.forward(request, response);
			}
		} else {
			request.getRequestDispatcher("views/additional_features.jsp")
					.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}