package com.lurn2kode.gwt.vntful.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.lurn2kode.gwt.vntful.shared.LoginInfo;

/*
 * Taken from Stockwatcher Tutorial
 */
@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
  public LoginInfo login(String requestUri);
}