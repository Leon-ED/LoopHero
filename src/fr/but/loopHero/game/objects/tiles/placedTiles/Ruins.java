package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;

import fr.but.loopHero.droppable.Ressource;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.game.objects.tiles.Wasteland;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.mobs.ScorchWorm;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class Ruins extends PlacedTiles {

	private int lastSpawn;
	
	private Mobs spawnedMobs;
	
	public Ruins() {
		super("Ruins", new Wasteland(), new Color(45,20,69));
		this.lastSpawn = -1;
		this.spawnedMobs = null;
	}

	@Override
	public void doNewDayEffects(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas,Cell cell) {
		if (spawnedMobs == null || spawnedMobs.isDead()) {
			if((lastSpawn == -1 || lastSpawn >= 2)) {
				lastSpawn = 0;
				Mobs mob = new ScorchWorm(cell);
				spawnedMobs = mob;
				cell.addMob(mob);
				
			}
			else
				lastSpawn++;
		}
		
	}
	


	@Override
	public Tile generateNew() {
		return new Ruins();
	}
	
	@Override
	public void doHeroOnEffect(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas,Cell cell) {
		hero.addRessource(new Ressource("stable_branches"));
	}
	
	

}
