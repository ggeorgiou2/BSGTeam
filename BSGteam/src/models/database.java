package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import twitter4j.Place;
import twitter4j.Status;


public class database {

  static final String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/team003";
  
  public static void twitterDB(String tweetPic, String screenName, String Location, String Description, String userTweet, int reRetweets) {
      try {
          String insert = "INSERT INTO twitter(picture,username,location,description,tweet,retweeted) VALUES (?, ?, ?, ?, ?, ?)";

          Class.forName("com.mysql.jdbc.Driver");
          Connection con = DriverManager.getConnection(url, "team003", "20ec79a9");
 
          PreparedStatement ps = con.prepareStatement(insert);
          
          ps.setString(1, tweetPic);
          ps.setString(2, screenName);
          ps.setString(3, Location);
          ps.setString(4, Description);
          ps.setString(5, userTweet);
          ps.setInt(6, reRetweets);
          ps.executeUpdate();
          con.close();

      } catch (Exception ex) {
          Logger.getLogger(database.class.getName()).log(
                           Level.SEVERE, null, ex);

      }

  }
  
  public static void venuesDB(String VenueName, String Address, String URL, String Description) {
      try {

          String insert = "INSERT INTO venues(VenueName,Address,URL,Description) VALUES (?, ?, ?, ?)";

          Class.forName("com.mysql.jdbc.Driver");
          Connection con = DriverManager.getConnection(url, "team003", "20ec79a9");
 
          PreparedStatement ps = con.prepareStatement(insert);
          
          ps.setString(1, VenueName);
          ps.setString(2, Address);
          ps.setString(3, URL);
          ps.setString(4, Description);
          ps.executeUpdate();
          con.close();

      } catch (Exception ex) {
          Logger.getLogger(database.class.getName()).log(
                           Level.SEVERE, null, ex);
      }
  }

}