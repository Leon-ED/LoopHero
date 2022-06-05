package fr.but.loopHero.droppable.equipment;

import fr.but.loopHero.droppable.Rarity;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.umlv.zen5.ApplicationContext;

public class Armor extends Equipement {

	public Armor() {
		super("Armure",randomRarity() , LoopHeroGameData.LEVEL, (int)((20*LoopHeroGameData.LEVEL)),Modifier.MaximumHP, (int)((20*LoopHeroGameData.LEVEL)),Placement.Armor); // calcul pas bon
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
