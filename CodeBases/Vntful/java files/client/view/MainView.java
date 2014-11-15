package com.lurn2kode.gwt.vntful.client.view;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ButtonGroup;
import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.Dropdown;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.NavWidget;
import com.github.gwtbootstrap.client.ui.Navbar;
import com.github.gwtbootstrap.client.ui.ProgressBar;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.datepicker.client.ui.DateBox;
import com.google.gwt.core.client.Callback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasCellPreviewHandlers;
import com.lurn2kode.gwt.vntful.client.ButtonFactory;
import com.lurn2kode.gwt.vntful.client.ListBoxFactory;
import com.lurn2kode.gwt.vntful.client.NavBarBuilder;
import com.lurn2kode.gwt.vntful.client.PopUpBuilder;
import com.lurn2kode.gwt.vntful.client.ProgressBarFactory;
import com.lurn2kode.gwt.vntful.client.TableFactory;
import com.lurn2kode.gwt.vntful.client.presenter.MainPresenter;
import com.lurn2kode.gwt.vntful.shared.SortOrder;

public class MainView extends Composite implements MainPresenter.Display {

	private static final String SCROLL_HEIGHT = "500px";
	private static final String SCROLL_WIDTH = "1000px";
	private static final String MAP_WIDTH = "1000px";
	private static final String MAP_HEIGHT = "400px";
	private static final String LOCATION_PLACEHOLDER = "Location (e.g. Vancouver)";
	private static final String SEARCH_PANEL = "searchPanel";
	private static final String KEYWORD = "keyword";
	private static final String LOCATION = "location";
	private static final String TITLE_PLACEHOLDER = "Title";
	private static final String ADV_SEARCH = "Advanced Search";
	private static final String ADV_SEARCH_CBOX_STYLE_NAME = "advancedSearch";
	private static final LatLng DEFAULT_LATLONG = LatLng.newInstance(49.25,
			-123.1);
	private static final int DEFAULT_ZOOM_LEVEL = 12;

	// Expose the presenter for dynamic widgets
	private MainPresenter presenter;

	private final Button searchButton;
	private final VerticalPanel mainPanel;
	private final HorizontalPanel searchPanel;
	private final FlowPanel mapPanel;
	private final HorizontalPanel advSearchPanel;
	private final ScrollPanel scrollPanel;
	private final ProgressBar progressBar;
	private final ListBox radiusListBox;
	private final CellTable<HashMap<String, String>> eventTable;
	// private final List<HashMap<String, String>> eventCoordinates;
	private final TextBox keywordBox;
	private final TextBox locationBox;
	private final MapWidget eventMap;
	private final Button saveEventButton;
	private final Button removeEventButton;
	private final ButtonGroup favoriteEventButtons;
	private HashMap<String, String> favoriteEvent;

	private final Alert alert;

	// Adv Search Fields
	private final CheckBox advSearchCheckBox;
	private final ListBox sortOrderBox;
	private final DateBox startDateBox;
	private final DateBox endDateBox;

	// Navigation Bar
	private final Navbar navBar;
	private final Dropdown recentSearches;
	private final NavWidget favEvents;
	private final NavWidget logOutLink;

	public MainView() {
		mainPanel = new VerticalPanel();
		searchPanel = new HorizontalPanel();
		advSearchPanel = new HorizontalPanel();
		initWidget(mainPanel);

		mainPanel.setStyleName(SEARCH_PANEL, true);
		advSearchPanel.setVisible(false);

		keywordBox = buildSearchTextBox(TITLE_PLACEHOLDER);
		keywordBox.setStyleDependentName(KEYWORD, true);

		locationBox = buildSearchTextBox(LOCATION_PLACEHOLDER);
		locationBox.setStyleDependentName(LOCATION, true);

		searchButton = ButtonFactory.createSearchButton();
		saveEventButton = ButtonFactory.createSaveEventButton();
		removeEventButton = ButtonFactory.createRemoveEventButton();
		favoriteEventButtons = new ButtonGroup(saveEventButton,
				removeEventButton);

		advSearchCheckBox = buildAdvSearchCheckBox(ADV_SEARCH);
		advSearchCheckBox.setStyleDependentName(ADV_SEARCH_CBOX_STYLE_NAME,
				true);

		// Build Navigation Bar and Nav components
		navBar = new Navbar();
		recentSearches = new Dropdown("Recently searched");
		favEvents = new NavWidget();
		logOutLink = new NavWidget();
		NavBarBuilder navBarBuilder = NavBarBuilder.getNavBarBuilder();
		navBarBuilder.createNavBar(navBar, recentSearches, favEvents, logOutLink);

		ListBoxFactory listBoxFactory = new ListBoxFactory();
		radiusListBox = listBoxFactory.getListBox("radius");

		searchPanel.add(navBar);
		searchPanel.add(keywordBox);
		searchPanel.add(locationBox);
		searchPanel.add(searchButton);
		searchPanel.add(advSearchCheckBox); // checkbox for advanced search

		// Add advanced search features
		sortOrderBox = listBoxFactory.getListBox(ListBoxFactory.SORT_ORDER);
		sortOrderBox.setWidth("auto");

		startDateBox = buildDateBox("Start Date");
		endDateBox = buildDateBox("End Date");
		addAdvOptionsToPanel(advSearchPanel);

		eventTable = TableFactory.createTable();

		scrollPanel = constructScrollPanel();
		scrollPanel.add(eventTable);
		scrollPanel.setVisible(false);

		progressBar = ProgressBarFactory.createProgressBar();

		eventMap = instantiateMap();

		mapPanel = constructMapPanel();
		mapPanel.add(eventMap);
		mapPanel.setVisible(false);

		alert = new Alert();
		alert.setClose(false);
		alert.setVisible(false);

		mainPanel.add(searchPanel);
		mainPanel.add(advSearchPanel);
		mainPanel.add(progressBar);
		mainPanel.add(alert);
		mainPanel.add(scrollPanel);
		mainPanel.add(mapPanel);
	}

	@Override
	public HasClickHandlers getSearchButton() {
		return searchButton;
	}

	@Override
	public HasClickHandlers getAdvSearchCheckBox() {
		return advSearchCheckBox;
	}

	@Override
	public HasClickHandlers getSaveEventButtonHandler() {
		return saveEventButton;
	}

	@Override
	public HasClickHandlers getRemoveEventButtonHandler() {
		return removeEventButton;
	}

	@Override
	public HasClickHandlers getFavoritePageLink() {
		return favEvents;
	}
	
	@Override
	public HasClickHandlers getLogoutLink() {
		return logOutLink;
	}

	@Override
	public HasKeyUpHandlers getKeywordBox() {
		return keywordBox;
	}

	@Override
	public HasKeyUpHandlers getLocationBox() {
		return locationBox;
	}

	@Override
	public HasCellPreviewHandlers<HashMap<String, String>> getTableRowSelection() {
		return eventTable;
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public String getKeyword() {
		String keyword = keywordBox.getValue();
		keywordBox.setValue("");
		return keyword;
	}

	@Override
	public String getLocation() {
		String location = locationBox.getValue();
		locationBox.setValue("");
		return location;
	}

	@Override
	public String getRadius() {
		String radius = radiusListBox.getValue();
		if (radius.equals(ListBoxFactory.RADIUS_PLACEHOLDER_OPTION)) {
			radius = "";
		}
		radiusListBox.setItemSelected(0, true);
		return radius;
	}

	@Override
	public DateBox getStartDateBox() {
		return startDateBox;
	}

	@Override
	public DateBox getEndDateBox() {
		return endDateBox;
	}

	@Override
	public Dropdown getDropdown() {
		return recentSearches;
	}

	@Override
	public void setAlert(AlertType type, String message) {
		alert.setText(message);
		alert.setType(type);
	}

	@Override
	public void toggleAlert(boolean visible) {
		alert.setVisible(visible);

	}

	@Override
	public String getSortOrder() {
		String sortOrder = sortOrderBox.getValue();
		if (!SortOrder.contains(sortOrder)) {
			sortOrder = "";
		}
		sortOrderBox.setItemSelected(0, true);
		return sortOrder;
	}

	@Override
	public void setEvents(List<HashMap<String, String>> events) {
		scrollPanel.setVisible(true);
		eventTable.setRowData(events);
		mapPanel.setVisible(true);
		placeMarkers(eventMap, events);
		eventMap.checkResize();
	}

	@Override
	public void setRecentSearch(final String keyword, final String location,
			final String radius, final Date startDateTime,
			final Date endDateTime, final String sortOrder) {

		// Remove the time from Date objects to display in the Navlink
		String startDate = (startDateTime == null) ? null : startDateTime
				.toString().substring(0, 10);
		String endDate = (endDateTime == null) ? null : endDateTime.toString()
				.substring(0, 10);

		String searchQueryText = buildQueryString(keyword, location, radius,
				startDate, endDate, sortOrder);
		NavLink searchQuery = new NavLink(searchQueryText);
		searchQuery.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				presenter.onRecentNavLinkClicked(keyword, location, radius,
						startDateTime, endDateTime, sortOrder);
			}
		});

		recentSearches.add(searchQuery);

		// Remove first query if greater than 5 search queries cached
		if (recentSearches.getMenuWiget().getWidgetCount() > 5) {
			recentSearches.getMenuWiget().remove(0);
		}

		System.out.println("NUMBER OF SEARCHES: "
				+ recentSearches.getMenuWiget().getWidgetCount());

	}

	@Override
	public void toggleProgressBar(Boolean visible) {
		progressBar.setVisible(visible);
	}

	@Override
	public void toggleAdvancedPanel() {
		Boolean isVisible = isAdvCheckBoxChecked() ? true : false;
		advSearchPanel.setVisible(isVisible);
	}

	@Override
	public void triggerPopupPanel(HashMap<String, String> event) {
		PopUpBuilder builder = new PopUpBuilder(event, favoriteEventButtons);
		PopupPanel panel = builder.createPopUpPanel();
		resetButtonState(removeEventButton);
		resetButtonState(saveEventButton);
		favoriteEvent = event;
		panel.center();
		panel.show();
		zoomToEventLocation(event);
	}

	@Override
	public HashMap<String, String> getFavoriteEvent() {
		return favoriteEvent;
	}

	@Override
	public void setButtonStateLoading(Button button) {
		button.state().loading();
	}

	@Override
	public void setButtonStateComplete(Button button) {
		button.state().complete();
	}

	@Override
	public void resetButtonState(Button button) {
		button.state().reset();
	}

	@Override
	public Button getSaveButton() {
		return saveEventButton;
	}

	@Override
	public Button getRemoveButton() {
		return removeEventButton;
	}

	@Override
	public void setPresenter(MainPresenter presenter) {
		this.presenter = presenter;
	}

	private String buildQueryString(String keyword, String location,
			String radius, String startDate, String endDate, String sortOrder) {

		final String DEFAULT_QUERY = "DEFAULT SEARCH ";
		final String WHERE_LOCATION = " IN ";
		final String DATE_FROM = " FROM ";
		final String DATE_TO = " TO ";
		final String RADIUS_WITHIN = " WITHIN ";
		final String RADIUS_UNIT = "km";
		final String SORT_BY = " BY ";

		String returnKeyword = "ALL EVENTS";
		String returnLocation = "ANYWHERE";
		String returnStartDate = "ANYTIME";
		String returnEndDate = "ANYTIME";

		StringBuilder sb = new StringBuilder();
		if (keyword == "" && location == "") {
			sb.append(DEFAULT_QUERY);
		} else {
			if (keyword != "") {
				returnKeyword = keyword;
			}
			if (location != "") {
				returnLocation = location;
			}
			if (startDate != null) {
				returnStartDate = startDate;
			}
			if (endDate != null) {
				returnEndDate = endDate;
			}

			sb.append(returnKeyword + WHERE_LOCATION + returnLocation);
		}

		sb.append(DATE_FROM + returnStartDate + DATE_TO + returnEndDate);

		if (radius != "") {
			sb.append(RADIUS_WITHIN + radius + RADIUS_UNIT);
		}

		if (sortOrder != "") {
			sb.append(SORT_BY + sortOrder);
		}

		return sb.toString();
	}

	private ScrollPanel constructScrollPanel() {
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setWidth(SCROLL_WIDTH);
		scrollPanel.setHeight(SCROLL_HEIGHT);
		return scrollPanel;
	}

	private TextBox buildSearchTextBox(String placeholder) {
		TextBox textBox = new TextBox();
		textBox.setPlaceholder(placeholder);
		return textBox;
	}

	private CheckBox buildAdvSearchCheckBox(String advSearch) {
		CheckBox checkbox = new CheckBox(advSearch);
		return checkbox;
	}

	private void addAdvOptionsToPanel(HorizontalPanel advSearchPanel) {
		advSearchPanel.add(sortOrderBox);
		advSearchPanel.add(radiusListBox);
		advSearchPanel.add(startDateBox);
		advSearchPanel.add(endDateBox);
	}

	private Boolean isAdvCheckBoxChecked() {
		return advSearchCheckBox.getValue();
	}

	private DateBox buildDateBox(String placeholder) {
		DateBox dateBox = new DateBox();
		dateBox.setStyleName("listBox"); // TODO: make css class for dateBox
		dateBox.setWidth("auto");
		dateBox.setValue(null);
		dateBox.setPlaceholder(placeholder);
		return dateBox;
	}

	private FlowPanel constructMapPanel() {
		FlowPanel mapPanel = new FlowPanel();
		mapPanel.setHeight(MAP_HEIGHT);
		mapPanel.setWidth(MAP_WIDTH);
		mapPanel.setStyleDependentName("map", true);
		return mapPanel;
	}

	private void findGPSLocation() {
		System.out.println("into findGPSLocation");

		Geolocation geo = Geolocation.getIfSupported();
		if (geo == null) {
			Window.alert("Browser does not support GPS");
		}

		geo.getCurrentPosition(new Callback<Position, PositionError>() {

			@Override
			public void onSuccess(Position result) {
				placeUserMarker(eventMap, result.getCoordinates());
			}

			@Override
			public void onFailure(PositionError reason) {
				System.out.println("Searching for GPS location denied");
			}
		});
	}

	private MapWidget instantiateMap() {
		MapWidget map = new MapWidget(DEFAULT_LATLONG, DEFAULT_ZOOM_LEVEL);
		map.clearOverlays();
		map.setSize(MAP_WIDTH, MAP_HEIGHT);
		map.addControl(new SmallMapControl());

		return map;
	}

	private void placeMarkers(MapWidget map,
			List<HashMap<String, String>> events) {

		map.clearOverlays();
		for (HashMap<String, String> event : events) {
			double lat = NumberFormat.getDecimalFormat().parse(
					event.get(MainPresenter.EVENT_LATITUDE));
			double lon = NumberFormat.getDecimalFormat().parse(
					event.get(MainPresenter.EVENT_LONGITUDE));
			LatLng point = LatLng.newInstance(lat, lon);
			map.addOverlay(createMarkerAndInfoWindow(point, event));
			map.setCenter(point);
		}
		findGPSLocation();
	}

	private void placeUserMarker(MapWidget map, Coordinates coordinates) {
		double lat = coordinates.getLatitude();
		double lon = coordinates.getLongitude();

		LatLng latLng = LatLng.newInstance(lat, lon);
		System.out.println(latLng);

		Icon icon = Icon
				.newInstance("http://www.google.com/mapfiles/dd-start.png");
		MarkerOptions options = MarkerOptions.newInstance();
		options.setIcon(icon);

		final Marker marker = new Marker(latLng, options);
		map.addOverlay(marker);
		map.setCenter(latLng);
	}

	private Marker createMarkerAndInfoWindow(LatLng point,
			final HashMap<String, String> eventHash) {
		final Marker marker = new Marker(point);

		marker.addMarkerClickHandler(new MarkerClickHandler() {
			@Override
			public void onClick(MarkerClickEvent event) {
				InfoWindow infoWindow = eventMap.getInfoWindow();
				String htmlContent = "<strong>"
						+ eventHash.get(MainPresenter.EVENT_TITLE)
						+ "</strong>" + "</br>" + "<i>Venue: </i>"
						+ eventHash.get(MainPresenter.EVENT_VENUE_NAME);
				InfoWindowContent windowContent = new InfoWindowContent(
						htmlContent);
				infoWindow.open(marker, windowContent);
			}
		});
		return marker;
	}

	private void zoomToEventLocation(HashMap<String, String> event) {
		double lat = NumberFormat.getDecimalFormat().parse(
				event.get(MainPresenter.EVENT_LATITUDE));
		double lon = NumberFormat.getDecimalFormat().parse(
				event.get(MainPresenter.EVENT_LONGITUDE));
		LatLng point = LatLng.newInstance(lat, lon);
		eventMap.setCenter(point);
		eventMap.setZoomLevel(15);
	}
}
