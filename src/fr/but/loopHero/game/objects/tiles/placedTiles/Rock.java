package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;

import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.tiles.LandScape;
import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class Rock extends PlacedTiles {

	private boolean placedEffectDone = false;
	
	
	public Rock() {
		super("Rock", new LandScape("Rock"), Color.black);
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public void doEffects(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas) {
		
		if (!placedEffectDone) {
			hero.increaseMaxHealth( (int)(hero.getHealths()[1]*0.01));
		}
	}
	
	
	private int checkNeighbors(Board plateau, Cell cell) {
		return 0;
	}

	@Override
	public Tile generateNew() {
		// TODO Auto-generated method stub
		return new Rock();
	}

}
