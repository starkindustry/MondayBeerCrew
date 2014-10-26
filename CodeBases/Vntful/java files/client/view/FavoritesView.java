package com.lurn2kode.gwt.vntful.client.view;

import java.util.HashMap;
import java.util.List;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ButtonGroup;
import com.github.gwtbootstrap.client.ui.CellTable;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.HasCellPreviewHandlers;
import com.lurn2kode.gwt.vntful.client.ButtonFactory;
import com.lurn2kode.gwt.vntful.client.PopUpBuilder;
import com.lurn2kode.gwt.vntful.client.TableFactory;
import com.lurn2kode.gwt.vntful.client.presenter.FavoritesPresenter;

public class FavoritesView extends Composite implements
		FavoritesPresenter.Display {

	private CellTable<HashMap<String, String>> favoritesTable;
	private HashMap<String, String> favoriteEvent;
	private ScrollPanel scrollPanel;
	private Button removeEventButton;

	public FavoritesView() {
		VerticalPanel mainPanel = new VerticalPanel();
		initWidget(mainPanel);
		HTML header = new HTML("Favorites");
		header.setStyleName("header");
		mainPanel.add(header);
		mainPanel.setStyleName("searchPanel", true);
		favoritesTable = TableFactory.createTable();
		removeEventButton = ButtonFactory.createRemoveEventButton();
		scrollPanel = new ScrollPanel();
		scrollPanel.add(favoritesTable);
		scrollPanel.setVisible(false);
		mainPanel.add(scrollPanel);
	}

	@Override
	public HasCellPreviewHandlers<HashMap<String, String>> getTableRowSelection() {
		return favoritesTable;
	}

	@Override
	public void populateTable(List<HashMap<String, String>> events) {
		scrollPanel.setVisible(true);
		favoritesTable.setRowData(events);
	}

	@Override
	public void triggerPopupPanel(HashMap<String, String> event) {
		ButtonGroup favoriteEventButtons = new ButtonGroup(removeEventButton);
		PopUpBuilder builder = new PopUpBuilder(event, favoriteEventButtons);
		PopupPanel panel = builder.createPopUpPanel();
		resetButtonState(removeEventButton);
		favoriteEvent = event;

		panel.center();
		panel.show();
	}

	@Override
	public HashMap<String, String> getFavoriteEvent() {
		return favoriteEvent;
	}

	@Override
	public Button getRemoveButton() {
		return removeEventButton;
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
	public HasClickHandlers getRemoveEventButton() {
		return removeEventButton;
	}
}
