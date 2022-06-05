package fr.but.loopHero.droppable.equipment;

import java.util.Random;

import fr.but.loopHero.droppable.Rarity;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.umlv.zen5.ApplicationContext;

public class Shield extends Equipement {

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
		if (!(obj instanceof Shield))
			return false;
		return true;
	}



	public Shield() {
		super("Bouclier",randomRarity() , LoopHeroGameData.LEVEL, (4*LoopHeroGameData.LEVEL),Modifier.Defense, (4*LoopHeroGameData.LEVEL),Placement.Shield);
		// TODO Auto-generated constructor stub
	}





	@Override
	public Equipement makeNew(String name) {
		// TODO Auto-generated method stub
		return new Shield();
	}
	
	

	

	
	
	
	
}
