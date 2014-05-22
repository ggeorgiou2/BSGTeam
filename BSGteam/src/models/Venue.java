package models;

import java.util.ArrayList;

public class Venue {

	private String visitorName;
	private String venueName;
	private String photo;
	private String category;
	private String address;
	private String description;
	private String url;
	private String checkinTime;
	private String uri;

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	
	/**
	 * @param visitorName
	 * @param venueName
	 * @param photo
	 * @param category
	 * @param address
	 * @param description
	 * @param url
	 * @param checkinTime
	 */
	public Venue(String visitorName, String venueName, String url,
			String address, String description, String photo, String category,
			String checkinTime) {
		super();
		this.visitorName = visitorName;
		this.venueName = venueName;
		this.photo = photo;
		this.category = category;
		this.address = address;
		this.description = description;
		this.url = url;
		this.checkinTime = checkinTime;
	}

	/**
	 * @return the visitorName
	 */
	public String getVisitorName() {
		return visitorName;
	}

	/**
	 * @param visitorName
	 *            the visitorName to set
	 */
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	/**
	 * @return the venueName
	 */
	public String getVenueName() {
		return venueName;
	}

	/**
	 * @param venueName
	 *            the venueName to set
	 */
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	/**
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * @param photo
	 *            the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the checkinTime
	 */
	public String getCheckinTime() {
		return checkinTime;
	}

	/**
	 * @param checkinTime
	 *            the checkinTime to set
	 */
	public void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}

	public String[] getPhotos(){
		String[] photos = null;
		if(getPhoto()!=null)
		{
		photos = getPhoto().split(" ");}
		return photos;
	}
}
