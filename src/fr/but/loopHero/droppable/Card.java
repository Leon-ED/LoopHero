package fr.but.loopHero.droppable;

import java.util.Objects;

import fr.but.loopHero.game.objects.tiles.Tile;

public class Card implements Droppable {
	
	private final String name;
	private final Tile cardType;
	
	
	
	
	public Card(String name, Tile cardType) {
		this.name = Objects.requireNonNull(name);
		this.cardType = Objects.requireNonNull(cardType);

	}
	
	public String displayName() {
		return name;
	}
	
	public Tile cardType() {
		return cardType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cardType, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Card))
			return false;
		Card other = (Card) obj;
		return Objects.equals(cardType, other.cardType) && Objects.equals(name, other.name);
	}
	
	

	
	
	
}
