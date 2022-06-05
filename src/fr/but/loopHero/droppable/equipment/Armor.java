package fr.but.loopHero.droppable.equipment;

import fr.but.loopHero.droppable.Rarity;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.umlv.zen5.ApplicationContext;

public class Armor extends Equipement {

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Armor))
			return false;
		return true;
	}

	public Armor() {
		super("Armure",randomRarity() , LoopHeroGameData.LEVEL, (int)((20*LoopHeroGameData.LEVEL)),Modifier.MaximumHP, (int)((20*LoopHeroGameData.LEVEL)),Placement.Armor); // calcul pas bon
		// TODO Auto-generated constructor stub
	}



	@Override
	public Equipement makeNew(String name) {
		 return new Armor();
	}

}
