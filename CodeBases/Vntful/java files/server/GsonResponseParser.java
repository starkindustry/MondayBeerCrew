package com.lurn2kode.gwt.vntful.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonResponseParser {

	public GsonResponseParser() {
		
	}
	
	public FullEventfulResponse parseEventfulResponse(String response) {
		Gson gson = initEventfulParser();
		
		FullEventfulResponse fullResponse = getObjectFromJson(gson, response);
		
		return fullResponse;
	}
	
	private Gson initEventfulParser() {
		Gson gson = new Gson();
		GsonBuilder gsonBuilder = new GsonBuilder();
		// Registering gson deserializers 
		gsonBuilder.registerTypeAdapter(FullEventfulResponse.class, new FullEventfulResponseDeserializer());
		gsonBuilder.registerTypeAdapter(EventsContainer.class, new EventsContainerDeserializer());
		gson = gsonBuilder.create();
		
		return gson;
	}
	
	private FullEventfulResponse getObjectFromJson(Gson gson, String response) {
		FullEventfulResponse fullResponse = gson.fromJson(response, FullEventfulResponse.class);
		
		return fullResponse;
	}
	
}
