package com.google.gwt.client.shared;
import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Animal implements Serializable {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long key;
	@Persistent
	private Date dateLost;
	@Persistent
	private String color;
	@Persistent
	private String breed;
	@Persistent
	private String sex;
	@Persistent
	private String state;
	@Persistent
	private String name;
	@Persistent
	private Date dateCreated;
	@Persistent
	private String searchTags;
	@Persistent
	// The name of the user that uploaded the pet
	private String owner;
	@Persistent
	private String address;
	
	
	public Animal(){;
	}
	
	
	public long getKey() { return key; }
	
	
	public void setDateLost(String dateLost) { 
		int year = Integer.parseInt(dateLost.substring(0,4)) - 1900;
		int month = Integer.parseInt(dateLost.substring(5,7)) - 1;
		int date = Integer.parseInt(dateLost.substring(9));
		Date time = new Date();
		time.setYear(year);
		time.setMonth(month);
		time.setDate(date);
		this.dateLost = time;
		}
	
	public Date getDateLost(){
		return dateLost;
	}
	
	
	public void setColor(String color) { this.color = color; }
	public String getColor() { return color; }
	
		
	public void setBreed(String breed) { this.breed = breed; }
	public String getBreed() { return breed; }
	
	
	public void setSex(String sex) { this.sex = sex; }
	public String getSex() { return sex; }
	
	
	public void setState(String state) { this.state = state; }
	public String getState() { return state; }
	
	
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	
	public void setDateCreated(String dateCreated) {
		int year = Integer.parseInt(dateCreated.substring(0,4)) - 1900;
		int month = Integer.parseInt(dateCreated.substring(5,7)) - 1;
		int date = Integer.parseInt(dateCreated.substring(9));
		Date time = new Date();
		time.setYear(year);
		time.setMonth(month);
		time.setDate(date);
		this.dateCreated = time;
		}
	
	
	public void setSearchTags(String searchTags) {
		this.searchTags = searchTags;
	}
	
	public String getSearchTags() {
		return searchTags;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setOwner(String owner){
		this.owner = owner;
	}
	
	public String getOwner (){
		return owner;
	}

	public void setAddress(String address){
		this.address = address;
	}
	
	public String getAddress(){
		return address;
	}

}
