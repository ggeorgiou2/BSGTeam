package controllers;

import models.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class UserDiscussion4 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request.getRequestDispatcher("UserDisussion4a.html").forward(request,response);
		request.getRequestDispatcher("views/queryInterface.jsp").forward(
				request, response);
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
System.out.println("Am caling the doGet method from  login twitter");
		
		try{
			String token_access ="263885132-oDic38nO96k91obUMBypD2V7gBkd664DPCSszpHa";
			String token_secret="7XPXklqAiP18xdw0kfQImShEWYf06fmVVveIfboAghRvT";
			String customer_key="MXH39rOd9mOxRWh9Exma7g";
			String customer_sercet="789P2oTcZL9liV2DhGjiDX8J7ZGXPwZRCCoWrSeVkoo";
			
			ConfigurationBuilder cb= new ConfigurationBuilder();
			
			cb.setDebugEnabled(true)
			  .setOAuthConsumerKey(customer_key)
			  .setOAuthConsumerSecret(customer_sercet)
			  .setOAuthAccessToken(token_access)
			  .setOAuthAccessTokenSecret(token_secret);
			
			TwitterFactory tf=new TwitterFactory(cb.build());
			twitter4j.Twitter twitter = tf.getInstance();
			
	
			int days = Integer.parseInt(request.getParameter("days"));
			//String month = request.getParameter("month");
			//String year = request.getParameter("year");
			double radius = Double.parseDouble(request.getParameter("radius"));
			double lat = Double.parseDouble(request.getParameter("lat"));
			double log = Double.parseDouble(request.getParameter("log"));
			//double radius = Double.parseDouble(request.getParameter("radius"));
			
			//String date = year + "-"+ month +"-" + days;
			
			
			long DAY_IN_MS = 1000 * 60 * 60 * 24;
			Date date = new Date(System.currentTimeMillis() - (days * DAY_IN_MS));
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
			
			String since = dateformatyyyyMMdd.format(date);
			
			
			 PrintWriter out = response.getWriter();
			 
			
			
			Query query = new Query("");
			query.setSince(since);
			query.setGeoCode(new GeoLocation(lat, log), radius,Query.KILOMETERS);
			
			
		//	query.setGeoCode(location, radius, Query.KILOMETERS );
			
			 
			//System.out.println(query);
		
			 
			
			QueryResult result = twitter.search(query);
			 
			FrquentWord w = new FrquentWord();
			
			String text = null;
			
			for(Status status : result.getTweets()){
			//System.out.println(status.getUser().getURL());
			out.println("@"+ status.getUser().getScreenName() + ":" + status.getText() +"<br />");
			
			 text += status.getText();
			
			 Map<String, Integer> a =	w.countWord(text);
				System.out.println(a);
				
									
			}	
			
						
		}catch(Exception err){
			System.out.println("Error while tweeting" + err.getMessage());
		}
		
		
	}
	

	
	
	
	

}
