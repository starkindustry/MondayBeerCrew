package com.google.gwt.client.client;


import java.util.List;

import com.google.gwt.client.shared.Animal;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AnimalServiceAsync {
	
	void fetchDatabase(AsyncCallback<Void> callback);
	
	void initializeTime(AsyncCallback<Void> callback);
	
	void fetchUpdate(AsyncCallback<Void> callback);
	
	void addPet(Animal animal, AsyncCallback<Void> callback);
	
	void removePet(Long key, AsyncCallback<Void> callback);
	
	void removeListOfPets(List<Long> keys, AsyncCallback<Void> callback);
	
	void getAllAnimals(AsyncCallback<List<Animal>> callback);
	
	// Get animals written by a specific user
	void getUsersAnimals(String user, AsyncCallback<List<Animal>> callback);
	
//	void searchString(String s, AsyncCallback<List<Animal>> callback);
	
	void searchTags(String s, AsyncCallback<List<Animal>> callback);
}
