package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.TwitterBean;
import models.TwitterObject;
import twitter4j.Twitter;

/**
 * Servlet implementation class TwitterToken
 */
public class TwitterToken extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("twitter");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String token_access = request.getParameter("token_access");
		String token_secret = request.getParameter("token_secret");
		String customer_key = request.getParameter("customer_key");
		String customer_secret = request.getParameter("customer_secret");
		TwitterObject twitterTokens = new TwitterObject(token_access,
				token_secret, customer_key, customer_secret);

		HttpSession session = request.getSession();
		if (request.getParameter("twitterToken") == null) {
			Twitter twitter;
			TwitterBean twitterConnection = new TwitterBean();
			try {
				twitter = twitterConnection.init(customer_key, customer_secret,
						token_access, token_secret);
				if (twitter.getScreenName() != null) {
					session.setAttribute("twitterToken", "twitterToken");
					session.setAttribute("token_access",
							twitterTokens.getToken_access());
					session.setAttribute("token_secret",
							twitterTokens.getToken_secret());
					session.setAttribute("customer_key",
							twitterTokens.getCustomer_key());
					session.setAttribute("customer_secret",
							twitterTokens.getCustomer_secret());
					// System.out.println(twitter);
					response.getWriter().write("success");
				} else {
					response.getWriter().write("error");
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().write("error");

			}
		}

	}

}
