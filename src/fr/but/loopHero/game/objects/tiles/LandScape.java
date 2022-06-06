package fr.but.loopHero.game.objects.tiles;

import java.awt.Color;

import fr.but.loopHero.droppable.Card;

public class LandScape extends Tile {

	public LandScape(String name) {
		super(name);
	}

	public LandScape(String name, Color color) {
		super(name, color);
	}

	@Override
	public boolean allowToPlace(Card card) {
		if (card == null)
			return false;
		return card.cardType().getParentTile().getClass().equals(this.getClass());
	}

	@Override
	public Tile generateNew() {
		return new LandScape("empty", Color.DARK_GRAY);
	}
}
