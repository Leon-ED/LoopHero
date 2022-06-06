package fr.but.loopHero.game.objects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.but.loopHero.game.objects.tiles.LandScape;
import fr.but.loopHero.game.objects.tiles.Road;
import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.mobs.Mobs;

public class Cell {
	private final int i;
	private final int j;
	private int index;
	private Tile type;
	private ArrayList<Mobs> mobs;
	
	public Cell(int i, int j) {
		this.i=Objects.requireNonNull(i);
		this.j=Objects.requireNonNull(j);
		this.mobs = new ArrayList<Mobs>();
		this.type = new LandScape("empty",Color.DARK_GRAY);
		this.index = -1;
	}
	
	public Cell(int i, int j,int index) {
		this.i=Objects.requireNonNull(i);
		this.j=Objects.requireNonNull(j);
		this.mobs = new ArrayList<Mobs>();
		this.type = new LandScape("empty",Color.DARK_GRAY);
		this.index = index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	public int getIndex() {
		return index;
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

	public ArrayList<Cell> getAdjacentsCells(boolean adj, Board plateau,int i, int j) {
		List<Tuple> listIndex;
		if(adj) {
			 listIndex = Tuple.getAdjactentsPos();
		}else {
			listIndex = Tuple.getNeighboursPos();
		}
		Cell[][] boardMatrix = plateau.getBoardMatrix();
		ArrayList<Cell> liste = new ArrayList<>();
		for (Tuple tuple : listIndex) {
			int x = tuple.i();
			int y = tuple.j();
			if (!plateau.isOutOfBounds(x+i, y+j)) {
				Cell cell = boardMatrix[x+i][y+j];
				if(cell.type instanceof Road) {
					liste.add(cell);
				}
			
			
			}
		}
		return liste;
	}

		
	
}
