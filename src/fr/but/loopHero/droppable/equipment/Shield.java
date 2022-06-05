package fr.but.loopHero.droppable.equipment;

import java.util.Random;

import fr.but.loopHero.droppable.Rarity;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.umlv.zen5.ApplicationContext;

public class Shield extends Equipement {

	public Shield() {
		super("Bouclier",randomRarity() , LoopHeroGameData.LEVEL, (4*LoopHeroGameData.LEVEL),Modifier.Defense, (4*LoopHeroGameData.LEVEL));
		// TODO Auto-generated constructor stub
	}



	@Override
	public void draw(ApplicationContext context, int i) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void draw(ApplicationContext context, int i, int j) {
		// TODO Auto-generated method stub
		
	}
	
	

	

	
	
	
	
}
