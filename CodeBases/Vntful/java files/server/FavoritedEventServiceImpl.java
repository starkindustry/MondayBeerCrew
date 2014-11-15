package com.lurn2kode.gwt.vntful.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.lurn2kode.gwt.vntful.client.FavoritedEventService;

public class FavoritedEventServiceImpl extends RemoteServiceServlet implements
		FavoritedEventService {

	PersistenceManagerFactory manager = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	@Override
	public void addEvent(HashMap<String, String> event) {
		PersistenceManager pm = getPersistenceManager();
		FavoritedEvent favoriteEvent = buildFavoritedEvent(event);
		try {
			pm.makePersistent(favoriteEvent);
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void removeEvent(String eventId) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(FavoritedEvent.class, "user == u");
			List<FavoritedEvent> events = (List<FavoritedEvent>) query
					.execute(getUser());
			for (FavoritedEvent event : events) {
				if (event.getEventId() != null
						&& event.getEventId().equals(eventId)) {
					pm.deletePersistent(event);
				}
			}
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HashMap<String, String>> getEvents() {
		PersistenceManager pm = getPersistenceManager();
		List<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();
		try {
			Query query = pm.newQuery(FavoritedEvent.class, "user == u");
			List<FavoritedEvent> events = (List<FavoritedEvent>) query
					.execute(getUser());
			for (FavoritedEvent event : events) {
				eventList.add(buildFavoritedEventMap(event));
			}
		} finally {
			pm.close();
		}
		return eventList;
	}

	private PersistenceManager getPersistenceManager() {
		return manager.getPersistenceManager();
	}

	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

	private HashMap<String, String> buildFavoritedEventMap(FavoritedEvent event) {
		HashMap<String, String> eventMap = new HashMap<String, String>();
		eventMap.put("title", event.getTitle());
		eventMap.put("id", event.getEventId());
		eventMap.put("city_name", event.getCity());
		eventMap.put("start_time", event.getStartTime());
		eventMap.put("stop_time", event.getStopTime());
		eventMap.put("venue_name", event.getVenue());
		eventMap.put("url", event.getUrl());
		eventMap.put("description", event.getDescription());
		eventMap.put("image", event.getImageUrl());
		return eventMap;
	}

	private FavoritedEvent buildFavoritedEvent(HashMap<String, String> eventData) {
		FavoritedEvent event = new FavoritedEvent(eventData.get("title"));
		event.setUser(getUser());
		event.setEventId(eventData.get("id"));
		event.setCity(eventData.get("city_name"));
		event.setStartTime(eventData.get("start_time"));
		event.setStopTime(eventData.get("stop_time"));
		event.setVenue(eventData.get("venue_name"));
		event.setUrl(eventData.get("url"));
		String description = eventData.get("description");
		description = description == null ? "" : description;
		event.setDescription(description);
		event.setImageUrl(eventData.get("image"));
		return event;
	}
}
