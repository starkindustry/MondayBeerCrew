package com.lurn2kode.gwt.vntful.client.presenter;

import java.util.HashMap;
import java.util.List;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.HasCellPreviewHandlers;
import com.lurn2kode.gwt.vntful.client.FavoritedEventServiceAsync;
import com.lurn2kode.gwt.vntful.client.view.FavoritesView;

public class FavoritesPresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		void populateTable(List<HashMap<String, String>> events);

		void triggerPopupPanel(HashMap<String, String> event);

		HasCellPreviewHandlers<HashMap<String, String>> getTableRowSelection();

		void setButtonStateLoading(Button button);

		void setButtonStateComplete(Button button);

		void resetButtonState(Button button);

		HasClickHandlers getRemoveEventButton();

		HashMap<String, String> getFavoriteEvent();

		Button getRemoveButton();
	}

	private final FavoritedEventServiceAsync favoriteService;
	private final HandlerManager eventBus;
	private final Display display;

	public FavoritesPresenter(FavoritedEventServiceAsync favoriteService,
			HandlerManager eventBus, FavoritesView favoritesView) {
		this.favoriteService = favoriteService;
		this.eventBus = eventBus;
		this.display = favoritesView;
	}

	@Override
	public void go(HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
		fetchEventResults();

	}

	private void bind() {
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

		display.getRemoveEventButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.setButtonStateLoading(display.getRemoveButton());
				removeFavoriteEvent(display.getFavoriteEvent().get("id"));
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

	private void fetchEventResults() {
		favoriteService
				.getEvents(new AsyncCallback<List<HashMap<String, String>>>() {

					@Override
					public void onSuccess(List<HashMap<String, String>> result) {
						display.populateTable(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Failed to get events");
					}
				});
	}

}
