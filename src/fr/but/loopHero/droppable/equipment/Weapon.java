package fr.but.loopHero.droppable.equipment;

import java.util.Objects;

import fr.but.loopHero.game.LoopHeroGameData;

public class Weapon extends Equipement {

	private final int minAttack;
	private final int maxAttack;

	public Weapon(String name) {
		super(name, randomRarity(), LoopHeroGameData.LEVEL, 5 * LoopHeroGameData.LEVEL, Modifier.Damage, 0,
				Placement.Weapon);
		this.minAttack = 4 * LoopHeroGameData.LEVEL;
		this.maxAttack = 6 * LoopHeroGameData.LEVEL;
	}

	public int[] weaponModifierInteger() {
		int[] vies = new int[2];
		vies[0] = minAttack;
		vies[1] = maxAttack;
		return vies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(maxAttack, minAttack);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Weapon))
			return false;
		Weapon other = (Weapon) obj;
		return maxAttack == other.maxAttack && minAttack == other.minAttack;
	}

	@Override
	public Equipement makeNew(String name) {

		return new Weapon(name);
	}

}
