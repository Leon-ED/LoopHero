package fr.but.loopHero.droppable.equipment;

import fr.but.loopHero.droppable.Rarity;

public class Ring extends Equipement {

	public Ring(String name, Rarity rarity, int lEVEL, int modInt, Modifier mod, int modInt2, Placement ring) {
		super(name, rarity, lEVEL, modInt2, mod, modInt, ring);

	}

	@Override
	public Equipement makeNew(String name) {
		return super.newRing(name);
	}

}
