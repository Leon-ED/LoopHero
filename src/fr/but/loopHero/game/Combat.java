package fr.but.loopHero.game;

import java.util.ArrayList;
import java.util.Objects;

import fr.but.loopHero.droppable.Droppable;
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
	
	private final Player hero;
	private final Cell cell;
	private final Mobs mob;
	private final ArrayList<ArrayList<Droppable>> loot;
	
	public Combat(Player hero, Cell cell,ApplicationContext context,TimeData timedata,LoopHeroGameData data,GameGraphics graphics) {
		this.hero = Objects.requireNonNull(hero);
		this.cell = Objects.requireNonNull(cell);
		this.mob = Objects.requireNonNull(cell.getFirstMob());
		this.loot = Objects.requireNonNull(mob.getDroppedItems());
		initiateCombat(context, timedata, data, graphics);
	}
	
	
    private void initiateCombat(ApplicationContext context,TimeData timedata,LoopHeroGameData data,GameGraphics graphics) {
		graphics.drawCombat(context,this);
		
		timedata.resetElapsedBob();
    	timedata.stop();
    	System.out.println("En combat !");
    	timedata.startCombat();
    	
    	makeCombat(context, timedata, data, graphics);

    	if(isHeroWinner()) {
    		heroDefeat(context, graphics);
    		return;
    	}
    	heroVictory(context, graphics);
    	endCombat(timedata);
    	
    }
    
    private void makeCombat(ApplicationContext context,TimeData timedata,LoopHeroGameData data,GameGraphics graphics) {
    	int attaque = 0;
    	while(!(mob.isDead() || hero.isDead())) {
    		if(timedata.readyToAttack()) {
    			int heroAttack = hero.attack();
    			int mobAttack = mob.attack();
        		System.out.println("Attaque joueur = " +heroAttack+" Attaque mob = "+ mobAttack);
    			mob.takeDamage(heroAttack);
    			hero.takeDamage(mobAttack);
    			
    			graphics.drawDamages(context,heroAttack,mobAttack);
    			graphics.drawHealthInfos(context, hero);
    			attaque++;
    		}
    	}	
    }
    
    private boolean isHeroWinner() {
    	return hero.isDead();
    }
    
    
    private void heroVictory(ApplicationContext context,GameGraphics graphics) {
		hero.addInventory(loot);
		graphics.drawInventory(context, hero);
    }
    
    private void heroDefeat(ApplicationContext context,GameGraphics graphics) {
		System.out.println("LE HERO EST MORT, VIVE LE HERO");
		graphics.drawDeathScreen(context,hero,this);
		context.exit(0);
    }
    
    private void endCombat(TimeData timedata) {
    	cell.removeMob(mob);
    	timedata.stopCombat();
    	timedata.start();
    }
    public Mobs getOpponent() {
    	return mob;
    }

}
