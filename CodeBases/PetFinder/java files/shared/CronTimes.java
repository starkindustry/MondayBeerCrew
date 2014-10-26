package com.google.gwt.client.shared;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class CronTimes  implements Serializable{
	@PrimaryKey
    @Persistent
	private String key;
	private Date lastUpdate;
	
	public CronTimes(String s){
		key = s;
	}
	
	public void setTimeToNow(){
		lastUpdate = new Date();
	}
	
	public Date getLastUpdate(){
		return lastUpdate;
	}
	
}
