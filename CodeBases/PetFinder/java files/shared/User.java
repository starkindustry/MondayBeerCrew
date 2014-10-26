package com.google.gwt.client.shared;

import java.io.Serializable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class User implements Serializable{
	@PrimaryKey
	@Persistent
	// Same as username, but is case insensitive
	private String key;
    @Persistent
	private String username;
	@Persistent
	private String password;
	// A list of keys for the animals created by the user
	private boolean isAdmin;
	private boolean isOwner;
	
	public User(){
		isAdmin = false;
	}
	
	public void setup(String username, String password){
		key = username.toLowerCase();
		this.username = username;
		this.password = password;
	}
	
	public String getKey(){
		return key;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void makeAdmin(){
		isAdmin = true;
	}
	
	public void removeAdmin(){
		isAdmin = false;
	}
	
	public void makeOwner(){
		isAdmin = true;
		isOwner = true;
	}
	
	public boolean isAdmin(){
		return isAdmin;
	}
	
	public boolean isOwner(){
		return isOwner;
	}
}
