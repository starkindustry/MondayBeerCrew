package com.lurn2kode.gwt.vntful.client;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.resources.ButtonSize;

public class ButtonFactory {

	private static final String SAVE_BUTTON_LABEL = "Save to Favourites";
	private static final String SEARCH_BUTTON_STYLE = "searchButton";
	private static final String SEARCH_BUTTON_LABEL = "Search";
	private static final String ENTER_EVENTFUL_LABEL = "Enter Eventful";
	private static final String GOOGLE_LOGIN_LABEL = "Login to Google";

	public static Button createSearchButton() {
		Button button = new Button(SEARCH_BUTTON_LABEL);
		button.addStyle(ButtonType.INVERSE);
		button.addStyleDependentName(SEARCH_BUTTON_STYLE);
		button.setIcon(IconType.SEARCH);
		return button;
	}

	public static Button createSaveEventButton() {
		Button button = new Button(SAVE_BUTTON_LABEL);
		button.setIcon(IconType.BOOKMARK);
		button.addStyle(ButtonSize.SMALL);
		button.addStyle(ButtonType.INVERSE);
		button.addStyleDependentName("saveButton");
		button.setLoadingText("Saving...");
		button.setCompleteText("Saved!");
		return button;
	}

	public static Button createRemoveEventButton() {
		Button button = new Button("Remove from Favourites");
		button.setIcon(IconType.REMOVE);
		button.addStyle(ButtonSize.SMALL);
		button.addStyle(ButtonType.INVERSE);
		button.addStyleDependentName("removeButton");
		button.setLoadingText("Removing...");
		button.setCompleteText("Removed!");
		return button;
	}
	
	public static Button createEnterEventfulButton() {
		Button button = new Button(ENTER_EVENTFUL_LABEL, IconType.POWER_OFF);
		button.addStyle(ButtonType.INVERSE);
		button.addStyleDependentName("loginButton");
		button.setBlock(true);
		button.setLoadingText("Loading...");
		return button;
	}
	
	public static Button createGoogleLoginButton() {
		Button button = new Button(GOOGLE_LOGIN_LABEL, IconType.GOOGLE_PLUS);
		button.addStyle(ButtonType.DANGER);
		button.addStyleDependentName("gmailButton");
		button.setBlock(true);
		return button;
	}
}
