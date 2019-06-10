package com.example.movieplanner.model;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public abstract class MovieAbstract implements Movie{
	
	private String id;
	private String title;
	private Integer year;
	private String poster;
	
	public MovieAbstract(String id, String title, Integer year, String poster) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.poster = poster;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public String getyearstring(){return this.year.toString();}

	public void setYear(int year) {
		this.year = year;
	}

	public String getPoster() {
		return poster;
	}

}
