package fr.but.loopHero.game;

import fr.but.loopHero.game.graphics.GameGraphics;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class Combat {
	
	public static boolean combatAvailable(Cell cell) {
		return cell.hasMob();
		
		
		
		
	}
	
	
    public static void startCombat(ApplicationContext context, Player hero,TimeData timedata,Cell cell,LoopHeroGameData data,GameGraphics graphics,Board plateau) {
		graphics.drawHero(plateau, context, hero, timedata,hero.getCurrentCellIndex());
		timedata.resetElapsedBob();
    	timedata.stop();
    	Mobs mob = cell.getFirstMob();
    	System.out.println("En combat !");
    	timedata.startCombat();
    	while(!(mob.isDead() || hero.isDead())) {
    		if(timedata.readyToAttack()) {
    			int heroAttack = hero.attack();
    			int mobAttack = mob.attack();
        		System.out.println("Attaque joueur = " +heroAttack+" Attaque mob = "+ mobAttack);
    			mob.takeDamage(heroAttack);
    			hero.takeDamage(mobAttack);
    			graphics.drawHealthInfos(context, hero);
    		}
    	}
    	if(hero.isDead()) {
    		System.out.println("LE HERO EST MORT, VIVE LE HERO");
    		context.exit(0);
    	}else {
    		hero.addInventory(mob.getDroppedItems());
    	}
    	timedata.stopCombat();
    	timedata.start();
    	cell.removeMob(mob);
    	
    }

}
