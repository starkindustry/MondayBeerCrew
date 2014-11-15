package com.lurn2kode.gwt.vntful.client;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FavoritedEventServiceAsync {

	void addEvent(HashMap<String, String> event, AsyncCallback<Void> callback);

	void removeEvent(String eventId, AsyncCallback<Void> callback);

	void getEvents(AsyncCallback<List<HashMap<String, String>>> callback);

}
