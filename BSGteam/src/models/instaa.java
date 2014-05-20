/**
 * 
 */
package models;

/**
 * @author acp13gg
 *
 */
public class instaa {

	/**
	 * 
	 */
	public instaa() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InstagramService service =  new InstagramAuthService()
        .apiKey("your_client_id")
        .apiSecret("your_client_secret")
        .callback("your_callback_url")     
        .build();
	}

}
