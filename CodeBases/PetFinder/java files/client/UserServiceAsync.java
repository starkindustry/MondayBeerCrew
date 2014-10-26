package com.google.gwt.client.client;

import com.google.gwt.client.shared.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {
	
	void registerUser(User user, AsyncCallback<Void> callback);
	void getUser(String username, AsyncCallback<User> callback);

}
