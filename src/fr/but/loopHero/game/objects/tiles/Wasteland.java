package fr.but.loopHero.game.objects.tiles;

import java.awt.Color;
import java.util.Random;

import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.mobs.Slime;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class Wasteland extends Road {

	public Wasteland() {
		super("Wasteland",Color.gray);
	}

	public void doNewDayEffects(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas,Cell cell) {
		if (!cell.hasMob()) { 	
			
			
			
			
			Random rand = new Random();
			int rand_number = rand.nextInt(99);
			if (rand_number < 5) { // < 5
				cell.addMob(new Slime(cell));
			}
		}
	}

	
	
	
	
	
	
	
}
