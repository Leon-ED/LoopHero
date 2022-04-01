package fr.but.loopHero.droppable;

import java.util.Objects;

public class Ressource implements Droppable {

	
	private final String name;
	
	
	public Ressource(String name) {
		this.name = Objects.requireNonNull(name);
		
	}
	
	
	
}
