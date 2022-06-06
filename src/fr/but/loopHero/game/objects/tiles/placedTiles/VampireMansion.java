package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;
import java.util.ArrayList;

import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.tiles.RoadSide;
import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.mobs.Vampire;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class VampireMansion extends PlacedTiles{

	public VampireMansion() {
		super("Vampire_Mansion", new RoadSide("Vampire Mansion"), new Color(189,101,19));
	}

	@Override
	public Tile generateNew() {
		return new VampireMansion();
	}

	@Override
	public void doNewDayEffects(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas,Cell cell) {
		//Check si une cell peut accueil le mob
		ArrayList<Cell> liste = cell.getAdjacentsCells(false,plateau,cell.i(),cell.j());
		System.out.println(liste);
		for (Cell cellToAdd : liste) {
			if(!cellToAdd.hasMob()) {
				Mobs mob = new Vampire(cellToAdd);
				System.out.println(cellToAdd.i()+" "+cellToAdd.j());
				cellToAdd.addMob(mob);
				break;
			}
		}
		
		
		
				
			
			
		
		
	}
}
