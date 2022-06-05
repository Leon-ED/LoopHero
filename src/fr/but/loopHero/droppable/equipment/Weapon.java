package fr.but.loopHero.droppable.equipment;

import fr.but.loopHero.droppable.Rarity;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.umlv.zen5.ApplicationContext;

public class Weapon extends Equipement {

	public Weapon(String name) {
		super(name, randomRarity(), LoopHeroGameData.LEVEL, 5*LoopHeroGameData.LEVEL, Modifier.Damage, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(ApplicationContext context, int i, int j) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(ApplicationContext context, int i) {
		// TODO Auto-generated method stub
		
	}

}
