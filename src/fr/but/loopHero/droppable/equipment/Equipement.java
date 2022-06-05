package fr.but.loopHero.droppable.equipment;

import java.awt.Color;
import java.util.Objects;

import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.droppable.Rarity;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.player.CombatEffects;
import fr.umlv.zen5.ApplicationContext;

public abstract class Equipement implements Droppable {
	
	@Override
	public int hashCode() {
		return Objects.hash(InventoryPlacement, level, modifierInteger, name, providedModifier, rarity, value);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Equipement))
			return false;
		Equipement other = (Equipement) obj;
		return InventoryPlacement == other.InventoryPlacement && level == other.level
				&& modifierInteger == other.modifierInteger && Objects.equals(name, other.name)
				&& providedModifier == other.providedModifier && rarity == other.rarity && value == other.value;
	}



	private final String name;
	private final Rarity rarity;
	private final int level;
	private final int value;
	
	private final Placement InventoryPlacement;
	private final Modifier providedModifier;
	
	private final int modifierInteger;
	
	
	public static Rarity randomRarity() {
		//return Rarity.Gris;
		double d = Math.random() * 100;
		if ((d -= 35) < 0) return Rarity.Gris;
		if ((d -= 30) < 0) return Rarity.Bleu;
		if ((d -= 20) < 0) return Rarity.Jaune;
		if(LoopHeroGameData.LEVEL >= 3)
			if ((d -= 15) < 0) return Rarity.Orange;
		return Rarity.Gris;
	}
	
	
	
	public Equipement(String name, Rarity rarity,int level, int value,Modifier providedModifier,int modifierInteger,Placement inventoryPlacement) {
		this.name = Objects.requireNonNull(name);
		this.rarity = Objects.requireNonNull(rarity);
		this.level = level;
		this.value = value;
		this.providedModifier = Objects.requireNonNull(providedModifier);
		this.modifierInteger = modifierInteger;
		this.InventoryPlacement = Objects.requireNonNull(inventoryPlacement);
	}
	
	
	public abstract Equipement makeNew(String name);
	
	@Override
	public abstract void draw(ApplicationContext context, int i);

	@Override
	public String displayName() {
		// TODO Auto-generated method stub
		return name;
	}



	public Modifier getModifier() {
		// TODO Auto-generated method stub
		return providedModifier;
	}



	public int getModifierValue() {
		// TODO Auto-generated method stub
		return modifierInteger;
	}
	
	public Color getColor() {
		switch (rarity) {
		case Gris -> {return Color.gray;}
		case Bleu -> {return Color.CYAN;}
		case Orange ->{return Color.orange;}
		case Jaune ->{return Color.yellow;}
		
		default ->
		throw new IllegalArgumentException("Unexpected value: " + rarity);
		}
	}
	
	public int getValue() {
		return value;
	}



	public Placement placement() {
		// TODO Auto-generated method stub
		return InventoryPlacement;
	}
	
}
