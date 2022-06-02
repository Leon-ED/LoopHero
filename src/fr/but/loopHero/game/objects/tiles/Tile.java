package fr.but.loopHero.game.objects.tiles;

import java.awt.Color;
import java.util.Objects;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public abstract class Tile {
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
		return name.toUpperCase();
	}
	
	public Color getColor() {
		return color;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Tile)) {
			return false;
		}
		Tile other = (Tile) obj;
		return name.equalsIgnoreCase(other.name);
	}
	
	public boolean mobCanSpawn() {
		return true;
	}
	
	public boolean allowToPlace(Card card) {
		
		return false;
	}
	
	public void doEffects(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas,Cell cell) {
		
	}
	
}
