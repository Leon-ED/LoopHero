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

	private int lastSpawn;
	
	private Mobs spawnedMobs;
	
	public Village() {
		super("Grove", new Wasteland(), Color.GREEN.darker());
		this.lastSpawn = -1;
		this.spawnedMobs = null;
	}

	@Override
	public void doNewDayEffects(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas,Cell cell) {
		//moveSpawnedRatWolf(plateau);
		if (spawnedMobs == null || spawnedMobs.isDead()) {
			if((lastSpawn == -1 || lastSpawn >= 1)) {
				lastSpawn = 0;
				Mobs mob = new Ratwolf(cell);
				spawnedMobs = mob;
				cell.addMob(mob);
				//System.out.println(cell.hasMob());	
			}
			else
				lastSpawn++;
		}
		
	}
	
	


	@Override
	public Tile generateNew() {
		// TODO Auto-generated method stub
		return new Village();
	}
	
	
	
	

}
