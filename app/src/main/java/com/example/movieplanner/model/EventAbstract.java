package com.example.movieplanner.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public abstract class EventAbstract implements Event {
	private String id;
	private String title;
	private Date sdate;
	private Date edate;
	private String venue;
	private String location;
	private MovieImpl movie;
	private boolean isdismiss;
	private Map<String, AttendeesAbstract> contacts;
	private ArrayList<Integer> notifications;
	private Timer timer;

	
	public EventAbstract(String id, String title, Date sdate, Date edate, String venue, String location
			) {
		this.id = id;
		this.title = title;
		this.sdate = sdate;
		this.edate = edate;
		this.venue = venue;
		this.location = location;
		this.movie=null;
		this.isdismiss=false;
		contacts=new HashMap<String, AttendeesAbstract>();
		this.notifications = new ArrayList<>();
		this.timer = null;
	}


	@Override
	public String getId() {
		return id;
	}


	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Map<String, AttendeesAbstract> getcontacts(){
		return contacts;
	}

	@Override
	public String getTitle() {
		return title;
	}


	@Override
	public void setTitle(String title) {
		this.title = title;
	}


	public Date getSdate() {
		return sdate;
	}


	@Override
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}


	@Override
	public Date getEdate() {
		return edate;
	}


	@Override
	public void setEdate(Date edate) {
		this.edate = edate;
	}


	@Override
	public String getVenue() {
		return venue;
	}


	@Override
	public void setVenue(String venue) {
		this.venue = venue;
	}


	@Override
	public String getLocation() {
		return location;
	}


	@Override
	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public MovieImpl getMovie(){
		return movie;
	}

	@Override
	public void setMovie(MovieImpl smovie){
		this.movie=smovie;
	}

	@Override
	public void addatten(AttendeesAbstract con) {
		contacts.put(con.getemail(), con);
	}

	@Override
	public void removeatten(String email) {
		contacts.remove(email);
	}

	@Override
	public boolean isIsdismiss() {
		return isdismiss;
	}

	@Override
	public void setIsdismiss(boolean isdismiss) {
		this.isdismiss = isdismiss;
	}

	@Override
	public ArrayList<Integer> getNotifications() {
		return notifications;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}
