package com.lurn2kode.gwt.vntful.client.presenter;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.lurn2kode.gwt.vntful.client.LoginServiceAsync;
import com.lurn2kode.gwt.vntful.client.event.LoginEvent;
import com.lurn2kode.gwt.vntful.client.view.LoginView;
import com.lurn2kode.gwt.vntful.shared.LoginInfo;

public class LoginPresenter implements Presenter{
	
	public interface Display{
		Widget asWidget();
		Button getGmailButton();
		Button getLoginButton();
		void setButtonLoadingState();
	}
	
	private final LoginServiceAsync loginService;
	private final HandlerManager eventBus;
	private final Display display;
	
	private LoginInfo loginInfo;

	public LoginPresenter(LoginServiceAsync loginService,
			LoginInfo loginInfo, HandlerManager eventBus, LoginView loginView) {
		this.loginService = loginService;
		this.eventBus = eventBus;
		this.loginInfo = loginInfo;
		this.display = loginView;
	}

	@Override
	public void go(HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
		checkLoggedIn(loginInfo);
	}

	private void bind() {
		display.getLoginButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				display.setButtonLoadingState();
				
				if(loginInfo.isLoggedIn()) {
					eventBus.fireEvent(new LoginEvent(loginInfo));
				}
			}
		});
		
		display.getGmailButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String loginURL = loginInfo.getLoginUrl();
				Window.Location.assign(loginURL);
			}
		});
	}
	
	private void checkLoggedIn(LoginInfo loginInfo) {
		if (loginInfo.isLoggedIn()){
			display.getLoginButton().setEnabled(true);
		}
		else {
			display.getGmailButton().setVisible(true);
		}	
	}
	

}
