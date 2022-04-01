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
	
}
