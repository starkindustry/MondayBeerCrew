package com.google.gwt.client.client;

import java.util.List;

import com.google.gwt.client.shared.Animal;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("animal")
public interface AnimalService extends RemoteService {
	
	  public void fetchDatabase();

	  public void fetchUpdate();
	  
	  public void initializeTime();
	  
	  public List<Animal> getAllAnimals();
	 
	  public void addPet(Animal animal);
	  
	  public void removePet(Long key);
	  
	  public void removeListOfPets(List<Long> keys);
	  
	// Get animals written by a specific user
	  public List<Animal> getUsersAnimals(String user);
	  
//	  public List<Animal> searchString(String s);
	  
	  public List<Animal> searchTags(String s);
}
