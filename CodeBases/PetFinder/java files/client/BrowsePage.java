package com.google.gwt.client.client;

import java.util.List;

import com.google.gwt.client.client.DefaultPage.AboutPopup;
import com.google.gwt.client.client.DefaultPage.ContactPopup;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class BrowsePage extends Composite {
	
	private final AnimalServiceAsync animalService = GWT.create(AnimalService.class);
	private HorizontalPanel hPanel = new HorizontalPanel(); 
	private ScrollPanel scrollPanel = new ScrollPanel(); 
	private HorizontalPanel mainPanel = new HorizontalPanel();
	private HorizontalPanel loginPanel = new HorizontalPanel();
	private HorizontalPanel bottomPanel = new HorizontalPanel();
	private Button loginButton = new Button("Login");
	private Button logoutButton = new Button("Log out");
	private Button defaultButton = new Button("Back To Main Page");
	private Button myPetButton = new Button("My Pets");
	private FlexTable tbl = new FlexTable();
	private User user;
	
	public BrowsePage(User user) {
		this.user = user;
		initWidget(this.hPanel);
		
		loginPanel.add(defaultButton);
		defaultButton.addStyleName("defaultButtonStyle");
		
		if(user==null) {
			loginPanel.add(loginButton);
		}
		
		if(user!=null) {
			Label welcomeLabel = new Label("Welcome " + user.getUsername());
			loginPanel.add(welcomeLabel);
			loginPanel.add(myPetButton);
			loginPanel.add(logoutButton);
		}
		
		Image logo = new Image("images/Petfinderlogo.png");
        mainPanel.add(logo);
		 
		defaultButton.addStyleName("defaultAlignment");
		// on click default button
		defaultButton.addClickHandler(new ClickHandler() {
		      @Override
			public void onClick(ClickEvent event) {
		        defaultPage();
		      }
		    });
		
		   // creates about us button
        final Image aboutUsButton = new Image("images/AboutUsButton.png");
        Button aboutButton = new Button();
        aboutButton.getElement().appendChild(aboutUsButton.getElement());
        bottomPanel.add(aboutUsButton);
          
        // creates contact us button
        final Image contactUsButton = new Image("images/ContactUsButton.png");
        Button contactButton = new Button();
        contactButton.getElement().appendChild(contactUsButton.getElement());
        bottomPanel.add(contactUsButton);
        bottomPanel.addStyleName("aboutAlignment");
		
        scrollPanel.setSize("920px", "430px");
		scrollPanel.add(tbl);
	       
		
		logoutButton.addClickHandler(new ClickHandler() {
            @Override
			public void onClick(ClickEvent event) {
              loggedOutPage();
            }
          });
		
		// creates popup when you click on about us button
        aboutUsButton.addClickHandler(new ClickHandler() {
            @Override
			public void onClick(ClickEvent event) {
              new AboutPopup().show();
            }
        });
         
        // creates popup when you click on contact us button
        contactUsButton.addClickHandler(new ClickHandler() {
            @Override
			public void onClick(ClickEvent event) {
              new ContactPopup().show();
            }
        });
        
        loginButton.addClickHandler(new ClickHandler() {
            @Override
			public void onClick(ClickEvent event) {
              loginPage();
            }
          });
        
        myPetButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				userControlPanel();
			}
		});
         
        // bottom aligns the contact and about us panel
		
		animalService.getAllAnimals(new AsyncCallback<List<Animal>>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("No Listings Available");

			}

			@Override
			public void onSuccess(List<Animal> result) {
				
				tbl.setText(0, 0, "Name");  
				tbl.setText(0, 1, "Breed");  
				tbl.setText(0, 2, "Color");  
				tbl.setText(0, 3, "Sex");
				tbl.setText(0, 4, "Date Created");
				tbl.setText(0, 5, "Date Lost");
				tbl.setText(0, 6, "State");
				tbl.setText(0, 7, "Address Lost");
				
			    // Add styles to elements in the stock list table.
			    tbl.getRowFormatter().addStyleName(0, "tblHeader");
				
				DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");
		
				int row = tbl.getRowCount();
				
				for (Animal animal : result){
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
					tbl.setText(row, 7, animal.getAddress());
					
					tbl.getCellFormatter().addStyleName(row,1, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,2, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,3, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,4, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,5, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,6, "FlexTable-Cell");
					tbl.getCellFormatter().addStyleName(row,7, "FlexTable-Cell");
					row++;
				
				}
				tbl.addStyleName("tblList");
			}

		});
		
		  RootPanel.get("loginPanel").add(loginPanel);
		  RootPanel.get("mainPanel").add(mainPanel);
		  RootPanel.get("scrollPanel").add(scrollPanel); 
	      RootPanel.get("bottomPanel").add(bottomPanel);
	}
	
	private void userControlPanel() {
		UserControlPanel userControlPanel = new UserControlPanel(user);
		hPanel.clear();
		loginPanel.clear(); 
		mainPanel.clear();
		scrollPanel.setSize("0px", "0px");
		scrollPanel.clear();
		bottomPanel.clear();
		hPanel.add(userControlPanel);
	}
      

	protected void loggedOutPage() {
		DefaultPage defaultPage = new DefaultPage(); 
		hPanel.clear();
		loginPanel.clear(); 
		mainPanel.clear();
		scrollPanel.setSize("0px", "0px");
		scrollPanel.clear();
		bottomPanel.clear();
		hPanel.add(defaultPage);	
	}

	protected void loginPage() {
        LoginPage loginPage = new LoginPage();
        hPanel.clear();
        mainPanel.clear();
        scrollPanel.setSize("0px", "0px");
        scrollPanel.clear(); 
        loginPanel.clear();
        bottomPanel.clear();
        hPanel.add(loginPage);
		
	}

	// takes you back to default page
	private void defaultPage() {
		
		if (user == null) {
			DefaultPage defaultPage = new DefaultPage(); 
			hPanel.clear();
			mainPanel.clear();
			loginPanel.clear(); 
			scrollPanel.setSize("0px", "0px");
			scrollPanel.clear();
			bottomPanel.clear();
			hPanel.add(defaultPage);
				
		}
		else  {
			DefaultLoggedInPage defaultPage = new DefaultLoggedInPage(user);
			hPanel.clear();
			loginPanel.clear(); 
			mainPanel.clear();
			scrollPanel.setSize("0px", "0px");
			scrollPanel.clear();
			bottomPanel.clear();
			hPanel.add(defaultPage);
		}
      
}

}