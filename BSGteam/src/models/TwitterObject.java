package models;

/**
 * This class holds the token values for twitter API authentication
 * @author BSG Team
 * 
 */
public class TwitterObject {
	private String token_access;
	private String token_secret;
	private String customer_key;
	private String customer_secret;

	/**
	 * @param token_access
	 * @param token_secret
	 * @param customer_key
	 * @param customer_secret
	 */
	public TwitterObject(String token_access, String token_secret,
			String customer_key, String customer_secret) {
		super();
		this.token_access = token_access;
		this.token_secret = token_secret;
		this.customer_key = customer_key;
		this.customer_secret = customer_secret;
	}

	public TwitterObject() {
	}

	/**
	 * @return the token_access
	 */
	public String getToken_access() {
		return token_access;
	}

	/**
	 * @param tokenAccess
	 *            the token_access to set
	 */
	public void setToken_access(String tokenAccess) {
		token_access = tokenAccess;
	}

	/**
	 * @return the token_secret
	 */
	public String getToken_secret() {
		return token_secret;
	}

	/**
	 * @param tokenSecret
	 *            the token_secret to set
	 */
	public void setToken_secret(String tokenSecret) {
		token_secret = tokenSecret;
	}

	/**
	 * @return the customer_key
	 */
	public String getCustomer_key() {
		return customer_key;
	}

	/**
	 * @param customerKey
	 *            the customer_key to set
	 */
	public void setCustomer_key(String customerKey) {
		customer_key = customerKey;
	}

	/**
	 * @return the customer_secret
	 */
	public String getCustomer_secret() {
		return customer_secret;
	}

	/**
	 * @param customerSecret
	 *            the customer_secret to set
	 */
	public void setCustomer_secret(String customerSecret) {
		customer_secret = customerSecret;
	}

}
