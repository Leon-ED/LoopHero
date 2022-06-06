package fr.but.loopHero.game.objects.tiles.placedTiles;

import java.awt.Color;

import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.game.objects.tiles.Wasteland;
import fr.but.loopHero.mobs.DarkSlime;
import fr.but.loopHero.mobs.Mobs;

public class Oblivion extends PlacedTiles {

	public Oblivion() {
		super("Oblivion", new Wasteland(), new Color(0, 69, 116));
	}

	@Override
	public Tile generateNew() {
		LoopHeroGameData.selectedCell.terminateAllMobs();
		if (LoopHeroGameData.selectedCell.type().generateNew() instanceof Wasteland) {
			Mobs mob = new DarkSlime(LoopHeroGameData.selectedCell);
			LoopHeroGameData.selectedCell.addMob(mob);
		}
		return LoopHeroGameData.selectedCell.originalType().generateNew();

	}

}
