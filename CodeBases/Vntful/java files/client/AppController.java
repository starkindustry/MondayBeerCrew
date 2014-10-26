package com.lurn2kode.gwt.vntful.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.lurn2kode.gwt.vntful.client.event.FavoriteEvent;
import com.lurn2kode.gwt.vntful.client.event.FavoriteEventHandler;
import com.lurn2kode.gwt.vntful.client.event.LoginEvent;
import com.lurn2kode.gwt.vntful.client.event.LoginEventHandler;
import com.lurn2kode.gwt.vntful.client.event.SearchEvent;
import com.lurn2kode.gwt.vntful.client.event.SearchEventHandler;
import com.lurn2kode.gwt.vntful.client.presenter.FavoritesPresenter;
import com.lurn2kode.gwt.vntful.client.presenter.LoginPresenter;
import com.lurn2kode.gwt.vntful.client.presenter.MainPresenter;
import com.lurn2kode.gwt.vntful.client.presenter.Presenter;
import com.lurn2kode.gwt.vntful.client.view.FavoritesView;
import com.lurn2kode.gwt.vntful.client.view.LoginView;
import com.lurn2kode.gwt.vntful.client.view.MainView;
import com.lurn2kode.gwt.vntful.shared.LoginInfo;

public class AppController implements Presenter, ValueChangeHandler<String> {

	private final HandlerManager eventBus;
	private final LoginServiceAsync loginService;
	private final EventModelServiceAsync rpcService;
	private final FavoritedEventServiceAsync favoriteService;
	private HasWidgets container;
	private LoginInfo loginInfo;

	public AppController(FavoritedEventServiceAsync favoriteService,
			LoginServiceAsync loginService, EventModelServiceAsync rpcService,
			LoginInfo loginInfo, HandlerManager eventBus) {
		this.favoriteService = favoriteService;
		this.loginService = loginService;
		this.rpcService = rpcService;
		this.loginInfo = loginInfo;
		this.eventBus = eventBus;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler() {
			@Override
			public void onLogin(LoginEvent event) {
				// System.out.println("Logged in and moving to MainView");
				afterLogin(event.getLoginInfo());
			}
		});

		eventBus.addHandler(SearchEvent.TYPE, new SearchEventHandler() {
			@Override
			public void onSearch(SearchEvent event) {
				// TODO: add the doSearch... method call
			}
		});

		eventBus.addHandler(FavoriteEvent.TYPE, new FavoriteEventHandler() {
			@Override
			public void onClickFavorites(FavoriteEvent event) {
				goToFavorites();
			}
		});
	}

	private void afterLogin(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
		History.newItem("mainpage");
	}

	private void goToFavorites() {
		History.newItem("favorites");
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {
			Presenter presenter = null;

			if (token.equals("login")) {
				presenter = new LoginPresenter(loginService, loginInfo, 
						eventBus, new LoginView());
			} else if (token.equals("mainpage")) {				
				presenter = new MainPresenter(favoriteService, rpcService,
						loginInfo, eventBus, new MainView());
			} else if (token.equals("favorites")) {
				presenter = new FavoritesPresenter(favoriteService, eventBus,
						new FavoritesView());
			} else  {
				presenter = new MainPresenter(favoriteService, rpcService,
						loginInfo, eventBus, new MainView());
			}

			if (presenter != null) {
				presenter.go(container);
			}
		} 
		
	}

	@Override
	public void go(final HasWidgets container) {
		this.container = container;

		if ("".equals(History.getToken())) {
			History.newItem("login");
		} else {
			History.fireCurrentHistoryState();
		}
	}
}
