package fr.but.loopHero.game.objects;

import java.nio.file.Path;
import java.util.Objects;

import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.player.Player;

public class SaveDatas {
	
	private final Player hero;
	private final LoopHeroGameData datas;
	
	public SaveDatas(Player hero, LoopHeroGameData datas) {
		this.hero = Objects.requireNonNull(hero);
		this.datas = Objects.requireNonNull(datas);
	}

	
	
	
	private final void saveFiles(Path path) {
		
		
		
		
		
	}
}
