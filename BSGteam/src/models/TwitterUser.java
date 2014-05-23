package models;

/**
 * Model for users saved to the triple store
 * @author BSG Team
 *
 */
public class TwitterUser {

	private String userName;
	private String id;
	private String location;
	private String image;
	private String description;
	private String locationVisited;
	private String contactPeople;
	private String uri;

	/**
	 * @param userName user's full name
	 * @param id twitter id of user
	 * @param location location of user
	 * @param image URL of user profile image
	 * @param description user description
	 * @param locationVisited locations user has visited
	 * @param contactPeople people user has contacted
	 */
	public TwitterUser(String userName, String id, String location,
			String image, String description, String locationVisited,
			String contactPeople) {
		super();
		this.userName = userName;
		this.id = id;
		this.location = location;
		this.image = image;
		this.description = description;
		this.locationVisited = locationVisited;
		this.contactPeople = contactPeople;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the locationVisited
	 */
	public String getLocationVisited() {
		return locationVisited;
	}

	/**
	 * @param locationVisited
	 *            the locationVisited to set
	 */
	public void setLocationVisited(String locationVisited) {
		this.locationVisited = locationVisited;
	}

	/**
	 * @return the contactPeople
	 */
	public String getContactPeople() {
		return contactPeople;
	}

	/**
	 * @param contactPeople
	 *            the contactPeople to set
	 */
	public void setContactPeople(String contactPeople) {
		this.contactPeople = contactPeople;
	}

	public String[] getPeople() {
		String[] knows = null;
		if (getContactPeople() != null) {
			knows = getContactPeople().split(",");
		}
		return knows;
	}

	public String[] getLocations() {
		String[] locations = null;
		if (getLocationVisited() != null) {
			locations = getLocationVisited().split(",");
		}
		return locations;
	}

}
