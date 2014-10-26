package com.lurn2kode.gwt.vntful.server;

import junit.framework.TestCase;

import org.junit.Test;

public class EventfulURIBuilderTest extends TestCase {
	private EventfulURIBuilder uriBuilder;
	private final String DEFAULT_DATE = "future";
	private final String DEFAULT_PAGE_SIZE = "50";
	private final String APP_KEY = "MjgMcF6rv7JRHq7W";
	private final String baseURI = "http://api.eventful.com/json/events/search?...=&app_key=" + APP_KEY;

	@Test
	public void testDefaults() {
		uriBuilder = new EventfulURIBuilder("vancouver");

		assertEquals(DEFAULT_DATE, uriBuilder.getDate());
		assertEquals(DEFAULT_PAGE_SIZE, uriBuilder.getPageSize());
		assertEquals(APP_KEY, uriBuilder.getAppKey());
	}
	
	@Test
	public void testGetSet() {
		// We might need to add more get set interfaces?
		
		String location = "vancouver";
		uriBuilder = new EventfulURIBuilder(location);
		assertEquals(uriBuilder.getLocationKeyword(), location);
		
		String keyword = "food";
		uriBuilder.setSearchKeyword(keyword);
		assertEquals(uriBuilder.getSearchKeyword(), keyword);
	}
	
	
	@Test
	public void testDefaultValuesInBuiltURI() {
		uriBuilder = new EventfulURIBuilder("vancouver");
		
		String fullURI = uriBuilder.buildURI();
		assertEquals(baseURI, fullURI.substring(0, baseURI.length()));
		
		int appKeyLength = "&app_key=".length() + APP_KEY.length();
		String queryString = fullURI.substring(baseURI.length() - appKeyLength, fullURI.length());
		assertTrue(queryString.contains("&app_key=" + APP_KEY));
		assertTrue(queryString.contains("&date=" + DEFAULT_DATE));
		assertTrue(queryString.contains("&page_size=" + DEFAULT_PAGE_SIZE));
	}

	@Test
	public void testBothStringNull() {
		uriBuilder = new EventfulURIBuilder(null).setSearchKeyword(null);
		String fullURI = uriBuilder.buildURI();
		String queryString = fullURI.substring(baseURI.length(), fullURI.length());
		
		// These two lines might need a bit of discussion on what we want the spec to be.
		assertFalse(queryString.contains("&keyword="));
//		assertTrue(queryString.contains("&location=null")); 
		
		assertTrue(queryString.contains("&date=future"));
		assertTrue(queryString.contains("&page_size=50"));
		
	}

	@Test
	public void testRegularSearch() {
		uriBuilder = new EventfulURIBuilder("vancouver")
				.setSearchKeyword("hello");
		
		String fullURI = uriBuilder.buildURI();
		String queryString = fullURI.substring(baseURI.length(), fullURI.length());
		
		assertTrue(queryString.contains("&keywords=hello"));
		assertTrue(queryString.contains("&location=vancouver"));
		
		assertTrue(queryString.contains("&date=future"));
		assertTrue(queryString.contains("&page_size=50"));
		
	}
	
	@Test
	public void testBothMultipleWords() {
		uriBuilder = new EventfulURIBuilder("san diego").setSearchKeyword("food kimchi");
		String fullURI = uriBuilder.buildURI();
		String queryString = fullURI.substring(baseURI.length(), fullURI.length());
		
		assertTrue(queryString.contains("&location=san+diego"));
		assertTrue(queryString.contains("&keywords=food+kimchi"));
		
		assertTrue(queryString.contains("&date=future"));
		assertTrue(queryString.contains("&page_size=50"));
	}
	
	@Test
	public void testExtraParameters() {
		uriBuilder = new EventfulURIBuilder("vancouver");
		uriBuilder.setDate("date");
		uriBuilder.setRadius("100");
		uriBuilder.setSearchKeyword("food");
		uriBuilder.setSortOrder("popularity");
		
		String fullURI = uriBuilder.buildURI();
		String queryString = fullURI.substring(baseURI.length(), fullURI.length());
		
		assertTrue(queryString.contains("&date=date"));
		assertTrue(queryString.contains("&within=100"));
		assertTrue(queryString.contains("&keywords=food"));
		assertTrue(queryString.contains("&sort_order=popularity"));
	}
	
	@Test
	public void testMultipleWordsAllParameters() {
		String location = "new york";
		String date = "last week";
		String radius = "100 0";
		String keyword = "music event";
		String sortOrder = "popu larity";
		
		uriBuilder = new EventfulURIBuilder(location);
		uriBuilder.setDate(date);
		uriBuilder.setRadius(radius);
		uriBuilder.setSearchKeyword(keyword);
		uriBuilder.setSortOrder(sortOrder);
		
		String fullURI = uriBuilder.buildURI();
		String queryString = fullURI.substring(baseURI.length(), fullURI.length());
		
		assertTrue(queryString.contains("&location=new+york"));
		assertTrue(queryString.contains("&date=last+week"));
		assertTrue(queryString.contains("&within=100+0"));
		assertTrue(queryString.contains("&keywords=music+event"));
		assertTrue(queryString.contains("&sort_order=popu+larity"));
		
	}
}
