package com.google.gwt.client.server;

public class AnimalRegistryTest {
	public static void main(String [ ] args)
	{
		  AnimalRegistry reg = AnimalRegistry.defaultInstance();
		  reg.fetchUpdate();
		  System.out.println("Full Datastore size: " + reg.getAnimalList().size());
		  System.out.println("New things added to Datastore:" + reg.getNewAnimals().size());
		  reg.fetchUpdate();
		  System.out.println("Full Datastore size: " + reg.getAnimalList().size());
		  System.out.println("New things added to Datastore:" + reg.getNewAnimals().size());
	}
	
	
}
