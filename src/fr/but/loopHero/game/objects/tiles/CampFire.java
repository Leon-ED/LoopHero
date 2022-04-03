package fr.but.loopHero.game.objects.tiles;

import java.awt.Color;

public class CampFire extends Road {

	public CampFire(String name, Color color) {
		super(name, color);
		// TODO Auto-generated constructor stub
	}
	
	public CampFire(String name) {
		super(name, Color.orange);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean mobCanSpawn() {
		return false;
	}
}
