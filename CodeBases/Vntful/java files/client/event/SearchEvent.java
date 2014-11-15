package com.lurn2kode.gwt.vntful.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SearchEvent extends GwtEvent<SearchEventHandler> {

	public static Type<SearchEventHandler> TYPE = new Type<SearchEventHandler>();

	String keyword;
	String location;
	String radius;
	String startdate;
	String endDate;

	public SearchEvent(String keyword, String location, String radius,
			String dateRange, String sortOrder) {

	}

	@Override
	public Type<SearchEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void dispatch(SearchEventHandler handler) {
		handler.onSearch(this);
	}

}
