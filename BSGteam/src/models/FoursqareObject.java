/**
 * 
 */
package models;

/**
 * @author Solomon
 *
 */
public class FoursqareObject {

	private String fsAPI;
	private String authToken;
	
	/**
	 * 
	 */
	public FoursqareObject() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param fsAPI
	 * @param authToken
	 */
	public FoursqareObject(String fsAPI, String authToken) {
		super();
		this.fsAPI = fsAPI;
		this.authToken = authToken;
	}

	/**
	 * @return the fsAPI
	 */
	public String getFsAPI() {
		return fsAPI;
	}

	/**
	 * @param fsAPI the fsAPI to set
	 */
	public void setFsAPI(String fsAPI) {
		this.fsAPI = fsAPI;
	}

	/**
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * @param authToken the authToken to set
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

}
