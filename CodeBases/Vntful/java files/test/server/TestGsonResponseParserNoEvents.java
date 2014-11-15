package com.lurn2kode.gwt.vntful.server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestGsonResponseParserNoEvents {
	private final String NO_EVENTS_RESPONSE = "{'last_item': null, 'total_items': '0', 'first_item': null, 'page_number': '1', 'page_size': '10', 'page_items': null, 'search_time': '0.021', 'page_count': '0', 'events': null }";
	
	GsonResponseParser responseParser;

	@Before
	public void setUp() throws Exception {
		responseParser = new GsonResponseParser();
	}

	@Test
	public void testParseNoEvents() {
		FullEventfulResponse fullResponse = responseParser.parseEventfulResponse(NO_EVENTS_RESPONSE);
		assertEquals(fullResponse.getTotalItems(), 0);
		assertNotNull(fullResponse.getNestedEvents()); //Expect an EventsContainer
		assertEquals(fullResponse.getNestedEvents().getNestedEventArray().size(), 0);
	}

}
