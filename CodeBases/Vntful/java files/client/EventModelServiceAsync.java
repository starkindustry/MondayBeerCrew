package com.lurn2kode.gwt.vntful.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.lurn2kode.gwt.vntful.shared.EventModel;

public interface EventModelServiceAsync {
	void getEventModelDetails(String keyword, String location, String radius,
			String dateRange, String sortOrder,
			AsyncCallback<List<EventModel>> callback);

}
