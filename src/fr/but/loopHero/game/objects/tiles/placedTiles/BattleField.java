package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.tiles.Road;
import fr.but.loopHero.game.objects.tiles.RoadSide;
import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.mobs.Spider;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class BattleField extends PlacedTiles{

	public BattleField() {
		super("BattleField", new RoadSide("Battle Field"), new Color(180,80,14));
	}

	@Override
	public Tile generateNew() {
		return new BattleField();
	}

	@Override
	public void doNewDayEffects(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas,Cell cell) {
		//Check si une cell peut accueil le mob
		ArrayList<Cell> liste = cell.getAdjacentsCells(true,plateau,cell.i(),cell.j());
		System.out.println(liste);
		for (Cell cellToAdd : liste) {
			if(!cellToAdd.hasMob()) {
				Mobs mob = new Spider(cellToAdd);
				cellToAdd.addMob(mob);
				break;
			}
		}
		
		
		
				
			
			
		
		
	}
}
