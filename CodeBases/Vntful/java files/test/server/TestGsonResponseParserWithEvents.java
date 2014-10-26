package com.lurn2kode.gwt.vntful.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.lurn2kode.gwt.vntful.shared.EventModel;


@RunWith(Parameterized.class)
public class TestGsonResponseParserWithEvents {

	private final static String SAMPLE_JSON_DATA = "sampleEventfulJson.properties";
	
	private static Properties testData;	
	private static Properties expectedData;
	private Properties expectedDataSet;
	private static GsonResponseParser responseParser;
	
	// Parameters go into these fields
	private String jsonResponse;
	
	public TestGsonResponseParserWithEvents(String expectedId, String jsonResponse) {
		this.jsonResponse = jsonResponse;
		
		// The keys in the sampleEventfulJson.properties represent the filename for the expected output .properties file
		String expectedDataPropertiesFile = expectedId + ".properties";
		
		expectedData = new Properties();
		InputStream inStream = TestGsonResponseParserWithEvents.class.getResourceAsStream(expectedDataPropertiesFile);
		
		try {
			expectedData.load(inStream);
		} catch (IOException e) {
			System.out.println("Error reading expected data .properties file");
			e.printStackTrace();
		}
		
		this.expectedDataSet = expectedData;
		
		
		
	}
	
	private static void initializeTest() {
		testData = new Properties();
		responseParser = new GsonResponseParser();
		InputStream inStream = TestGsonResponseParserWithEvents.class.getResourceAsStream(SAMPLE_JSON_DATA);
		
		try {
			testData.load(inStream);
			inStream.close();
		} 
		catch (IOException e) {
			System.out.println("Error reading .properties file!");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Cannot rely on @Before or @BeforeClass to be called when using @Parameters.  Need initializeTest() to
	 * setup the test.
	 * 
	 * Hashmap to 2d array idea from: http://stackoverflow.com/questions/2265266/convert-hash-map-to-2d-array
	 * @return A list of parameters to run test with
	 */
	@Parameters
	public static Collection eventIds() {
		
		initializeTest();
		
		String arr[][] = new String[testData.size()][2];
		int i = 0;
		for (Map.Entry<Object, Object> entry: testData.entrySet()) {
			arr[i][0] = (String) entry.getKey();
			arr[i][1] = (String) entry.getValue();

			i++;
		}
		
		return Arrays.asList(arr);
	}


	/*
	 * Tests to match the first Event ID of the parsed JSON response
	 */
	@Test
	public void testGsonResponseParser() {
		
		List<EventModel> eventModels = responseParser.parseEventfulResponse(jsonResponse).getNestedEvents().getNestedEventArray();
		
		assertEquals(Integer.parseInt((String)expectedDataSet.get("actual_length")), eventModels.size());
		
		int testLength = Integer.parseInt((String)expectedDataSet.get("test_length"));
		
		for (int i = 0; i<testLength; i++) {
			assertEquals(getNullHandledExpectedDataSet("id", i), eventModels.get(i).getId());
			assertEquals(getNullHandledExpectedDataSet("start_time", i), eventModels.get(i).getStartDateTime());
			assertEquals(getNullHandledExpectedDataSet("stop_time", i), eventModels.get(i).getStopDateTime());
			assertEquals(getNullHandledExpectedDataSet("title", i), eventModels.get(i).getTitle());
			assertEquals(getNullHandledExpectedDataSet("city_name", i), eventModels.get(i).getCity());
			assertEquals(getNullHandledExpectedDataSet("venue_name", i), eventModels.get(i).getVenueName());
			assertEquals(getNullHandledExpectedDataSet("region_name", i), eventModels.get(i).getRegionName());
			assertEquals(getNullHandledExpectedDataSet("url", i), eventModels.get(i).getUrl());
			assertEquals(getNullHandledExpectedDataSet("latitude", i), eventModels.get(i).getLatitude());
			assertEquals(getNullHandledExpectedDataSet("longitude", i), eventModels.get(i).getLongitude());
			assertEquals(getNullHandledExpectedDataSet("description", i), eventModels.get(i).getDescription());
		}
		
	}
	
	private String getNullHandledExpectedDataSet(String key, int index) {
		String returnString = (String)expectedDataSet.get(key + index);
		
		if (returnString.equals("null")) {
			return null;
		}
		return returnString;
	}

}
