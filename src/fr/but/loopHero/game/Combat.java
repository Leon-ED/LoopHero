package fr.but.loopHero.game;

import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.player.Player;

public class Combat {
	
	public static boolean isInCombat(Cell cell) {
		return cell.hasMob();
		
		
	}

}
