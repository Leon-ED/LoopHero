package fr.but.loopHero.mobs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.droppable.Ressource;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Cell;

public class ScareCrow extends Mobs {

	public ScareCrow(Cell cell) {
		super("ScareCrow", -1, 18, 8.25, 0.45, Color.ORANGE.brighter(), 100, cell, genMobsList(), 0);

	}

	@Override
	public void draw(Graphics2D graphics, int taille) {
		int minus = 0;

		graphics.setColor(super.getColor()); // Couleur du smob
		int startingPointx = taille + super.getCurrentCell().j() * taille;
		int startingPointy = taille + super.getCurrentCell().i() * taille;
		graphics.fillRect(startingPointx, startingPointy, (taille / 2) + 10, (taille / 3) + minus);

		graphics.setColor(Color.black); // Contour du mob
		graphics.drawRect(startingPointx, startingPointy, (taille / 2) + 10, (taille / 3) + minus);

	}

	@Override
	public void drawInCombat(Graphics2D graphics, int taille) {
		int minus = 0;
		graphics.setColor(super.getColor()); // Couleur du mob
		int startingPointx = taille + 15 * taille;
		int startingPointy = taille + 6 * taille;
		graphics.fillRect(startingPointx + taille, startingPointy, (taille / 2) + 10, (taille / 3) + minus);

		graphics.setColor(Color.black); // Contour du mob
		graphics.drawRect(startingPointx + taille, startingPointy, (taille / 2) + 10, (taille / 3) + minus);

	}

	private static ArrayList<Droppable> genMobsList() {
		ArrayList<Droppable> MOBS_DROPPABLE_ITEMS = new ArrayList<Droppable>();
		MOBS_DROPPABLE_ITEMS.add(new Ressource("stable_branches"));
		MOBS_DROPPABLE_ITEMS.add(new Ressource("ration"));
		MOBS_DROPPABLE_ITEMS.add(new Ressource("craft_fragment"));// Ressources que le mob peut drop
		MOBS_DROPPABLE_ITEMS.addAll(LoopHeroGameData.MOBS_DROPPABLE_ITEMS);
		return MOBS_DROPPABLE_ITEMS;
	}

}
