package fr.but.loopHero.game.objects.tiles;

import java.awt.Color;
import java.util.Objects;

public class Tile {
	private final String name;
	private final Color color;
	
	public Tile(String name, Color color) {
		this.name = Objects.requireNonNull(name);
		this.color = Objects.requireNonNull(color);
		
	}
	
	public Tile(String name) {
		this.name = Objects.requireNonNull(name);
		this.color = Color.gray;
	}
	
	public String name() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
	
}
