package com.lurn2kode.gwt.vntful.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lurn2kode.gwt.vntful.shared.EventModel;

public class EventsContainer implements Serializable {
	
	private List<EventModel> nestedEventArray;
	
	public EventsContainer() {
		nestedEventArray = new ArrayList<EventModel>();
	}
	
	public List<EventModel> getNestedEventArray() {
		return nestedEventArray;
	}
	
	public void setNestedEventArray(EventModel[] nestedEvent2) {
		if (nestedEvent2 != null) {
			nestedEventArray = Arrays.asList(nestedEvent2);
		}
	}
}
