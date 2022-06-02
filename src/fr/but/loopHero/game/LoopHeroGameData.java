package fr.but.loopHero.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.w3c.dom.css.RGBColor;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.tiles.LandScape;
import fr.but.loopHero.game.objects.tiles.Road;
import fr.but.loopHero.game.objects.tiles.RoadSide;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.mobs.Slime;
import fr.but.loopHero.player.Player;

public class LoopHeroGameData {
	
	/*
	 * 
	 * 
	 * METHODES STATIQUES !!!!!!
	 * 
	 * 
	 * 
	 */
	public static int LEVEL = 1;
	public static Color BG_COLOR = Color.WHITE;
	public static Color TXT_COLOR = Color.BLACK;
	
	public static ArrayList<Droppable> MOBS_DROPPABLE_ITEMS = new ArrayList<>();
	public static ArrayList<Mobs> SPAWNABLE_MOBS = new ArrayList<>();
	
	public static List<Card> START_CARDS = List.of(new Card("Grove", new Road("Grove",Color.green.darker())),new Card("Rock", new LandScape("Rock",Color.BLACK)),new Card("Meadow", new LandScape("Meadow",new Color(53,103,23))));
	
	
	public static void generateDroppableItems() {
		MOBS_DROPPABLE_ITEMS.add(new Card("Grove", new Road("Grove")));
		MOBS_DROPPABLE_ITEMS.add(new Card("Rock", new LandScape("Rock",Color.BLACK)));
		MOBS_DROPPABLE_ITEMS.add(new Card("Meadow", new LandScape("Meadow")));
	}
	
	
	public static void generateMobsList() {
		
		SPAWNABLE_MOBS.add(new Slime(null));
	}
	
	
	/*
	 * 
	 * 
	 * METHODES PAS STATIQUES !!!!!!
	 * 
	 * 
	 * 
	 */
	
	
	
	private Cell selectedCell;
	private Card selectedCard;
	
	public LoopHeroGameData() {
		this.selectedCell = null;
		this.selectedCard = null;
	}

	public void selectCell (Cell cell) {
		this.selectedCell = cell;
	}


	public void selectCard(Droppable droppable) {
		try {
			Card card = (Card) droppable;
			this.selectedCard = card;
			
		}catch(ClassCastException e) {
			// Ne devrait pas arriver !
		throw new IllegalArgumentException("seulement entrer une carte en paramètres !");
		}
	
		
	}


	public Cell getSelectedCell() {
		return selectedCell;
	}


	public Card getSelectedCard() {
		// TODO Auto-generated method stub
		return selectedCard;
	}

	
	public boolean canBePlaced() {
		if(selectedCard == null || selectedCell == null)
			return false;
		
		System.out.println(selectedCard.cardType() instanceof LandScape);
		System.out.println(selectedCell.type()  instanceof LandScape);
		
		if((selectedCard.cardType() instanceof Road) && (selectedCell.type()  instanceof Road) )
			return true;

		if((selectedCard.cardType() instanceof LandScape) && (selectedCell.type()  instanceof LandScape) )
			return true;

		if((selectedCard.cardType() instanceof RoadSide) && (selectedCell.type()  instanceof RoadSide) )
			return true;
		
		
		return false;
		
	}
	
	
	
	
}
