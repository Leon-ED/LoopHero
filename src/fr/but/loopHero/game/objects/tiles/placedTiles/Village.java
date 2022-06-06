package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.game.objects.tiles.Wasteland;
import fr.but.loopHero.game.objects.tiles.placedTiles.PlacedTiles;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.mobs.Ratwolf;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class Village extends PlacedTiles {

	
	public Village() {
		super("Village", new Wasteland(), new Color(112,102,44));
	}

	


	@Override
	public Tile generateNew() {
		return new Village();
	}
	
	
	@Override
	public void doHeroOnEffect(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas,Cell cell) {
		hero.regenHero(15+5*LoopHeroGameData.LEVEL);
		
	}
	
	
	

}
