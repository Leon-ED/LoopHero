package fr.but.loopHero.droppable.equipment;

import fr.but.loopHero.game.LoopHeroGameData;

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
		super("Armure", randomRarity(), LoopHeroGameData.LEVEL, (int) ((20 * LoopHeroGameData.LEVEL)),
				Modifier.MaximumHP, (int) ((20 * LoopHeroGameData.LEVEL)), Placement.Armor);
	}

	@Override
	public Equipement makeNew(String name) {
		return new Armor();
	}

}
