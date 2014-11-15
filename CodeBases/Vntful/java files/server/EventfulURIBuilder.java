package com.lurn2kode.gwt.vntful.server;

public class EventfulURIBuilder {

	private static final String DEFAULT_DATESTRING = "future";
	private static final String DEFAULT_LOCATION = "Vancouver";
	private static final String SORT_ORDER_PARAMETER = "&sort_order=";
	private static final String DEFAULT_PAGE_SIZE = "50";
	private static final String UNITS_PARAMETER = "&units=";
	private static final String WITHIN_PARAMETER = "&within=";
	private static final String DATE_PARAMETER = "&date=";
	private static final String LOCATION_PARAMETER = "&location=";
	private static final String KEYWORDS_PARAMETER = "&keywords=";
	private static final String APP_KEY_PARAMETER = "&app_key=";
	private static final String PAGE_SIZE_PARAMETER = "&page_size=";
	private static final String APP_KEY = "MjgMcF6rv7JRHq7W";
	private static final String BASE_URL = "http://api.eventful.com/json/events/search?...=";
	private static final String KM_UNITS = "km";

	private String searchKeyword;
	private final String location;
	private String date; // default = "Future"
	private String radius;
	private String sortOrder;

	public EventfulURIBuilder(String location) {
		this.date = DEFAULT_DATESTRING;
		this.location = replaceSpaceWithPlus(location);
	}
	

	public EventfulURIBuilder setSearchKeyword(String searchKeyword) {
		this.searchKeyword = replaceSpaceWithPlus(searchKeyword);
		return this;
	}

	public EventfulURIBuilder setRadius(String radius) {
		this.radius = replaceSpaceWithPlus(radius);
		return this;
	}

	public EventfulURIBuilder setDate(String date) {
		this.date = replaceSpaceWithPlus(date);
		return this;
	}

	public EventfulURIBuilder setSortOrder(String order) {
		this.sortOrder = replaceSpaceWithPlus(order);
		return this;
	}

	public String getAppKey() {
		return APP_KEY;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public String getLocationKeyword() {
		return location;
	}

	public String getDate() {
		return date;
	}

	public String getPageSize() {
		return DEFAULT_PAGE_SIZE;
	}
	
	public String getRadius() {
		return radius;
	}

	public String buildURI() {
		StringBuilder sb = buildBaseURI();
		if (isKeywordSet()) {
			sb.append(KEYWORDS_PARAMETER).append(searchKeyword);
		}

		if (!isLocationSet() && !isKeywordSet()) {
			sb.append(LOCATION_PARAMETER).append(DEFAULT_LOCATION);
		} else if (isLocationSet()) {
			sb.append(LOCATION_PARAMETER).append(location);
		}

		if (isRadiusSet()) {
			sb.append(WITHIN_PARAMETER).append(radius).append(UNITS_PARAMETER)
					.append(KM_UNITS);
		}

		if (isDateSet()) {
			sb.append(DATE_PARAMETER).append(date);
		}

		if (isSortOrderSet()) {
			sb.append(SORT_ORDER_PARAMETER).append(sortOrder);
		}
		sb.append(PAGE_SIZE_PARAMETER).append(DEFAULT_PAGE_SIZE);
		return sb.toString();
	}
	
	private String replaceSpaceWithPlus(String s) {
		if(s != null) {
			return s.replace(' ', '+');
		}
		return "";
	}

	private boolean isSortOrderSet() {
		return sortOrder != null && !sortOrder.isEmpty();
	}

	private boolean isDateSet() {
		return date != null && !date.isEmpty();
	}

	private boolean isRadiusSet() {
		return radius != null && !radius.isEmpty();
	}

	private boolean isKeywordSet() {
		return searchKeyword != null && !searchKeyword.isEmpty();
	}

	private boolean isLocationSet() {
		return !location.isEmpty();
	}

	private StringBuilder buildBaseURI() {
		return new StringBuilder().append(BASE_URL).append(APP_KEY_PARAMETER)
				.append(APP_KEY);
	}

}
