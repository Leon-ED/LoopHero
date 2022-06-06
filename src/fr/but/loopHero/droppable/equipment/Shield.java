package fr.but.loopHero.droppable.equipment;

import fr.but.loopHero.game.LoopHeroGameData;

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
		super("Bouclier", randomRarity(), LoopHeroGameData.LEVEL, (4 * LoopHeroGameData.LEVEL), Modifier.Defense,
				(4 * LoopHeroGameData.LEVEL), Placement.Shield);
	}

	@Override
	public Equipement makeNew(String name) {
		return new Shield();
	}

}
