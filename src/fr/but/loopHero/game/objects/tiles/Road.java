package fr.but.loopHero.game.objects.tiles;

import java.awt.Color;

public class Road extends Tile{

	public Road(String name, Color color) {
		super(name, color);
	}
	
	public Road(String name) {
		super(name);
	}

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
		if (!(obj instanceof Road))
			return false;
		return true;
	}

}
