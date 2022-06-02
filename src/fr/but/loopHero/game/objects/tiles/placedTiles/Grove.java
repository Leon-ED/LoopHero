package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.game.objects.tiles.Wasteland;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.mobs.Ratwolf;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class Grove extends PlacedTiles {

	private int lastSpawn;
	
	private final ArrayList<Mobs> spawnedRatwolf;
	
	public Grove() {
		super("Grove", new Wasteland(), Color.GREEN.darker());
		this.lastSpawn = 0;
		this.spawnedRatwolf = new ArrayList<>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doEffects(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas,Cell cell) {
		moveSpawnedRatWolf(plateau);
		if(lastSpawn == 0 || datas.LEVEL >= lastSpawn+2) {
			lastSpawn = datas.LEVEL;
			Mobs rat = new Ratwolf(cell);
			spawnedRatwolf.add(rat);
			cell.addMob(rat);
			System.out.println(cell.hasMob());
			
		}
		
	}
	
	
	private void moveSpawnedRatWolf(Board plateau) {
		for (Mobs mobs : spawnedRatwolf) {
			Cell cell = mobs.getCurrentCell();
			int index = cell.getIndex();
			int max = plateau.getlistCellsLoop().size()-1;
			
			Random rand = new Random();
			int rand_number = rand.nextInt(-1, 2);
			System.out.println(rand_number+" eee");
			if(index+rand_number < 0 || rand_number+index >= max || plateau.getlistCellsLoop().get(rand_number+index).hasMob())
				return;
			
			cell.removeMob(mobs);
			plateau.getlistCellsLoop().get(rand_number+index).addMob(mobs);
			
			
			
		}
	}

	@Override
	public Tile generateNew() {
		// TODO Auto-generated method stub
		return new Grove();
	}
	
	
	
	

}
