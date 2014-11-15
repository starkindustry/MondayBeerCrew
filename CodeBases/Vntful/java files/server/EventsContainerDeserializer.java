package com.lurn2kode.gwt.vntful.server;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.lurn2kode.gwt.vntful.shared.EventModel;

public class EventsContainerDeserializer implements JsonDeserializer<EventsContainer> {

	@Override
	public EventsContainer deserialize(JsonElement jElement, Type arg1,
			JsonDeserializationContext jsonContext) throws JsonParseException {
		
		JsonObject jsonObj = jElement.getAsJsonObject();
		final EventsContainer eventsContainer = new EventsContainer();
				
		// Deserialize inner "event" array elements under "events"
		if (jsonObj.get("event").isJsonArray()) {
			EventModel[] eventArray = jsonContext.deserialize(jsonObj.get("event"), EventModel[].class);
			eventsContainer.setNestedEventArray(eventArray);
		}
		else {
			EventModel singleEvent = jsonContext.deserialize(jsonObj.get("event"), EventModel.class);
			EventModel[] singleEventArray = new EventModel[] {
					singleEvent
			};
			eventsContainer.setNestedEventArray(singleEventArray);
		}
				
		return eventsContainer;
	}

}
