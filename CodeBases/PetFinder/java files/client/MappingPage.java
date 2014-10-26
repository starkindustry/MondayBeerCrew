package com.google.gwt.client.client;

import com.google.gwt.client.shared.Animal;
import com.google.gwt.client.shared.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.maps.gwt.client.Geocoder;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.maps.gwt.client.GeocoderRequest;
import com.google.maps.gwt.client.GeocoderResult;
import com.google.maps.gwt.client.GeocoderStatus;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.Geocoder.Callback;


// This is where the user sees their own pets and adds pets/edits their existing pets
public class MappingPage extends Composite{
	private final AnimalServiceAsync animalService = GWT.create(AnimalService.class);
	private HorizontalPanel hPanel = new HorizontalPanel(); 
	private VerticalPanel vPanel = new VerticalPanel();
	private Button defaultButton = new Button("Back To Main Page");
	private FlexTable tbl = new FlexTable();
	private User user;
	private Animal animal; 
	private Geocoder geocoder;
	private GoogleMap map;
	private Marker marker;
	private LatLng latlng;


	public MappingPage(User user, Animal animal) {
		this.user = user;
		this.animal = animal; 

		initWidget(this.hPanel);
		hPanel.add(vPanel);
		hPanel.add(defaultButton);

		defaultButton.addStyleName("defaultAlignment");
		// on click default button
		defaultButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				defaultPage();
			}
		});

		tbl.setText(0, 0, "Name");  
		tbl.setText(0, 1, "Breed");  
		tbl.setText(0, 2, "Color");  
		tbl.setText(0, 3, "Sex");
		tbl.setText(0, 4, "Date Created");
		tbl.setText(0, 5, "Date Lost");
		tbl.setText(0, 6, "State");
		tbl.setText(0, 7, "Address");
		// Add styles to elements in the stock list table.
		tbl.getRowFormatter().addStyleName(0, "tblHeader");

		DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");
		tbl.setText(1, 0, animal.getName());
		tbl.setText(1, 1, animal.getBreed());
		tbl.setText(1, 2, animal.getColor());
		tbl.setText(1, 3, animal.getSex());
		tbl.setText(1, 4, dtf.format(animal.getDateCreated()));
		if (animal.getDateLost() == null)
			tbl.setText(1, 5, "Unknown");
		else 
			tbl.setText(1, 5, dtf.format(animal.getDateLost()));
		tbl.setText(1, 6, animal.getState());
		tbl.setText(1, 7, animal.getAddress());

		tbl.getCellFormatter().addStyleName(1,1, "FlexTable-Cell");
		tbl.getCellFormatter().addStyleName(1,2, "FlexTable-Cell");
		tbl.getCellFormatter().addStyleName(1,3, "FlexTable-Cell");
		tbl.getCellFormatter().addStyleName(1,4, "FlexTable-Cell");
		tbl.getCellFormatter().addStyleName(1,5, "FlexTable-Cell");
		tbl.getCellFormatter().addStyleName(1,6, "FlexTable-Cell");
		tbl.getCellFormatter().addStyleName(1,7, "FlexTable-Cell");

		vPanel.add(tbl);

		geocoder = Geocoder.create();
		
		GeocoderRequest request = GeocoderRequest.create();
		request.setAddress(animal.getAddress());
		geocoder.geocode(request, new Callback() {
			public void handle(JsArray<GeocoderResult> results, GeocoderStatus status) {
				if (status == GeocoderStatus.OK) {
					GeocoderResult location = results.get(0);
					latlng = location.getGeometry().getLocation();
					SimplePanel mapWidget = new SimplePanel();
					mapWidget.setHeight("600px");
					mapWidget.setWidth("600px");
					MapOptions myOptions = MapOptions.create();
					myOptions.setZoom(15.0);
					myOptions.setCenter(latlng);
					myOptions.setMapTypeId(MapTypeId.ROADMAP);
					map = GoogleMap.create(mapWidget.getElement(), myOptions);
					
					MarkerOptions markerOptions = MarkerOptions.create();
					markerOptions.setPosition(latlng);
					markerOptions.setMap(map);
				
					marker = Marker.create(markerOptions);
																			
					vPanel.add(mapWidget);
					
					map.triggerResize();
					
				} else {
					Window.alert("We were unable to locate that address.");
				}
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
}

