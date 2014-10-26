package com.lurn2kode.gwt.vntful.server;


public class FullEventfulResponse {
	private int total_items;
	private EventsContainer events;
	
	public EventsContainer getNestedEvents() {
		return events;
	}
	
	public void setNestedEvents(EventsContainer nEvents) {
		this.events = nEvents;
	}
	
	public int getTotalItems() {
		return total_items;
	}
	
	public void setTotalItems(int tItems) {
		this.total_items = tItems;
	}
}
