package com.lurn2kode.gwt.vntful.shared;

import java.io.Serializable;

public class EventModel implements Serializable {
	private String id;
	private String start_time;
	private String stop_time;
	private String title;
	private String city_name;
	private String venue_name;
	private String region_name;
	private String url;
	private String latitude;
	private String longitude;
	private String description;
	private Image image;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStartDateTime() {
		return start_time;
	}

	public void setStartDateTime(String startDateTime) {
		this.start_time = startDateTime;
	}

	public String getStopDateTime() {
		return stop_time;
	}

	public void setStopDateTime(String stopDateTime) {
		this.stop_time = stopDateTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCity() {
		return city_name;
	}

	public void setCity(String city) {
		this.city_name = city;
	}

	public String getVenueName() {
		return venue_name;
	}

	public void setVenueName(String venueName) {
		this.venue_name = venueName;
	}

	public String getRegionName() {
		return region_name;
	}

	public void setRegionName(String regionName) {
		this.region_name = regionName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String descr) {
		this.description = descr;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String lon) {
		this.longitude = lon;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String lat) {
		this.latitude = lat;
	}
}
