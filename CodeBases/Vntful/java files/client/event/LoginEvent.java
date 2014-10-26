package com.lurn2kode.gwt.vntful.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.lurn2kode.gwt.vntful.shared.LoginInfo;

/*
 * Specific to logging into Vntful application
 */
public class LoginEvent extends GwtEvent<LoginEventHandler>{
	private LoginInfo loginInfo;
	
	public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
	

	public LoginEvent(LoginInfo loginInfo) {
		this.setLoginInfo(loginInfo);
	}

	@Override
	public Type<LoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoginEventHandler handler) {
		handler.onLogin(this);
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

}
