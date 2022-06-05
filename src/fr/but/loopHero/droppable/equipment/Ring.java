package fr.but.loopHero.droppable.equipment;

import fr.but.loopHero.droppable.Rarity;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.umlv.zen5.ApplicationContext;

public class Ring extends Equipement {


	
	public Ring(String name, Rarity rarity, int lEVEL, int modInt, Modifier mod, int modInt2, Placement ring) {
		super(name, rarity, lEVEL, modInt2, mod, modInt, ring);

	}

	@Override
	public Equipement makeNew(String name) {
		// TODO Auto-generated method stub
		return super.newRing(name);
	}
	







	

}

