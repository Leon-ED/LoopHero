package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
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
	public void doNewDayEffects(ApplicationContext context, Player hero, Board plateau,LoopHeroGameData datas,Cell cell) {
		//moveSpawnedRatWolf(plateau);
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
			
			cell.removeMob(mobs);
			System.out.println(cell.hasMob() +" ancienne");
			
			int index = cell.getIndex();
			int max = plateau.getlistCellsLoop().size()-1;
			
			Random rand = new Random();
			int rand_number = rand.nextInt(3);
			List<Integer> deplacements = List.of(-1,0,1);
			rand_number = deplacements.get(rand_number);

			if(index+rand_number < 0 || rand_number+index >= max || plateau.getlistCellsLoop().get(rand_number+index).hasMob())
				return;
			
			
			plateau.getlistCellsLoop().get(rand_number+index).addMob(mobs);
			System.out.println(plateau.getlistCellsLoop().get(rand_number+index).hasMob() +" nouvelle");
			
			mobs.changeCell(plateau.getlistCellsLoop().get(rand_number+index));
			
			
			
		}
	}

	@Override
	public Tile generateNew() {
		// TODO Auto-generated method stub
		return new Grove();
	}
	
	
	
	

}
