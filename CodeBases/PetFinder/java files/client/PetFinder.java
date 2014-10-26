package com.google.gwt.client.client;

import com.google.gwt.client.shared.User;
import com.google.gwt.core.client.EntryPoint; 
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class PetFinder implements EntryPoint {
	private final AnimalServiceAsync animalService = GWT.create(AnimalService.class);
	private final UserServiceAsync userService = GWT.create(UserService.class);
/** * Entry point method. */
@Override
public void onModuleLoad() {
	DefaultPage page = new DefaultPage(); 
	
// CronJob fix: Turns out that it wasn't a good idea to store the time for updates inside a variable
// since those might get reset and the variables will be cleared, even if you don't redeploy. Instead,
// I decided to put the date of the lastUpdate into the datastore itself. Uncomment the following line
// of code and deploy it once, then comment it out for future deploys. The Cron Job for updating the 
// datastore will take care of updating it every time the Cron Job is run. 
// You do not have to worry about this for Running as Web App since Cron Jobs don't work when 
// you run as Web App. 

	User admin = new User();
	admin.setup("admin", "password");
	admin.makeAdmin();
	
	userService.registerUser(admin, new AsyncCallback<Void>(){

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			
		}
	});
	

	/*
	animalService.fetchDatabase(new AsyncCallback<Void>(){

		@Override
		public void onFailure(Throwable caught) {
			System.out.println("failed to fetch database");
			
		}

		@Override
		public void onSuccess(Void result) {
			System.out.println("fetched database");
			
		}
		
	});*/
	/*
		animalService.getAllAnimals(new AsyncCallback<List<Animal>>(){

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("failed");
				
			}

			@Override
			public void onSuccess(List<Animal> result) {
				System.out.println(result.size());
				DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");
				for (int i = 0; i < 10; i++){
					Animal animal = result.get(i);
					
					System.out.println("Name: " + animal.getName());
					System.out.println("Breed: " + animal.getBreed());
					System.out.println("Color: " + animal.getColor());
					System.out.println("Sex: " + animal.getSex());
					System.out.println("Date Created: " + dtf.format(animal.getDateCreated()));
					System.out.println("Date Lost: " + dtf.format(animal.getDateLost()));
					System.out.println("State: " + animal.getState());
					System.out.println();	
				}
				
			}
			
		});*/
	
	// Associate the Main panel with the HTML host page.
    RootPanel.get("petList").add(page);
}
}