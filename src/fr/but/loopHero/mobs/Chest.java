package fr.but.loopHero.mobs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.droppable.Ressource;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Cell;

public class Chest extends Mobs{

	public Chest(Cell cell) {
		super("Chest",-1,11,0.6,-1,new Color(133,80,20),1,cell,genMobsList(),0);
	}

	@Override
	public void draw(Graphics2D graphics, int taille) {
		graphics.setColor(super.getColor()); // Couleur du mob
		int startingPointx = taille + super.getCurrentCell().j() * taille;
		int startingPointy = taille + super.getCurrentCell().i()* taille;
		graphics.fillRect(startingPointx,startingPointy,taille/3,taille/3);
		
		graphics.setColor(Color.black); // Contour du mob
		graphics.drawRect(startingPointx,startingPointy,taille/3,taille/3);
//		drawString(context, plateau.getBoardMatrix()[i][j].type().name(), Color.WHITE, 10, x, y);
		
	}

	private static ArrayList<Droppable> genMobsList() {
		ArrayList<Droppable> MOBS_DROPPABLE_ITEMS = new ArrayList<Droppable>();
		MOBS_DROPPABLE_ITEMS.add(new Ressource("pitful_remains")); // Ressources que le mob peut drop
		MOBS_DROPPABLE_ITEMS.addAll(LoopHeroGameData.MOBS_DROPPABLE_ITEMS);
		return MOBS_DROPPABLE_ITEMS;
	}
	
	@Override
	public void drawInCombat(Graphics2D graphics, int taille) {
		graphics.setColor(super.getColor()); // Couleur du mob
		int startingPointx = taille + 15 * taille;
		int startingPointy = taille + 6* taille;
		graphics.fillRect(startingPointx+taille,startingPointy,taille/3,taille/3);
		
		graphics.setColor(Color.black); // Contour du mob
		graphics.drawRect(startingPointx+taille,startingPointy,taille/3,taille/3);
		
		
	}
	
	@Override
	public boolean equals(Object obj) {
		return true;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
