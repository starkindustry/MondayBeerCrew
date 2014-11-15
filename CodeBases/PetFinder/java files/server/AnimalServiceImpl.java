package com.google.gwt.client.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.client.client.AnimalService;
import com.google.gwt.client.shared.Animal;
import com.google.gwt.client.shared.CronTimes;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class AnimalServiceImpl extends RemoteServiceServlet
implements AnimalService {
	private static final PersistenceManagerFactory PMF =
		      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	//This is run whenever Petfinder gets a request (such as from the Cron Job)
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			// The Cron Job sent the request, now do the update
			fetchUpdate();
			
	}
	
	@Override
	public void fetchDatabase() {
		PersistenceManager pm = getPersistenceManager();
		AnimalRegistry reg = AnimalRegistry.defaultInstance();
		reg.fetchUpdate();
		List<Animal> animals = reg.getAnimalList();
		
		pm.makePersistentAll(animals);
		pm.close();
	}
	
	@Override
	public void fetchUpdate() {
		PersistenceManager pm = getPersistenceManager();
		AnimalRegistry reg = AnimalRegistry.defaultInstance();
		// If you know the Key of the object, it is possible to fetch that single object with
		// getObjectById. I'm not sure what happens when you attempt it with a key tha doesn't exist,
		// you'll probably get Null back. Can be extremely useful when looking for Users in the
		// Datastore
		CronTimes ct = pm.detachCopy(pm.getObjectById(CronTimes.class, "Last Datastore Update"));
		reg.setTime(ct.getLastUpdate());
		reg.fetchUpdate();
		// Normally, when you update an object while PersistenceManager is open, it will update the
		// datastore automatically when you call pm.close. However, because we have to detach ct
		// since it has a Date field, we have to manually persist it again. Note: It is possible
		// to detach an Object, return it to, work on whatever called it (like browsepage), then 
		// send it back to be updated later. 
		ct.setTimeToNow();
		pm.makePersistentAll(ct);
		List<Animal> newAnimals = reg.getNewAnimals();
		
		pm.makePersistentAll(newAnimals);
		pm.close();
	}
	
	@Override
	public void addPet(Animal animal){
		PersistenceManager pm = getPersistenceManager();
		pm.makePersistent(animal);
		pm.close();
	}
	
	@Override
	public void removePet(Long key){
		PersistenceManager pm = getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(Animal.class, key));
		pm.close();
	}
	
	@Override
	public void removeListOfPets(List<Long> keys){
		PersistenceManager pm = getPersistenceManager();
		List<Animal> toBeRemoved = new ArrayList<Animal>();
		for (Long key: keys){
			toBeRemoved.add(pm.getObjectById(Animal.class, key));
		}
		pm.deletePersistentAll(toBeRemoved);
		pm.close();
	}
	
	@Override
	public void initializeTime(){
		PersistenceManager pm = getPersistenceManager();
		CronTimes time = new CronTimes("Last Datastore Update");
		time.setTimeToNow();
		pm.makePersistent(time);
		pm.close();
	}
	
	@Override
	public List<Animal> getAllAnimals() {
		PersistenceManager pm = getPersistenceManager();
		Query q = pm.newQuery(Animal.class);
		q.setOrdering("dateCreated desc");
		
		List<Animal> animals = (List<Animal>) pm.detachCopyAll((List<Animal>) q.execute());
		
		return animals;
	}
	
	@Override
	public List<Animal> getUsersAnimals(String user) {
		PersistenceManager pm = getPersistenceManager();
		Query q = pm.newQuery(Animal.class);
		q.setFilter("owner == ownerParam");
		q.setOrdering("dateCreated desc");
		q.declareParameters("String ownerParam");
		
		// Get all Animals made by user
		List<Animal> animals = (List<Animal>) pm.detachCopyAll((List<Animal>)q.execute(user));
		
		return animals;
	}
	
	@Override
	public List<Animal> searchTags(String s) {
		List<Animal> results = new ArrayList<Animal>();
		PersistenceManager pm = getPersistenceManager();
		
		Query q = pm.newQuery(Animal.class);
		
		q.setOrdering("dateCreated desc");
		
		
		try {
			  List<Animal> animals = (List<Animal>) pm.detachCopyAll((List<Animal>) q.execute());
			  System.out.println("number of total results returned: " + animals.size());
			  if (!animals.isEmpty()) {
			    for (Animal a : animals) {
			      if (a.getSearchTags().toLowerCase().contains(s.toLowerCase())){
			    	  results.add(a);
			      }
			    }
			  } else {
			    // Handle "no results" case
				  System.out.println("empty results");
			  }
			} finally {
			  q.closeAll();
			}
		
		return results;
	}

	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}



}
