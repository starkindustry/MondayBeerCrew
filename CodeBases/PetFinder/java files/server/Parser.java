package com.google.gwt.client.server;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.google.gwt.client.shared.Animal;

import java.util.Date;



/**
 * Parses the lost animals in the file LostAnimals.xml
 * and stores the animal's details in the AnimalRegistry.
 * 
 * Effects: the AnmalRegistry has been populated with all the
 * animals listed in the LostAnimals.xml
 */

public class Parser extends DefaultHandler {
	
	private AnimalRegistry reg;
	private Animal animal;
	int counter;
	private Date lastUpdate;
	private StringBuilder searchTagBuilder;
	
	public Parser(AnimalRegistry reg, int counter, Date lastUpdate) {
		this.reg = reg;
		this.counter = counter + 1;
		this.lastUpdate = lastUpdate;
		searchTagBuilder = new StringBuilder();
	}


	// Remember information being parsed
	StringBuffer accumulator;
	
	/**
	 * Called at the start of the document (as the name suggests)
	 */
	@Override
	public void startDocument() {
		//System.out.println("Start Document!");
		
		// Use accumulator to remember information parsed.
		// Just initialize for now.
		accumulator = new StringBuffer();		
	}
	
	/**
	 * Called when the parsing of an element starts.
	 */
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) {
		// System.out.println("StartElement: " + qName);

		if (qName.toLowerCase().equals("lostanimal")) {
			//System.out.println("Animal # " + counter);
			animal = new Animal();
			
		}
		// Let's start the accumulator
		// to remember the value that get parsed
		accumulator.setLength(0);
	}
	
	/**
	 * Called for values of elements.
	 */
	@Override
	public void characters(char[] temp, int start, int length) {
		accumulator.append(temp, start, length);
	}
	
	/**
	 * Called when the end of an element is seen.
	 */
	@Override
	public void endElement(String uri, String localName, String qName){
		//System.out.println(qName + " : " + accumulator.toString().trim());
		if(qName.toLowerCase().equals("date")) {
			animal.setDateLost(accumulator.toString().trim());
		} else if (qName.toLowerCase().equals("color")) {
			animal.setColor(accumulator.toString().trim());
			searchTagBuilder.append(accumulator.toString().trim());
		} else if (qName.toLowerCase().equals("breed")) {
			animal.setBreed(accumulator.toString().trim());
			searchTagBuilder.append(accumulator.toString().trim());
		} else if (qName.toLowerCase().equals("sex")) {
			animal.setSex(accumulator.toString().trim());
		} else if (qName.toLowerCase().equals("state")) {
			animal.setState(accumulator.toString().trim());
		} else if(qName.toLowerCase().equals("name")) {
			animal.setName(accumulator.toString().trim());
			searchTagBuilder.append(accumulator.toString().trim());
		} else if(qName.toLowerCase().equals("datecreated")) {
			animal.setDateCreated(accumulator.toString().trim());
		} else if (qName.toLowerCase().equals("lostanimal")) {
			// We only want the animal that is found, waiting for
			// owner to claim
			if (animal.getState().toLowerCase().equals("found")){
				if (lastUpdate == null){
					// case where the lastUpdate field hasn't been initialized
					animal.setSearchTags(searchTagBuilder.toString());
					reg.add(animal);
					// increase the counter when animal is added
					counter++;
				}
				
				else if (lastUpdate.before(animal.getDateCreated())){
					// add if last update was before the date of the current animal
					animal.setSearchTags(searchTagBuilder.toString());
					reg.add(animal);
					counter++;
				}
				
				
			}
			searchTagBuilder = new StringBuilder();
		}
		// Reset the accumulator because we have seen the value
		accumulator.setLength(0);
		
	}

	
//	public void endDocument(){
//		System.out.println("End Document");
//	}
}
