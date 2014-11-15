package com.lurn2kode.gwt.vntful.client;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("favorite")
public interface FavoritedEventService extends RemoteService {

	public void removeEvent(String eventId);

	public List<HashMap<String, String>> getEvents();

	void addEvent(HashMap<String, String> event);

}