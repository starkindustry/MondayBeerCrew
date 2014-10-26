package com.google.gwt.client.client;

import com.google.gwt.client.shared.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService{
	
	public void registerUser(User user);
	public User getUser(String username);

}
