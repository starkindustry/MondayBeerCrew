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

public class RegisterPage extends Composite {

	private final UserServiceAsync userService = GWT.create(UserService.class);

	private HorizontalPanel hPanel = new HorizontalPanel(); 
	private Button defaultButton = new Button("Main Page");

	private HorizontalPanel registrationPanel = new HorizontalPanel();
	private TextBox userNameTextBox = new TextBox();
	private Button checkAvailability = new Button("Check Availability");
	private TextBox passwordTextBox = new PasswordTextBox();
	private TextBox confirmPasswordTextBox = new PasswordTextBox();
	private Button registerButton = new Button("Register");
	private boolean nameAvailable = false;


	public RegisterPage() {
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

		tbl.setText(1, 0, "User Name: ");
		tbl.setWidget(1, 1, userNameTextBox);
		tbl.setWidget(1, 2, checkAvailability);
		tbl.setText(2, 0, "Password: ");
		tbl.setWidget(2, 1, passwordTextBox);
		tbl.setText(3, 0, "Confirm Password: ");
		tbl.setWidget(3, 1, confirmPasswordTextBox);
		tbl.setWidget(4,1, registerButton);

		// on click Check Availabiltiy button
		checkAvailability.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				checkForAvailability();
			}
		});

		// If they press any keys in the Username text box, set availability of username to false
		userNameTextBox.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				nameAvailable = false;
			}
		});

		// Listen for keyboard events in the confirm password box.
		confirmPasswordTextBox.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					registerUser();
				}
			}
		});

		// on click Register button
		registerButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				registerUser();
			}
		});



		hPanel.add(tbl); 


		hPanel.add(tbl); 
	}

	// takes you back to default page
	private void defaultPage() {
		DefaultPage defaultPage = new DefaultPage(); 
		hPanel.clear();
		hPanel.add(defaultPage);
	}
	
	private void loginPage() {
		LoginPage loginPage = new LoginPage();
		hPanel.clear();
		hPanel.add(loginPage);
	}

	private void checkForAvailability(){

		final String username = userNameTextBox.getText();

		if (username.length() < 5){
			Window.alert("Please enter a name that's at least 5 characters long.");
		}

		else {
			userService.getUser(username, new AsyncCallback<User>(){

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error, unable to connect to the server.");

				}

				@Override
				public void onSuccess(User result) {
					if (result == null){
						nameAvailable = true;
						Window.alert("The name " + username + " is available.");
					}
					
					else {
						nameAvailable = false;
						Window.alert("The name " + username + " is unavailable.");
					}
					
				}

			});
		}
	}

	private void registerUser(){
		String username = userNameTextBox.getText();
		String password = passwordTextBox.getText();
		String confirmPassword = confirmPasswordTextBox.getText();

		if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
			Window.alert("Please fill out every field");
		}

		else if (!nameAvailable){
			Window.alert("Please ensure your selected username is available.");
		}

		else if (password.length() < 5 || confirmPassword.length() < 5) {
			Window.alert("Your password must be at least 5 characters long.");
		}

		else if (!password.equals(confirmPassword)){
			Window.alert("Your passwords do not match");
		}

		else {
			User newUser = new User();
			newUser.setup(username, password);
			userService.registerUser(newUser, new AsyncCallback<Void>(){

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error, unable to connect to server.");

				}

				@Override
				public void onSuccess(Void result) {
					Window.alert("Registered successfully!");
					loginPage();

				}
			});
		}
	}
	
}