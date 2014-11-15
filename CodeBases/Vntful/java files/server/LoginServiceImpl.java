package com.lurn2kode.gwt.vntful.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.lurn2kode.gwt.vntful.client.LoginService;
import com.lurn2kode.gwt.vntful.shared.LoginInfo;
/*
 * Modified from Stockwatcher Tutorial
 */
public class LoginServiceImpl extends RemoteServiceServlet implements
    LoginService {
	
	private static final String DEV_MODE_SUFFIX = "Vntful.html?gwt.codesvr=127.0.0.1:9997";

  public LoginInfo login(String requestUri) {
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    String loginURL = createURLByMode(requestUri);
    LoginInfo loginInfo = new LoginInfo();

    if (user != null) {
      loginInfo.setLoggedIn(true);
      loginInfo.setEmailAddress(user.getEmail());
      loginInfo.setNickname(user.getNickname());
      loginInfo.setLogoutUrl(userService.createLogoutURL(loginURL));
    } else {
      loginInfo.setLoggedIn(false);
      loginInfo.setLoginUrl(userService.createLoginURL(loginURL));  
    
    }
    return loginInfo;
  }
  
//If in production mode, createLoginURL normally, else append dev mode ending
  private String createURLByMode(String baseUri) {
	  String modifiedUri = baseUri;
	  
	  if(this.getThreadLocalRequest().getHeader("Referer").contains("127.0.0.1") ) {
		  modifiedUri = modifiedUri + DEV_MODE_SUFFIX;
	  }
	  
	  return modifiedUri;
  }

}