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
  
  public static void twitterDB(String screenName, String Location, String Description, Status Text) {
      try {

          String insert = "INSERT INTO twitter(picture,userID,username,location,description,tweet,retweeted) VALUES (?, ?, ?, ?, ?, ?, ?)";

          Class.forName("com.mysql.jdbc.Driver");
          Connection con = DriverManager.getConnection(url, "team003", "20ec79a9");
 
          PreparedStatement ps = con.prepareStatement(insert);
          
          ps.setString(1, "picture");
          ps.setString(2, "userID");
          ps.setString(3, screenName);
          ps.setString(4, Location);
          ps.setString(5, Description);
          ps.setString(6, "tweet");
          ps.setString(7, "retweeted");
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