
package com.lurn2kode.gwt.vntful.server;

import java.lang.reflect.Type;
import java.util.Arrays;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class FullEventfulResponseDeserializer implements
		JsonDeserializer<FullEventfulResponse> {

	@Override
	public FullEventfulResponse deserialize(JsonElement json, Type arg1,
			JsonDeserializationContext context) throws JsonParseException {
		
		// convert JSONelement into a JSONobject
		final JsonObject jsonObject = json.getAsJsonObject();
		EventsContainer eventsContainer = new EventsContainer();
		
		final FullEventfulResponse fullEventfulResponse = new FullEventfulResponse();
		final int totalItems = jsonObject.get("total_items").getAsInt();
		fullEventfulResponse.setTotalItems(totalItems);
		
		if (totalItems > 0) {
			eventsContainer = context.deserialize(jsonObject.get("events"), EventsContainer.class);
		}
		fullEventfulResponse.setNestedEvents(eventsContainer);
		return fullEventfulResponse;
	}

}
