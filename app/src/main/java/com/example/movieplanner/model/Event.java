package com.example.movieplanner.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public interface Event {

	void addatten(AttendeesAbstract con);
	void removeatten(String email);
	String getTitle();
	void setTitle(String title);
	Date getSdate();
	void setSdate(Date sdate);
	Date getEdate();
	void setEdate(Date edate);
	String getVenue();
	void setVenue(String venue);
	String getLocation();
	void setLocation(String location);
	void setMovie(MovieImpl movie);
	MovieImpl getMovie();
	Map<String, AttendeesAbstract> getcontacts();
	void setId(String id);
	String getId();

	boolean isIsdismiss();

	void setIsdismiss(boolean isdismiss);

	ArrayList<Integer> getNotifications();
}
