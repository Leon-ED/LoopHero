package fr.but.loopHero.mobs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.droppable.Ressource;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Cell;

public class Slime extends Mobs{

	
	
	public Slime(Cell cell) {
		super(5,13,3.3,0.6,Color.green,35,cell,genMobsList());
	}
	
	private static ArrayList<Droppable> genMobsList() {
		ArrayList<Droppable> MOBS_DROPPABLE_ITEMS = new ArrayList<Droppable>();
		MOBS_DROPPABLE_ITEMS.add(new Ressource("craft_fragment"));// Ressources que le mob peut drop
		MOBS_DROPPABLE_ITEMS.add(new Ressource("shapeless_mass")); 
		MOBS_DROPPABLE_ITEMS.addAll(LoopHeroGameData.MOBS_DROPPABLE_ITEMS);
		return MOBS_DROPPABLE_ITEMS;
	}

	@Override
	public void draw(Graphics2D graphics, int taille) {
		int minus = -10;
		
		graphics.setColor(super.getColor()); // Couleur du slime
		int startingPointx = taille + super.getCurrentCell().j() * taille;
		int startingPointy = taille + super.getCurrentCell().i()* taille;
		graphics.fillOval(startingPointx,startingPointy,(taille/2)+minus,(taille/2)+minus);
		
		graphics.setColor(Color.black); // Contour du slime
		graphics.drawOval(startingPointx,startingPointy,(taille/2)+minus,(taille/2)+minus);
		
	}
	

	
}
