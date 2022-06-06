package fr.but.loopHero.game.objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.tiles.CampFire;
import fr.but.loopHero.game.objects.tiles.Wasteland;
import fr.but.loopHero.player.Player;

public class ReadDatas {
	
	
	public ReadDatas() {
		}
	
	public void readPathFromFile(Path path, Board plateau, ArrayList<Cell> listCellsLoop ) throws IOException {
		
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			String line = null;
			int count =0;
			while ((line = reader.readLine()) !=null) {
				count++;
				String[] line_parse = line.split(",");
				int i = Integer.parseInt(line_parse[1]);
				int j = Integer.parseInt(line_parse[2]);
				if (line_parse[0].equals("CampFire")) {
					plateau.getBoardMatrix()[i][j].setType(new CampFire("CampFire"));
					listCellsLoop.add(plateau.getBoardMatrix()[i][j]);
					plateau.getBoardMatrix()[i][j].setIndex(count);
				}
				else {
					plateau.getBoardMatrix()[i][j].setType(new Wasteland());
					listCellsLoop.add(plateau.getBoardMatrix()[i][j]);
					plateau.getBoardMatrix()[i][j].setIndex(count);
				}
			}
		}
	}

}
