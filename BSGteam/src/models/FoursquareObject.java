package models;

/**
 * This class holds the token values for foursquare API authentication
 * 
 * @author BSGTeam
 *
 */
public class FoursquareObject {

	private String clientID;
	private String clientSecret;
	private String redirectURL;
	private String accessToken;
	
	/**
	 * 
	 */
	public FoursquareObject() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param clientID
	 * @param clinetSec
	 * @param redirectURL
	 * @param accessToken
	 */
	public FoursquareObject(String clientID, String clinetSec,
			String redirectURL, String accessToken) {
		super();
		this.clientID = clientID;
		this.clientSecret = clinetSec;
		this.redirectURL = redirectURL;
		this.accessToken = accessToken;
	}

	/**
	 * @return the clientID
	 */
	public String getClientID() {
		return clientID;
	}

	/**
	 * @param clientID the clientID to set
	 */
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	/**
	 * @return the clinetSec
	 */
	public String getClinetSec() {
		return clientSecret;
	}

	/**
	 * @param clinetSec the clinetSec to set
	 */
	public void setClinetSec(String clinetSec) {
		this.clientSecret = clinetSec;
	}

	/**
	 * @return the redirectURL
	 */
	public String getRedirectURL() {
		return redirectURL;
	}

	/**
	 * @param redirectURL the redirectURL to set
	 */
	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
