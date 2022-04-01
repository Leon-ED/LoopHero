package fr.but.loopHero.game.objects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;

import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.mobs.Mobs;

public class Cell {
	private final int i;
	private final int j;
	private Tile type;
	private ArrayList<Mobs> mobs;
	
	public Cell(int i, int j) {
		this.i=Objects.requireNonNull(i);
		this.j=Objects.requireNonNull(j);
		this.mobs = new ArrayList<Mobs>();
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
	
	public Tile type() {return type;} 
	
	public boolean hasMob() {
		return mobs.size() != 0;
	}
	
	public void addMob(Mobs mob) {
		mobs.add(mob);
	}
	public Mobs getFirstMob() {
		return mobs.get(0);
	}
	
	public void removeMob(Mobs mob) {
		mobs.remove(mob);
	}
	@Override
	public int hashCode() {
		return Objects.hash(i, j,type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Cell)) {
			return false;
		}
		Cell cell = (Cell) obj;
		return i == cell.i && j == cell.j && cell.type.equals(type);
	}

		
	
}
