package com.google.gwt.client.client;

import java.util.List;

import com.google.gwt.client.shared.Animal;
import com.google.gwt.client.shared.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

// This is where the user sees their own pets and adds pets/edits their existing pets
public class UserControlPanel extends Composite{
	private final AnimalServiceAsync animalService = GWT.create(AnimalService.class);
	private HorizontalPanel hPanel = new HorizontalPanel(); 
	private VerticalPanel vPanel = new VerticalPanel();
	private Button addPetButton = new Button("Add Pet");
	private Button defaultButton = new Button("Back To Main Page");
	private FlexTable tbl = new FlexTable();
	private User user;
	
	public UserControlPanel(final User user) {
		this.user = user;
		initWidget(this.hPanel);
		hPanel.add(vPanel);
		hPanel.add(defaultButton);
		vPanel.add(addPetButton);
		 
		defaultButton.addStyleName("defaultAlignment");
		// on click default button
		defaultButton.addClickHandler(new ClickHandler() {
		      @Override
			public void onClick(ClickEvent event) {
		        defaultPage();
		      }
		    });
		
		addPetButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addPetPage();
			}
		});
		
	
		
		vPanel.add(tbl); 
		
		animalService.getUsersAnimals(user.getUsername(), new AsyncCallback<List<Animal>>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("No Listings Available");

			}

			@Override
			public void onSuccess(List<Animal> result) {
				if (result.isEmpty()) {
					tbl.setText(0, 0, "You have not added pets");
				}
				
				else 
				{
				tbl.setText(0, 0, "Name");  
				tbl.setText(0, 1, "Breed");  
				tbl.setText(0, 2, "Color");  
				tbl.setText(0, 3, "Sex");
				tbl.setText(0, 4, "Date Created");
				tbl.setText(0, 5, "Date Lost");
				tbl.setText(0, 6, "State");
			    // Add styles to elements in the stock list table.
			    tbl.getRowFormatter().addStyleName(0, "tblHeader");
				
				DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");
		
				int row = tbl.getRowCount();
				
				for (final Animal animal : result){
					tbl.setText(row, 0, animal.getName());  
					tbl.setText(row, 1, animal.getBreed());  
					tbl.setText(row, 2, animal.getColor());  
					tbl.setText(row, 3, animal.getSex());
					tbl.setText(row, 4, dtf.format(animal.getDateCreated()));
					if (animal.getDateLost() == null)
						tbl.setText(row, 5, "Unknown");
					else 
						tbl.setText(row, 5, dtf.format(animal.getDateLost()));
					tbl.setText(row, 6, animal.getState());
					// Create buttons within the loop so each button can be connected to its specific
					// animal
					Button markAsMatched = new Button ("Mark as matched");
					Button remove = new Button ("Remove");
					Button mapButton = new Button ("Map");
					tbl.setText(row, 7, animal.getAddress());
					tbl.setWidget(row, 8, markAsMatched);
					tbl.setWidget(row, 9, remove);
					tbl.setWidget(row, 10, mapButton);
		
					tbl.getCellFormatter().addStyleName(row,1, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,2, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,3, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,4, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,5, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,6, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,7, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,8, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,9, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,10, "FlexTable-Cell");
					
					markAsMatched.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event){
							markPetAsMatched(animal);
						}
					});
					
					remove.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event){
							removePet(animal.getKey());
						}
					});
					
					mapButton.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event){
							mappingPage(user, animal);
						}
					});
					
					row++;
					
				}
				tbl.addStyleName("tblList");
			}
			}

		});
		
	}
	
	private void markPetAsMatched(Animal animal){
		// Because the ID of the animal already exists in the datastore, using addPet will overwrite
		// the old entry
		animal.setState("Matched");
		animalService.addPet(animal, new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error, unable to connect to the server");
				
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Successfully connected to server, please wait a few seconds and re-enter this page to see your changes.");
				
			}
			
		});
	}
	
	private void removePet(Long key){
		animalService.removePet(key, new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error, unable to connect to the server");
				
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Successfully connected to server, please wait a few seconds and re-enter this page to see your changes.");
				
			}
			
		});
	}
	
	// takes you back to default page
	private void defaultPage() {
			// You should be logged in if you reach this page, so no need to check that the user
			// isn't NLI
			DefaultLoggedInPage defaultPage = new DefaultLoggedInPage(user);
			hPanel.clear();
			hPanel.add(defaultPage);
		}
	
	// takes you back to mapping page
	private void mappingPage(User user, Animal animal) {
			// You should be logged in if you reach this page, so no need to check that the user
			// isn't NLI
			MappingPage mappingPage = new MappingPage(user, animal);
			hPanel.clear();
			hPanel.add(mappingPage);
		}
	
	private void addPetPage() {
		AddPetPage addPetPage = new AddPetPage(user);
		hPanel.clear();
		hPanel.add(addPetPage);
	}
      
}
