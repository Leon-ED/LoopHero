package fr.but.loopHero.game.objects.tiles;

import java.awt.Color;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class CampFire extends Road{

	public CampFire(String name, Color color) {
		super(name, color);
		// TODO Auto-generated constructor stub
	}
	
	public CampFire(String name) {
		super(name, Color.orange);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean mobCanSpawn() {
		return false;
	}

	@Override
	public boolean allowToPlace(Card card) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void doEffects(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas,Cell cell) {
		if(hero.getCurrentCellIndex() == 0) {
			int currentHealth = hero.getHealths()[0];
			int toAdd =  (int) ((int) currentHealth*0.2); 
			hero.regenHero(toAdd);
		}
	}
	

}
