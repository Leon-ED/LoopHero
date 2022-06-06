package fr.but.loopHero.droppable;

import fr.umlv.zen5.ApplicationContext;

public interface Droppable {

	void draw(ApplicationContext context, int i);

	void draw(ApplicationContext context, int i, int j);

	String displayName();

}
