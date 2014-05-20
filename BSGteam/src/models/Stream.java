package models;

public class Stream {

	private int id;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	private String userId;
	private String venue;
	private String date;
	
	/**
	 * @param userId
	 * @param venue
	 * @param date
	 */
	public Stream(int id, String userId, String venue, String date) {
		super();
		this.id=id;
		this.userId = userId;
		this.venue = venue;
		this.date = date;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the venue
	 */
	public String getVenue() {
		return venue;
	}

	/**
	 * @param venue the venue to set
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	public Stream() {
		// TODO Auto-generated constructor stub
	}

}
