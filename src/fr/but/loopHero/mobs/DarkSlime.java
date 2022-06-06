package fr.but.loopHero.mobs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.droppable.Ressource;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Cell;

public class DarkSlime extends Mobs {

	public DarkSlime(Cell cell) {
		super("Gluant sombre !", -1, 14, 3.7, 0.9, new Color(9, 20, 66), 45, cell, genMobsList(), 5);
	}

	private static ArrayList<Droppable> genMobsList() {
		ArrayList<Droppable> MOBS_DROPPABLE_ITEMS = new ArrayList<Droppable>();
		MOBS_DROPPABLE_ITEMS.add(new Ressource("time_shard"));
		MOBS_DROPPABLE_ITEMS.add(new Ressource("shapeless_mass"));
		MOBS_DROPPABLE_ITEMS.addAll(LoopHeroGameData.MOBS_DROPPABLE_ITEMS);
		return MOBS_DROPPABLE_ITEMS;
	}

	@Override
	public void draw(Graphics2D graphics, int taille) {
		int minus = -10;

		graphics.setColor(super.getColor()); // Couleur du slime
		int startingPointx = taille + super.getCurrentCell().j() * taille;
		int startingPointy = taille + super.getCurrentCell().i() * taille;
		graphics.fillOval(startingPointx, startingPointy, (taille / 2) + minus, (taille / 2) + minus);

		graphics.setColor(Color.white); // Contour du slime
		graphics.drawOval(startingPointx, startingPointy, (taille / 2) + minus, (taille / 2) + minus);

	}

	@Override
	public void drawInCombat(Graphics2D graphics, int taille) {

		graphics.setColor(super.getColor()); // Couleur du slime
		int startingPointx = 1000;
		int startingPointy = 400;
		graphics.fillOval(startingPointx, startingPointy, taille, taille);

		graphics.setColor(Color.white); // Contour du slime
		graphics.drawOval(startingPointx, startingPointy, taille, taille);

	}

}
