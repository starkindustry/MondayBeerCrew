package com.google.gwt.client.client;

import java.util.Date;

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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class AddPetPage extends Composite{
	private User user;
	
	private final AnimalServiceAsync animalService = GWT.create(AnimalService.class);
	private HorizontalPanel hPanel = new HorizontalPanel(); 
	private Button defaultButton = new Button("Main Page");

	private TextBox petNameTextBox = new TextBox();
	private Button addPetButton = new Button("Add Pet");
	private TextBox  breedTextBox = new TextBox();
	private ListBox genderListBox = new ListBox();
	private TextBox colorTextBox = new TextBox();
	private TextBox addressTextBox = new TextBox();
	private TextBox dateLostTextBox = new TextBox();


	public AddPetPage(User user) {
		this.user = user;
		
		initWidget(this.hPanel);
		
		hPanel.add(defaultButton);
		hPanel.add(addPetButton);

		defaultButton.addStyleName("defaultAlignment");
		
		addPetButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addPet();
			}
		});

		// on click default button
		defaultButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				defaultPage();
			}
		});
		
		

		//all sample code
		final FlexTable tbl = new FlexTable(); 

		tbl.setText(1, 0, "Pet Name: ");
		tbl.setWidget(1, 1, petNameTextBox);
		tbl.setText(2, 0, "Breed: ");
		tbl.setWidget(2, 1, breedTextBox);
		tbl.setText(3, 0, "Gender: ");
		tbl.setWidget(3, 1, genderListBox);
		tbl.setText(4, 0, "Color: ");
		tbl.setWidget(4,1, colorTextBox);
		tbl.setText(5, 0, "Address Last Seen: ");
		tbl.setWidget(5, 1, addressTextBox);
		tbl.setText(6, 0, "Date Lost: ");
		tbl.setWidget(6, 1, dateLostTextBox);
		tbl.setText(6, 2, "yyyy-MM-dd format or leave blank if N/A");
		tbl.setWidget(8, 0, addPetButton);
		
		// Add the choices to the genderListBox
	    genderListBox.addItem("M");
	    genderListBox.addItem("F");
	    genderListBox.addItem("M/N");
	    genderListBox.addItem("F/S");


		hPanel.add(tbl); 
	}
	
	private void addPet() {
		if (!checkDate() && !dateLostTextBox.getText().isEmpty()){
			Window.alert("Invalid date, please leave blank if N/A or write the date in yyyy-MM-dd format");
			return;
		}
		String name =  petNameTextBox.getText();
		String color = colorTextBox.getText();
		String breed = breedTextBox.getText();
		String searchTags = color + breed + name;
		Animal usersAnimal = new Animal();
		DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");
		usersAnimal.setAddress(addressTextBox.getText());
		usersAnimal.setName(name);
		usersAnimal.setColor(color);
		usersAnimal.setBreed(breed);
		usersAnimal.setSearchTags(searchTags);
		usersAnimal.setSex(genderListBox.getItemText(genderListBox.getSelectedIndex()));
		usersAnimal.setOwner(user.getUsername());
		// Set the DateLost if not empty
		if (!dateLostTextBox.getText().isEmpty()) {
			usersAnimal.setDateLost(dateLostTextBox.getText());
		}
		usersAnimal.setState("Lost");
		usersAnimal.setDateCreated(dtf.format(new Date()));
		
		animalService.addPet(usersAnimal, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error, failed to connect to server");
				
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Successfully added pet to database, may take a few seconds to show up.");
				userControlPanel();
				
			}
			
		});
	}
	
	public boolean checkDate(){
		DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");
	    try {
	        dtf.parseStrict(dateLostTextBox.getText());
	        return true;
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	}
	
	// takes you back to default page
	private void defaultPage() {
		// Should not reach this page unless you're logged in
		DefaultLoggedInPage defaultPage = new DefaultLoggedInPage(user); 
		hPanel.clear();
		hPanel.add(defaultPage);
	}
	
	public void userControlPanel() {
		UserControlPanel userControlPanel = new UserControlPanel(user);
		hPanel.clear();
		hPanel.add(userControlPanel);
	};


	
	
}
