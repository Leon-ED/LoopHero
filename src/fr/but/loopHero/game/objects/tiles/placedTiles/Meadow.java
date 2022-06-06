package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;

import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.tiles.LandScape;
import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class Meadow extends PlacedTiles {

	// Peut a changer pour éviter un overflow !!!!!!!

	public Meadow() {
		super("Meadow", new LandScape("Meadow"), new Color(53, 103, 23));

	}

	@Override
	public void doNewDayEffects(ApplicationContext context, Player hero, Board plateau, LoopHeroGameData datas,
			Cell cell) {
		hero.regenHero(2);
	}

	@Override
	public Tile generateNew() {
		// TODO Auto-generated method stub
		return new Meadow();
	}

}
