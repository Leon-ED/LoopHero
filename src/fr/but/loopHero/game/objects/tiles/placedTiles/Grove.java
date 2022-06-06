package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;
import java.io.Serializable;

import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.game.objects.tiles.Wasteland;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.mobs.Ratwolf;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class Grove extends PlacedTiles implements Serializable {
	private static final long serialVersionUID = -857875196878755747L;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Grove [lastSpawn=");
		builder.append(lastSpawn);
		builder.append(", spawnedMobs=");
		builder.append(spawnedMobs);
		builder.append("]");
		return builder.toString();
	}

	private int lastSpawn;

	private Mobs spawnedMobs;

	public Grove() {
		super("Grove", new Wasteland(), Color.GREEN.darker());
		this.lastSpawn = -1;
		this.spawnedMobs = null;
	}

	public Grove(String name, Tile parentTile, Color color, int lastSpawn, Mobs spawnedMobs) {
		super(name, parentTile, color);
		this.lastSpawn = lastSpawn;
		this.spawnedMobs = spawnedMobs;
	}

	@Override
	public void doNewDayEffects(ApplicationContext context, Player hero, Board plateau, LoopHeroGameData datas,
			Cell cell) {
		if (spawnedMobs == null || spawnedMobs.isDead()) {
			if ((lastSpawn == -1 || lastSpawn >= 2)) {
				lastSpawn = 0;
				Mobs mob = new Ratwolf(cell);
				spawnedMobs = mob;
				cell.addMob(mob);
			} else
				lastSpawn++;
		}

	}



	@Override
	public Tile generateNew() {
		return new Grove();
	}

}
