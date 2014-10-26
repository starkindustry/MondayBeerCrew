package com.google.gwt.client.client;

import com.google.gwt.client.shared.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;


public class LoginPage extends Composite {
	private final UserServiceAsync userService = GWT.create(UserService.class);
	private HorizontalPanel hPanel = new HorizontalPanel(); 
	private Button defaultButton = new Button("Back to Main Page");
	private TextBox userNameTextBox = new TextBox();
	private PasswordTextBox userPassword = new PasswordTextBox();
	private Button loginButton = new Button("Login");
	private Button registerButton = new Button("New User");
	private String userName;
	private String passWord;
	private User user;

	public LoginPage() {
		// initializes page
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

		//all sample code
		final FlexTable tbl = new FlexTable(); 
		
		//Label label3 = new Label("Please enter your username and password"); 
		tbl.setText(0, 1, "Please enter your username and password");
		tbl.setText(1, 0, "User Name: ");
		tbl.setWidget(1, 1, userNameTextBox);
		tbl.setText(2, 0, "Password: ");
		tbl.setWidget(2, 1, userPassword);
		tbl.setWidget(3, 1, loginButton);
		tbl.setWidget(4, 1, registerButton);

		
		hPanel.add(tbl); 
		
		
		// Listen for keyboard events in the password box.
		userPassword.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			    	  goLogin();
				}
			}
		});


		// on click loginButton
		loginButton.addClickHandler(new ClickHandler() {
		      @Override
			public void onClick(ClickEvent event) {
		    	  goLogin();
		      }
		    });

		// on click register button
		registerButton.addClickHandler(new ClickHandler() {
		      @Override
			public void onClick(ClickEvent event) {
		        registerPage();
		      }
		    });
	}
	
	protected void goLogin() {
  	    userName = userNameTextBox.getText();
	    passWord = userPassword.getText();
	    
	    // Check if the username and passwords are both 5 characters or longer first, don't want
	    // to access datastore if we don't have to
	    
	    if (userName.length() < 5 || passWord.length() < 5){
	    	Window.alert("Invalid username/password");
			userName = "";
			passWord = "";
			userNameTextBox.setText("");
			userPassword.setText("");
			return;
	    }
	    
	    // First, check to make sure that the username is a valid one in the datastore
	    userService.getUser(userName, new AsyncCallback<User>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error, unable to connect to the server");
			}

			@Override
			public void onSuccess(User result) {
				// User does not exist
				if (result == null){
					Window.alert("Invalid username/password");
					userName = "";
					passWord = "";
					userNameTextBox.setText("");
					userPassword.setText("");
					return;
				}
				
				// User exists, check the password
				else {
					// If the username is in the datastore, check the password
					userName = result.getUsername();
					user = result;
					checkPassword(result.getPassword());
				}
				
			}
	    	
	    });
	}
	
	protected void checkPassword(String password){

		if (passWord.equals(password)){
			Window.alert("Logged in successfully.");
			defaultLoggedInPage();
		}

		else {
			Window.alert("Invalid username/password");
			userName = "";
			passWord = "";
			userNameTextBox.setText("");
			userPassword.setText("");
			return;
		}
	}


	// takes you back to default page
	private void defaultPage() {
		DefaultPage defaultPage = new DefaultPage(); 
	    hPanel.clear();
	    hPanel.add(defaultPage);
     }
	
	private void defaultLoggedInPage() {
		DefaultLoggedInPage defaultLoggedInPage = new DefaultLoggedInPage(user); 
	    hPanel.clear();
	    hPanel.add(defaultLoggedInPage);
     }
	
	private void registerPage() {
		RegisterPage registerPage = new RegisterPage(); 
	    hPanel.clear();
	    hPanel.add(registerPage);
     }

}