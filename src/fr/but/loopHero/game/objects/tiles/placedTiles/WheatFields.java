package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;

import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.game.objects.tiles.Wasteland;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.mobs.ScareCrow;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class WheatFields extends PlacedTiles {

	private int lastSpawn;
	
	private Mobs spawnedMobs;
	
	public WheatFields() {
		super("WheatFields", new Wasteland(), new Color(140,140,0));
		this.lastSpawn = -1;
		this.spawnedMobs = null;
	}

	@Override
	public void doNewDayEffects(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas,Cell cell) {
		if (spawnedMobs == null || spawnedMobs.isDead()) {
			if((lastSpawn == -1 || lastSpawn >= 4)) {
				lastSpawn = 0;
				Mobs mob = new ScareCrow(cell);
				spawnedMobs = mob;
				cell.addMob(mob);	
			}
			else
				lastSpawn++;
		}
	}
	
	@Override
	public Tile generateNew() {
		return new WheatFields();
	}
	
	@Override
	public void doHeroOnEffect(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas,Cell cell) {
		hero.regenHero(5*LoopHeroGameData.LEVEL);
	}
	
	

}
