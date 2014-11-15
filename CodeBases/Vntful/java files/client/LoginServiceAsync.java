package com.lurn2kode.gwt.vntful.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.lurn2kode.gwt.vntful.shared.LoginInfo;

/*
 * Taken from Stockwatcher Tutorial
 */
public interface LoginServiceAsync {

	void login(String requestUri, AsyncCallback<LoginInfo> callback);

}
