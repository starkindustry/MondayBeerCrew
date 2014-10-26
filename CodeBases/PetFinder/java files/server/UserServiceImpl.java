package com.google.gwt.client.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import com.google.gwt.client.client.UserService;
import com.google.gwt.client.shared.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServiceImpl extends RemoteServiceServlet
implements UserService{
	private static final PersistenceManagerFactory PMF =
		      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	
	@Override
	public void registerUser(User user) {
		PersistenceManager pm = getPersistenceManager();
		pm.makePersistent(user);
		pm.close();
		
	}


	// Gets the user specified by the key if it exists, returns null if it doesn't otherwise
	@Override
	public User getUser(String username) {
		PersistenceManager pm = getPersistenceManager();
		String key = username.toLowerCase();
		User result;
		
		try {
			result = pm.detachCopy(pm.getObjectById(User.class, key));
		}
		catch (javax.jdo.JDOObjectNotFoundException notFound){
			// Set result to null if not found
			result = null;
		}
		finally {
			pm.close();
		}
		
		return result;
	}
	
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}

}
