package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;
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
	
	private Mobs spawnedMobs;
	
	public Grove() {
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
	
	
//	private void moveSpawnedRatWolf(Board plateau) {
//		for (Mobs mobs : spawnedMobs) {
//			Cell cell = mobs.getCurrentCell();
//			
//			cell.removeMob(mobs);
//			System.out.println(cell.hasMob() +" ancienne");
//			
//			int index = cell.getIndex();
//			int max = plateau.getlistCellsLoop().size()-1;
//			
//			Random rand = new Random();
//			int rand_number = rand.nextInt(3);
//			List<Integer> deplacements = List.of(-1,0,1);
//			rand_number = deplacements.get(rand_number);
//
//			if(index+rand_number < 0 || rand_number+index >= max || plateau.getlistCellsLoop().get(rand_number+index).hasMob())
//				return;
//			
//			
//			plateau.getlistCellsLoop().get(rand_number+index).addMob(mobs);
//			System.out.println(plateau.getlistCellsLoop().get(rand_number+index).hasMob() +" nouvelle");
//			
//			mobs.changeCell(plateau.getlistCellsLoop().get(rand_number+index));
//			
//			
//			
//		}
//	}

	@Override
	public Tile generateNew() {
		// TODO Auto-generated method stub
		return new Grove();
	}
	
	
	
	

}
