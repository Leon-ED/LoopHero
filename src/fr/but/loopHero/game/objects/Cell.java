package fr.but.loopHero.game.objects;

import java.awt.Color;
import java.util.Objects;

import fr.but.loopHero.game.objects.tiles.Tile;

public class Cell {
	private final int i;
	private final int j;
	private Tile type;
	
	public Cell(int i, int j) {
		this.i=Objects.requireNonNull(i);
		this.j=Objects.requireNonNull(j);
		this.type = new Tile("empty");
	}
	@Override
	public String toString() {
		return i+","+j+" type:"+type;
	}
	
	public void setType(Tile newType) {
		type = newType;
	}
	
	public int i() {
		return i;
	}
	
	public int j() {
		return j;
	}
	
	public Color getColor() {
		return type.getColor();
	}
	
	public boolean isEmpty() {
		return type.name().equalsIgnoreCase("empty");
	}
	
}
