package fr.but.loopHero.mobs;

import java.awt.Color;
import java.util.ArrayList;

import fr.but.loopHero.droppable.Droppable;

public interface Mobs {
	boolean isDead();
	
	Color getColor();
	
	void takeDamage(int damages);
	
	int attack();
	
	int health();
	
	boolean equals(Object o);
	
	int hashCode();
	
	int[] getPos();

	ArrayList<Droppable> getDroppedItems();
	
	
}
