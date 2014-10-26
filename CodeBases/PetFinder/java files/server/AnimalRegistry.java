package com.google.gwt.client.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.google.gwt.client.shared.Animal;

public class AnimalRegistry {

	/**
	 * Static default instance.
	 */
	private static AnimalRegistry defaultInstance;
	private static List<Animal> reg;
	private List<Animal> animalList = new ArrayList<Animal>();
	private List<Animal> newAnimals = new ArrayList<Animal>();
	private Date lastUpdate;
	
	private AnimalRegistry (){}
	
	/**
	 * Create the default AnimalRegistry instance, which contains the
	 * lost animals that are listed in the LostAnimals.xml
	 * @return result, the AnimalRegistry
	 */
	public static AnimalRegistry defaultInstance() {
		
		if (defaultInstance == null){
			defaultInstance = new AnimalRegistry();
		}
		return defaultInstance;
	}
	
	public void setTime(Date newTime){
		lastUpdate  = newTime;
	}
	
	public void fetchUpdate(){
		// Create the parser and read in the data from 
		// LostAnimals.xml here
		newAnimals = new ArrayList<Animal>();
		reg = new ArrayList<Animal>();
		
		try{
			XMLReader reader = XMLReaderFactory.createXMLReader();
			
			if (lastUpdate == null){
				// if lastUpdate hasn't been initialized, set it to a time that can fetch all data
				Calendar start = Calendar.getInstance();
				start.clear();
				start.set(2005, 06, 01);
				lastUpdate = start.getTime();
			}
			
			reader.setContentHandler(new Parser(defaultInstance, animalList.size(), lastUpdate));
			reader.parse("http://www.ugrad.cs.ubc.ca/~v5o8/LostAnimals.xml");
			
			// set lastUpdate to current time
			lastUpdate = new Date();
			
			for(Animal animal : reg)
				defaultInstance.add(animal);

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void add(Animal animal) {
		animalList.add(animal);	
		newAnimals.add(animal);
	}
	
	public List<Animal> getAnimalList(){
		return animalList;
	}
	
	public List<Animal> getNewAnimals(){
		return newAnimals;
	}

}
