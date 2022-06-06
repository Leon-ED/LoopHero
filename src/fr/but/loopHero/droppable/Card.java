package fr.but.loopHero.droppable;

import java.io.Serializable;
import java.util.Objects;

import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.game.objects.tiles.placedTiles.PlacedTiles;
import fr.umlv.zen5.ApplicationContext;

public class Card implements Droppable,Serializable {
	
	private static final long serialVersionUID = -857875196878755747L;
	private final String name;
	private final PlacedTiles cardType;
	
	
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Card [name=");
		builder.append(name);
		builder.append(", cardType=");
		builder.append(cardType);
		builder.append("]");
		return builder.toString();
	}

	public Card(String name, PlacedTiles cardType) {
		this.name = Objects.requireNonNull(name);
		this.cardType = Objects.requireNonNull(cardType);

	}
	
	public String displayName() {
		return name;
	}
	
	public PlacedTiles cardType() {
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

	@Override
	public void draw(ApplicationContext context, int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(ApplicationContext context, int i, int j) {
		// TODO Auto-generated method stub
		
	}
	
	

	
	
	
}
