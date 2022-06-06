package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;
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

public class Spider_Coccon extends PlacedTiles{

	public Spider_Coccon() {
		super("Spider_Coccon", new RoadSide("Spider Coccon"), new Color(171,26,55));
	}

	@Override
	public Tile generateNew() {
		return new Spider_Coccon();
	}

	@Override
	public void doNewDayEffects(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas,Cell cell) {
		//Check si une cell peut accueil le mob
		cell.getAdjacentsCells(new Road(""),plateau,i,j);
		
				Mobs spider = new Spider(cell);
				cell.addMob(spider);
				//System.out.println(cell.hasMob());	
			
			
		
		
	}
}
