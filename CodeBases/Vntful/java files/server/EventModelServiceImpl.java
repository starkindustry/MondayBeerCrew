package com.lurn2kode.gwt.vntful.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.lurn2kode.gwt.vntful.client.EventModelService;
import com.lurn2kode.gwt.vntful.shared.EventModel;

@SuppressWarnings("serial")
public class EventModelServiceImpl extends RemoteServiceServlet implements
		EventModelService {

	@Override
	public List<EventModel> getEventModelDetails(String search,
			String location, String radius, String dateRange, String sortOrder)
			throws IOException {
		String urlString = new EventfulURIBuilder(location)
				.setSearchKeyword(search).setRadius(radius).setDate(dateRange)
				.setSortOrder(sortOrder).buildURI();

		System.out.println("Built URI: " + urlString);
		// get an array of nested events, convert to List<EventModel> before
		// returning
		EventsContainer eContainer = getEventsFromEventfulRequest(urlString);

		// get an array of nested events, convert to List<EventModel> before
		// returning
		List<EventModel> emList = eContainer.getNestedEventArray();

		return emList;
	}

	private EventsContainer getEventsFromEventfulRequest(String url) {

		HttpURLConnection con = null;
		int responseCode = 0;
		StringBuffer response = new StringBuffer();
		String responseString = new String();

		try {
			con = setUpHTTPConnection(url);
			responseCode = con.getResponseCode();
		} catch (IOException e) {
			// If an exception is caught here, most likely it is a bug from the
			// URI Builder.
			System.out
					.println("------- Unable to create HttpURLConnection --------");
			e.printStackTrace();
		}

		if (responseCode == 200) {
			responseString = retrieveResponse(con, response);
		} else {
			Window.alert("Error: " + responseCode);
		}

		// After retrieving response, parse it with GsonParseResponse
		GsonResponseParser parser = new GsonResponseParser();
		FullEventfulResponse fullResponse = parser
				.parseEventfulResponse(responseString);
		EventsContainer eCon = fullResponse.getNestedEvents();

		return eCon;
	}

	private HttpURLConnection setUpHTTPConnection(String url)
			throws IOException {
		URL urlObj = new URL(url);

		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setRequestMethod("GET");

		return con;
	}

	private String retrieveResponse(HttpURLConnection con, StringBuffer response) {
		String inputLine;

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

		} catch (IOException e) {
			Window.alert("Cannot read request");
			e.printStackTrace();
		}
		return response.toString();

	}

}
