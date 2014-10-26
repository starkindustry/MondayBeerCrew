package com.lurn2kode.gwt.vntful.client;

import java.util.HashMap;

import com.github.gwtbootstrap.client.ui.ButtonGroup;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.Label;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.lurn2kode.gwt.vntful.client.presenter.MainPresenter;

/**
 * Construction of the event table pop panel/components
 */
public class PopUpBuilder {

	private static final String POP_UP_STYLE = "popUpStyle";
	private static final String POP_UP_HEIGHT = "500px";

	private HashMap<String, String> event;
	private ButtonGroup favoriteEventButtons;

	public PopUpBuilder(HashMap<String, String> event,
			ButtonGroup favoriteEventButtons) {
		this.event = event;
		this.favoriteEventButtons = favoriteEventButtons;
	}

	public PopupPanel createPopUpPanel() {
		PopupPanel panel = new PopupPanel(true);
		panel.setStyleName(POP_UP_STYLE);
		panel.setSize(POP_UP_HEIGHT, "500px");
		VerticalPanel contentPanel = constructContentPanel();
		panel.add(contentPanel);
		return panel;
	}

	private Label constructTitleLabel(String text) {
		Label title = new Label(text);
		title.setStyleDependentName("title", true);
		return title;
	}

	private HTML constructHTMLBlock(String content) {
		return new HTML(content);
	}

	private Anchor constructFBShareLink(String link) {
		final String BASE_SHARE_URL = "https://www.facebook.com/dialog/share?";
		final String FB_APP_KEY = "699178540176035";
		final String REDIRECT_URL = "http://1-dot-vntful310.appspot.com//#mainpage";

		String shareLink = BASE_SHARE_URL + //
				"app_id=" + //
				FB_APP_KEY + //
				"&display=page" + //
				"&href=" + //
				link + //
				"&redirect_uri=" + //
				REDIRECT_URL;
		Anchor url = new Anchor("Share on Facebook", shareLink);
		url.setStyleDependentName("url", true);
		return url;
	}

	private Anchor constructEventfulLink(String link) {
		Anchor url = new Anchor("View on Eventful.com", link);
		url.setStyleDependentName("url", true);
		return url;
	}

	private VerticalPanel constructContentPanel() {
		VerticalPanel contents = new VerticalPanel();
		contents.add(constructTitleLabel(event.get(MainPresenter.EVENT_TITLE)));
		contents.add(constructHTMLBlock(event
				.get(MainPresenter.EVENT_VENUE_NAME)));
		contents.add(constructEventfulLink(event.get(MainPresenter.EVENT_URL)));
		contents.add(new Image(event.get(MainPresenter.EVENT_IMAGE)));
		contents.add(constructHTMLBlock(event
				.get(MainPresenter.EVENT_START_TIME)
				+ "-"
				+ event.get(MainPresenter.EVENT_STOP_TIME)));
		contents.add(constructFBShareLink(event.get(MainPresenter.EVENT_URL)));

		contents.add(favoriteEventButtons);
		ScrollPanel descriptionPanel = new ScrollPanel();
		descriptionPanel.setSize(POP_UP_HEIGHT, "200px");
		descriptionPanel.add(constructHTMLBlock(event
				.get(MainPresenter.EVENT_DESCRIPTION)));
		contents.add(descriptionPanel);
		return contents;
	}
}
