package com.example.movieplanner.model;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public abstract class AttendeesAbstract implements Attendee{

	private String email;
	private String name;
	private String phone;
	
	public AttendeesAbstract(String email, String name, String phone) {
		this.email=email;
		this.name = name;
		this.phone = phone;
	}

	public String getemail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

}
