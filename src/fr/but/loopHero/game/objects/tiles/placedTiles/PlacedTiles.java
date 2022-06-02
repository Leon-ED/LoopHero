package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;
import java.util.Objects;

import fr.but.loopHero.game.objects.tiles.Tile;

public abstract class PlacedTiles extends Tile {
	
	private final Tile parentTile;
	
	public PlacedTiles(String name,Tile parentTile, Color color) {
		super(name,color);
		this.parentTile = Objects.requireNonNull(parentTile);
		
	}
	
	public Tile getParentTile() {
		return parentTile;
		
	}
	
	public abstract Tile generateNew();
	
	
	

}
