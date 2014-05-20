package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

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
		request.getRequestDispatcher("views/queryInterface.jsp").forward(
				request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("1");

	    //List<String> list = new ArrayList<String>();

	    
		String token_access = request.getParameter("token_access");
		String token_secret = request.getParameter("token_secret");
		String customer_key = request.getParameter("customer_key");
		String customer_secret = request.getParameter("customer_secret");
		TwitterObject twitterTokens = new TwitterObject(token_access,
				token_secret, customer_key, customer_secret);

		System.out.println("2");

		HttpSession session = request.getSession();
		if (request.getParameter("twitterToken") == null) {
			System.out.println("3");
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
					System.out.println(twitter);
					response.getWriter().write("success"); 
				    //list.add("success");
					//request.setAttribute("success",	"You have been successfully login on twitter");
				} else {
					response.getWriter().write("error"); 
				    //list.add("error");
//					request.setAttribute("error","Sorry, your token is invalid");
				}
			} catch (Exception e) {
				// e.printStackTrace();
				response.getWriter().write("error"); 
			    //list.add("error123");
				//request.setAttribute("error", "Sorry, your token is invalid");
			}
		}
		//request.getRequestDispatcher("views/queryInterface.jsp").forward(request, response);
	   /* String json = new Gson().toJson(list);
		System.out.println(json);

		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);*/
	}

}
