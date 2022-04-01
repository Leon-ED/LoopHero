package fr.but.loopHero.game;

import java.awt.Color;
import java.util.ArrayList;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.game.objects.tiles.LandScape;
import fr.but.loopHero.game.objects.tiles.Road;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.mobs.Slime;

public class LoopHeroGameData {
	public static int LEVEL = 1;
	public static Color BG_COLOR = Color.WHITE;
	public static Color TXT_COLOR = Color.BLACK;
	
	public static ArrayList<Droppable> MOBS_DROPPABLE_ITEMS = new ArrayList<>();
	public static ArrayList<Mobs> SPAWNABLE_MOBS = new ArrayList<>();
	
	
	
	public static void generateDroppableItems() {
		MOBS_DROPPABLE_ITEMS.add(new Card("Grove", new Road("Grove")));
		MOBS_DROPPABLE_ITEMS.add(new Card("Rock", new LandScape("Grove")));
		MOBS_DROPPABLE_ITEMS.add(new Card("Meadow", new LandScape("Meadow")));
	}
	
	
	public static void generateMobsList() {
		
		SPAWNABLE_MOBS.add(new Slime(null));
	}
	
	
	
	
	
	
	
	
	
	public LoopHeroGameData() {
		// TODO Auto-generated constructor stub
	}


}
