package models;

/**
 * UserModel.java
 * 
 * This is the model class to store user data in the database
 *
 */
public class UserModel {

	private String name;
	private String email;
	private String mailing;

	public UserModel(String name, String email, String mailing) {
		this.email = email;
		this.name = name;
		this.mailing = mailing;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", email=" + email + ", mailing="
				+ mailing + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMailing() {
		return mailing;
	}

	public void setMailing(String mailing) {
		this.mailing = mailing;
	}

}
