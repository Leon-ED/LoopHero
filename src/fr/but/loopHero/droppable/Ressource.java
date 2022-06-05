package fr.but.loopHero.droppable;

import java.util.Objects;

import fr.umlv.zen5.ApplicationContext;

public class Ressource implements Droppable {

	
	private final String name;
	
	
	public Ressource(String name) {
		this.name = Objects.requireNonNull(name);
		
	}


	@Override
	public void draw(ApplicationContext context, int i) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String displayName() {
		return name;
	}


	@Override
	public void draw(ApplicationContext context, int i, int j) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
