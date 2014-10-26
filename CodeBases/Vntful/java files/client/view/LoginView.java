package com.lurn2kode.gwt.vntful.client.view;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.HelpBlock;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.lurn2kode.gwt.vntful.client.ButtonFactory;
import com.lurn2kode.gwt.vntful.client.presenter.LoginPresenter;

public class LoginView extends Composite implements LoginPresenter.Display {
	private final Button loginButton;
	private final Button gmailLoginButton;

	public LoginView() {
		VerticalPanel loginPanel = new VerticalPanel();
		initWidget(loginPanel);
		loginPanel.setStyleName("loginPanel");

		// The login Button
		loginButton = ButtonFactory.createEnterEventfulButton();
		loginButton.setEnabled(false);
		
		// Gmail Login Button
		gmailLoginButton = ButtonFactory.createGoogleLoginButton();
		gmailLoginButton.setVisible(false);
	
		// Instruction text under button
		HelpBlock helpText = new HelpBlock();
		helpText.setText("*You must be signed into your Google Account to use Eventful");

		loginPanel.add(loginButton);
		loginPanel.add(helpText);
		loginPanel.add(gmailLoginButton);
	}

	@Override
	public Button getLoginButton() {
		return loginButton;
	}
	
	@Override
	public Button getGmailButton() {
		return gmailLoginButton;
	}

	@Override
	public void setButtonLoadingState() {
		loginButton.setEnabled(false);
		loginButton.state().loading();
	}

	@Override
	public Widget asWidget() {
		return this;
	}

}
