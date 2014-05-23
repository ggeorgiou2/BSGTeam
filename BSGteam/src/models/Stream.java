package models;

/**
 * Holds the objects used for streaming live checkin information
 * @author BSGTeam
 *
 */
public class Stream {

	private int id;
	private String userId;
	private String venue;
	private String date;

	/**
	 * @param userId user's id
	 * @param venue name of venue
	 * @param date checkin time
	 */
	public Stream(int id, String userId, String venue, String date) {
		super();
		this.id=id;
		this.userId = userId;
		this.venue = venue;
		this.date = date;
	}

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
	}

}
