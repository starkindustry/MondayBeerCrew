package com.lurn2kode.gwt.vntful.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.lurn2kode.gwt.vntful.shared.LoginInfo;

public class Vntful implements EntryPoint {
	private LoginInfo loginInfo;
	
	@Override
	public void onModuleLoad() {
		final EventModelServiceAsync rpcService = GWT.create(EventModelService.class);
		final LoginServiceAsync loginService = GWT.create(LoginService.class);
		final FavoritedEventServiceAsync favoriteService = GWT
				.create(FavoritedEventService.class);
		
		loginInfo = new LoginInfo();
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>(){

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				HandlerManager eventBus = new HandlerManager(null);
				AppController appViewer = new AppController(favoriteService,
						loginService, rpcService, loginInfo, eventBus);
				appViewer.go(RootPanel.get());
			}
			
		});
		
	}
}
