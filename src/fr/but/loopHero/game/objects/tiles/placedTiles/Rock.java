package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.Tuple;
import fr.but.loopHero.game.objects.tiles.LandScape;
import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class Rock extends PlacedTiles {

	private boolean placedEffectDone = false;

	public Rock() {
		super("Rock", new LandScape("Rock"), Color.black);
	}

	@Override
	public void doEffects(ApplicationContext context, Player hero, Board plateau, LoopHeroGameData datas) {

		if (!placedEffectDone) {
			hero.increaseMaxHealth((int) (hero.getHealths()[1] * 0.01));
		}
	}

	public static void doNeighborsEffects(Board plateau, Cell cell, Player hero) {
		int i = cell.i();
		int j = cell.j();
		List<Tuple> listIndex;
		listIndex = Tuple.getAdjactentsPos();
		Cell[][] boardMatrix = plateau.getBoardMatrix();
		ArrayList<Cell> liste = new ArrayList<>();
		for (Tuple tuple : listIndex) {
			int x = tuple.i();
			int y = tuple.j();
			if (!plateau.isOutOfBounds(x + i, y + j)) {
				Cell celle = boardMatrix[x + i][y + j];
				if (celle.type() instanceof Rock) {
					liste.add(celle);
				}

			}
		}

		for (Cell cellToAdd : liste) {
			if (cellToAdd.type() instanceof Rock) {
				System.out.println("ok");
				hero.increaseMaxHealth((int) (hero.getHealths()[1] * 0.01));
			}
		}
	}

	@Override
	public Tile generateNew() {
		return new Rock();
	}

	@Override
	public boolean equals(Object obj) {
		return true;
	}
}
