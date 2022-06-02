package fr.but.loopHero.game.objects.tiles;

import java.awt.Color;

import fr.but.loopHero.droppable.Card;

public class RoadSide extends Tile{

	public RoadSide(String name) {
		super(name);
	}

	public RoadSide(String name, Color color) {
		super(name, color);
	}
	
	
	@Override
	public boolean allowToPlace(Card card) {
		return card.cardType().getParentTile().getClass().equals(this.getClass());
	}
}
