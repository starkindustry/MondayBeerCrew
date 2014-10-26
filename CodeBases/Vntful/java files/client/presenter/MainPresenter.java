package com.lurn2kode.gwt.vntful.client.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Dropdown;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.datepicker.client.ui.DateBox;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.HasCellPreviewHandlers;
import com.lurn2kode.gwt.vntful.client.EventModelServiceAsync;
import com.lurn2kode.gwt.vntful.client.FavoritedEventServiceAsync;
import com.lurn2kode.gwt.vntful.client.event.FavoriteEvent;
import com.lurn2kode.gwt.vntful.client.event.SearchEvent;
import com.lurn2kode.gwt.vntful.shared.EventModel;
import com.lurn2kode.gwt.vntful.shared.Image;
import com.lurn2kode.gwt.vntful.shared.LoginInfo;

public class MainPresenter implements Presenter {

	public static final String EVENT_VENUE_NAME = "venue_name";
	public static final String EVENT_CITY_NAME = "city_name";
	public static final String EVENT_TITLE = "title";
	public static final String EVENT_START_TIME = "start_time";
	public static final String EVENT_STOP_TIME = "stop_time";
	public static final String EVENT_LATITUDE = "latitude";
	public static final String EVENT_LONGITUDE = "longitude";
	public static final String EVENT_URL = "url";
	public static final String EVENT_DESCRIPTION = "description";
	public static final String EVENT_IMAGE = "image";
	public static final String EVENT_ID = "id";

	private final EventModelServiceAsync rpcService;
	private final FavoritedEventServiceAsync favoriteService;
	private final LoginInfo loginInfo;
	private final HandlerManager eventBus;
	private final Display display;
	private List<EventModel> eventModels;


	public interface Display {
		HasClickHandlers getSearchButton();

		String getKeyword();

		String getLocation();

		String getRadius();

		void setEvents(List<HashMap<String, String>> events);

		HasClickHandlers getAdvSearchCheckBox();

		String getSortOrder();

		Widget asWidget();

		void toggleProgressBar(Boolean visible);

		void setAlert(AlertType type, String message);

		void triggerPopupPanel(HashMap<String, String> events);

		void toggleAdvancedPanel();

		DateBox getEndDateBox();

		DateBox getStartDateBox();

		HasCellPreviewHandlers<HashMap<String, String>> getTableRowSelection();

		void toggleAlert(boolean visible);

		HasKeyUpHandlers getKeywordBox();

		HasKeyUpHandlers getLocationBox();

		HashMap<String, String> getFavoriteEvent();
		
		void setRecentSearch(String keyword, String location, String radius, Date startDate, Date endDate, String sortOrder);

		void setButtonStateLoading(Button button);

		void setButtonStateComplete(Button button);

		void setPresenter(MainPresenter presenter);

		void resetButtonState(Button button);

		HasClickHandlers getSaveEventButtonHandler();

		HasClickHandlers getRemoveEventButtonHandler();

		Button getSaveButton();

		Button getRemoveButton();

		Dropdown getDropdown();

		HasClickHandlers getFavoritePageLink();

		HasClickHandlers getLogoutLink();
	}

	public MainPresenter(FavoritedEventServiceAsync favoriteService,
			EventModelServiceAsync rpcService, LoginInfo loginInfo,
			HandlerManager eventBus, Display view) {
		this.favoriteService = favoriteService;
		this.rpcService = rpcService;
		this.loginInfo = loginInfo;
		this.eventBus = eventBus;
		this.display = view;
		this.display.setPresenter(this);
	}

	public void bind() {
		display.getSearchButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// First, fire the event with the search queries in case other
				// parts of the application needs it.
				// Second, call the RPC Service to retrieve the search results.
				// Third, set recent search into the dropdown menu

				sendSearchRequest();
			}

		});

		display.getAdvSearchCheckBox().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.toggleAdvancedPanel();
			}

		});

		display.getStartDateBox().addValueChangeHandler(
				new ValueChangeHandler<Date>() {

					@Override
					public void onValueChange(ValueChangeEvent<Date> event) {
						display.getEndDateBox().setStartDate_(event.getValue());
						display.getEndDateBox().reconfigure();
					}

				});

		display.getEndDateBox().addValueChangeHandler(
				new ValueChangeHandler<Date>() {

					@Override
					public void onValueChange(ValueChangeEvent<Date> event) {
						display.getStartDateBox().setEndDate_(event.getValue());
						display.getStartDateBox().reconfigure();
					}

				});

		display.getTableRowSelection().addCellPreviewHandler(
				new CellPreviewEvent.Handler<HashMap<String, String>>() {

					@Override
					public void onCellPreview(
							CellPreviewEvent<HashMap<String, String>> event) {
						if (BrowserEvents.CLICK.equals(event.getNativeEvent()
								.getType())) {
							display.triggerPopupPanel(event.getValue());
						}
					}
				});

		display.getKeywordBox().addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendSearchRequest();
				}
			}
		});

		display.getLocationBox().addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendSearchRequest();
				}
			}
		});

		display.getSaveEventButtonHandler().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent clickEvent) {
				display.setButtonStateLoading(display.getSaveButton());
				HashMap<String, String> event = display.getFavoriteEvent();
				addFavoriteEvent(event);
			}
		});

		display.getRemoveEventButtonHandler().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						display.setButtonStateLoading(display.getRemoveButton());
						removeFavoriteEvent(display.getFavoriteEvent().get(
								EVENT_ID));
					}
				});

		display.getFavoritePageLink().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new FavoriteEvent());

			}
		});
		
		display.getLogoutLink().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				onLogout();	
			}
		});
	}
	public void onRecentNavLinkClicked(String keyword, String location, String radius, Date startDate, 
			Date endDate, String sortOrder) {
		String dateRange = formatDateRange(startDate, endDate);
		display.toggleProgressBar(true);
		fetchSearchResults(keyword, location, radius, dateRange, sortOrder);
	}

	@Override
	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
	}

	private void addFavoriteEvent(HashMap<String, String> event) {
		favoriteService.addEvent(event, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Save failed");
			}

			@Override
			public void onSuccess(Void result) {
				display.setButtonStateComplete(display.getSaveButton());
				display.getSaveButton().setEnabled(false);

			}
		});
	}

	private void removeFavoriteEvent(String eventId) {
		favoriteService.removeEvent(eventId, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Remove failed");
			}

			@Override
			public void onSuccess(Void result) {
				display.setButtonStateComplete(display.getRemoveButton());
			}
		});
	}
	
	private void onLogout() {
		String logoutURL = loginInfo.getLogoutUrl();
		Window.Location.assign(logoutURL);
	}

	private void fetchSearchResults(String keyword, String location,
			String radius, String dateRange, String sortOrder) {
		rpcService.getEventModelDetails(keyword, location, radius, dateRange,
				sortOrder, new AsyncCallback<List<EventModel>>() {
					@Override
					public void onSuccess(List<EventModel> result) {
						eventModels = result;
						loadSearchResults();
					}

					@Override
					public void onFailure(Throwable caught) {
						display.toggleProgressBar(false);
						display.setAlert(AlertType.ERROR,
								"Failed to retrieve search results.");
						display.toggleAlert(true);
					}
				});
	}

	private void sendSearchRequest() {
		display.toggleProgressBar(true);
		display.toggleAlert(false);
		
		Date startDateTime = display.getStartDateBox().getValue();
		Date endDateTime = display.getEndDateBox().getValue();
		
		String keyword = display.getKeyword();
		String location = display.getLocation();
		String radius = display.getRadius();
		String dateRange = formatDateRange(startDateTime, endDateTime); // Format Date objects to be compatible with Eventful
		String sortOrder = display.getSortOrder();
		eventBus.fireEvent(new SearchEvent(keyword, location, radius,
				dateRange, sortOrder));
		fetchSearchResults(keyword, location, radius, dateRange, sortOrder);
		
		display.setRecentSearch(keyword, location, radius, startDateTime, 
				endDateTime, sortOrder);
	}

	private void loadSearchResults() {
		List<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < eventModels.size(); i++) {
			EventModel event = eventModels.get(i);
			HashMap<String, String> eventHash = new HashMap<String, String>();
			eventHash.put(EVENT_ID, event.getId());
			eventHash.put(EVENT_START_TIME,
					transformDateString(event.getStartDateTime()));
			eventHash.put(EVENT_STOP_TIME,
					transformDateString(event.getStopDateTime()));
			eventHash.put(EVENT_TITLE, event.getTitle());
			eventHash.put(EVENT_CITY_NAME, event.getCity());
			eventHash.put(EVENT_VENUE_NAME, event.getVenueName());
			eventHash.put(EVENT_URL, event.getUrl());
			eventHash.put(EVENT_DESCRIPTION,
					filterDescriptionText(event.getDescription()));
			eventHash.put(EVENT_LATITUDE, event.getLatitude());
			eventHash.put(EVENT_LONGITUDE, event.getLongitude());
			eventHash.put(EVENT_IMAGE, getImageUrl(event.getImage()));
			eventList.add(eventHash);
		}
		display.toggleProgressBar(false);
		display.setEvents(eventList);
	}

	private String getImageUrl(Image image) {
		return image != null ? image.getMedium().getUrl() : "";
	}

	private String formatDateRange(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return "";
		} else {
			return DateTimeFormat.getFormat("yyyyMMdd").format(startDate)
					+ "00-"
					+ DateTimeFormat.getFormat("yyyyMMdd").format(endDate)
					+ "00";
		}
	}

	private String transformDateString(String date) {
		if (date == null) {
			return "n/a";
		}
		DateTimeFormat originalDateFormat = DateTimeFormat
				.getFormat("yyyy-MM-dd HH:mm:ss");
		Date dateObject = originalDateFormat.parse(date);
		DateTimeFormat newDateFormat = DateTimeFormat
				.getFormat("MMM d yyyy h:mm a");
		return newDateFormat.format(dateObject);
	}

	// filter out strange characters returned in the JSON response
	private String filterDescriptionText(String text) {
		return text == null ? null : text.replaceAll("[^\\x00-\\x7F]", "");
	}
}
