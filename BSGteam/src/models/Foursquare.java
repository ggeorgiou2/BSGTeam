package models;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.regex.*;

import twitter4j.QueryResult;
import twitter4j.Status;
import fi.foyt.foursquare.api.*;
import fi.foyt.foursquare.api.entities.*;

public class Foursquare {

	
	public Foursquare() {
		super();
	}

	public String getFullURL (String shortURLs) throws IOException {
		URL shortUrl= new URL(shortURLs);
		final HttpURLConnection httpURLConnection =
		(HttpURLConnection)shortUrl.openConnection();
		httpURLConnection.setInstanceFollowRedirects(false);
		httpURLConnection.connect();
		return (httpURLConnection.getHeaderField("Location")); 
	}
	
	public String expandUrl (String shortURLs) {
		String url = shortURLs;
		//String initialUrl = shortURLs;
		while (url!=null){
		try {
		url = getFullURL(shortURLs);
		if (url!=null) shortURLs= url;
		else {
		url= shortURLs;
		break;
		}
		} catch (IOException e) {
		// this is not a tiny URL as it is not redirected!
			break;
		}
		}
		return url;
	}
	
//	public FoursquareApi authenticate() {
//	FoursquareApi fsAPI = new FoursquareApi("O2A21N0HUIM5UVFL2AYY4OMQ35DIUKVYBCVR5EJSHZWP52UF",
//			"FVL0GI21DP5ULAAM5BHO4I4X3D4YQNWHKOTVQDDZDWBCXCYV", "http://www.sheffield.ac.uk"); 
//	
//	fsAPI.setoAuthToken("3BD5LBHSXOQQGA2NFRWQYQ4R44XUTSMXZCXIQDCFGIWLIOYN");
//	return fsAPI;
//	}
	
	public CompactVenue getLocationInformation(String shortURLs) throws FoursquareApiException {
		FoursquareApi fsAPI = new FoursquareApi("O2A21N0HUIM5UVFL2AYY4OMQ35DIUKVYBCVR5EJSHZWP52UF",
				"FVL0GI21DP5ULAAM5BHO4I4X3D4YQNWHKOTVQDDZDWBCXCYV", "http://www.sheffield.ac.uk"); 
			fsAPI.setoAuthToken("3BD5LBHSXOQQGA2NFRWQYQ4R44XUTSMXZCXIQDCFGIWLIOYN");
		
		// expand the url if it is a short url!
		String url= expandUrl(shortURLs);
		//if it is not a 4square login url then we return!
		if (!((url.startsWith("https://foursquare.com/"))&&(url.contains("checkin"))&&(url.contains("s=")))) 
			return null;
		//url now contains the full url!
		Pattern pId = Pattern.compile(".+?checkin/(.+?)\\?s=.+", Pattern.DOTALL);
		Matcher matche = pId.matcher(url);
		String checkInId = (matche.matches()) ? matche.group(1) : "";
		Pattern pSig = Pattern.compile(".+?\\?s=(.*)\\&.+", Pattern.DOTALL);
		matche = pSig.matcher(url);
		String sig = (matche.matches()) ? matche.group(1) : "";
		Result<Checkin> chck = null;
		try {
		chck = fsAPI.checkin(checkInId, sig);
		} catch (FoursquareApiException e) {
			System.out.println("fsq excep");
		e.printStackTrace(); 
		}
		Checkin cc = chck.getResult();
		//CompactUser user= cc.getUser();
		//System.out.print("CHECK IN: " + user.getFirstName() + " " + user.getLastName() + " (" + user.getGender());
		//System.out.print(") Just checked in at: ");
		CompactVenue venue= cc.getVenue();
		//System.out.print(" " + venue.getName());
		//Location loc= venue.getLocation();
		//System.out.print(" " + loc.getAddress() + " " + loc.getCity());
		//System.out.println(" " + loc.getLat() + ", " + loc.getLng());
		return venue;
		}
	
	public List<CompactVenue> checkins(QueryResult result) {
		List<CompactVenue> venues = new ArrayList<CompactVenue>();
		for (Status status : result.getTweets()) {
			if (status.getGeoLocation() != null) {
				int index = status.getText().indexOf("http");
				String data = "nan";
				if (index >= 0) {
					data = status.getText().substring(index);
					try {
							venues.add(getLocationInformation(data));
					} catch (FoursquareApiException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return venues;
	}
	public Photo[] getImages(String venueID)
	{
		FoursquareApi fsAPI = new FoursquareApi("O2A21N0HUIM5UVFL2AYY4OMQ35DIUKVYBCVR5EJSHZWP52UF",
				"FVL0GI21DP5ULAAM5BHO4I4X3D4YQNWHKOTVQDDZDWBCXCYV", "http://www.sheffield.ac.uk"); 
			fsAPI.setoAuthToken("3BD5LBHSXOQQGA2NFRWQYQ4R44XUTSMXZCXIQDCFGIWLIOYN");
			Result<PhotoGroup> venuephoto;
			Photo[] image = null;

			try {
				venuephoto = fsAPI.venuesPhotos(venueID, "venue", null, null);
			
			if (venuephoto.getResult().getCount()>0)
			{
				image = venuephoto.getResult().getItems();
			}
			} catch (FoursquareApiException e) {
				e.printStackTrace();
			}
			return image;
	}
}
