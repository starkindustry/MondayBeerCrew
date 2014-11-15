package com.google.gwt.client.client;

import java.util.ArrayList;
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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AdminPanel extends Composite{
	
	private final AnimalServiceAsync animalService = GWT.create(AnimalService.class);
	private HorizontalPanel hPanel = new HorizontalPanel(); 
	private Button defaultButton = new Button("Back To Main Page");
	private FlexTable tbl = new FlexTable();
	private User user;
	// Keep a list of all selected pets (their key, more specifically) for removal
	private List<Long> animalsToBeRemoved = new ArrayList<Long>();
	
	
	public AdminPanel(User user) {
		VerticalPanel vPanel = new VerticalPanel();
		this.user = user;
		initWidget(this.hPanel);
		hPanel.add(defaultButton);
		 
		defaultButton.addStyleName("defaultAlignment");
		// on click default button
		defaultButton.addClickHandler(new ClickHandler() {
		      @Override
			public void onClick(ClickEvent event) {
		        defaultPage();
		      }
		    });
	
		// Button for removing selected pets
		Button removeSelectedPets = new Button("Remove All Selected");
		removeSelectedPets.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				removeSelectedPets();
			}
		});
		vPanel.add(removeSelectedPets);
		vPanel.add(tbl);
		hPanel.add(vPanel); 
		
		animalService.getAllAnimals(new AsyncCallback<List<Animal>>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("No Listings Available");

			}

			@Override
			public void onSuccess(List<Animal> result) {

				tbl.setText(0, 1, "Name");  
				tbl.setText(0, 2, "Breed");  
				tbl.setText(0, 3, "Color");  
				tbl.setText(0, 4, "Sex");
				tbl.setText(0, 5, "Date Created");
				tbl.setText(0, 6, "Date Lost");
				tbl.setText(0, 7, "State");
			    // Add styles to elements in the stock list table.
			    tbl.getRowFormatter().addStyleName(0, "tblHeader");
				
				DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");
		
				int row = tbl.getRowCount();
				
				for (final Animal animal : result){
					CheckBox selectPet = new CheckBox();
					selectPet.addClickHandler(new ClickHandler(){
						@Override
						public void onClick(ClickEvent event) {
							CheckBox checkBox = (CheckBox)event.getSource();
							if (checkBox.getValue())
								animalsToBeRemoved.add(animal.getKey());
							else
								animalsToBeRemoved.remove(animal.getKey());
						}
					}
					);
					
					tbl.setWidget(row, 0, selectPet);
					tbl.setText(row, 1, animal.getName());  
					tbl.setText(row, 2, animal.getBreed());  
					tbl.setText(row, 3, animal.getColor());  
					tbl.setText(row, 4, animal.getSex());
					tbl.setText(row, 5, dtf.format(animal.getDateCreated()));
					if (animal.getDateLost() == null)
						tbl.setText(row, 6, "Unknown");
					else
						tbl.setText(row, 6, dtf.format(animal.getDateLost()));
					tbl.setText(row, 7, animal.getState());
					Button remove = new Button ("Remove");
					remove.addClickHandler(new ClickHandler(){

						@Override
						public void onClick(ClickEvent event) {
							removePet(animal.getKey());
						}
					});
					tbl.setWidget(row, 8, remove);
					tbl.getCellFormatter().addStyleName(row,1, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,2, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,3, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,4, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,5, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,6, "FlexTable-Cell");
					row++;
					
				}
				tbl.addStyleName("tblList");
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
				Window.alert("Successfully removed pet (may take a moment for pets to disappear)");
				AdminPanel adminPanel = new AdminPanel(user);
				hPanel.clear();
				hPanel.add(adminPanel);

			}
			
		});
	}
	
	private void removeSelectedPets(){
		if (animalsToBeRemoved.isEmpty()){
			Window.alert("Please select some pets to be removed");
			return;
		}
		
		animalService.removeListOfPets(animalsToBeRemoved, new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error, unable to connect to the server");
				
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Successfully removed selected pets (may take a moment for pets to disappear)");
				AdminPanel adminPanel = new AdminPanel(user);
				hPanel.clear();
				hPanel.add(adminPanel);
				
			}
			
		});
	}
	
	// takes you back to default page
	private void defaultPage() {
			DefaultLoggedInPage defaultPage = new DefaultLoggedInPage(user);
			hPanel.clear();
			hPanel.add(defaultPage);
      
	}

}
