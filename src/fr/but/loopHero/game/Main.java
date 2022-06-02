package fr.but.loopHero.game;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.game.graphics.GameGraphics;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.tiles.LandScape;
import fr.but.loopHero.game.objects.tiles.Road;
import fr.but.loopHero.game.objects.tiles.RoadSide;
import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.mobs.Slime;
import fr.but.loopHero.player.Player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.ArrayList;
import java.util.Objects;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;



public class Main {
    private final GameGraphics loopHeroGraphics  = new GameGraphics(50,50,50,50,60);
    private final TimeData loopHeroTimeData = new TimeData();
    private final Board plateau = new Board(12,21);
    private final Player hero = new Player();
    private final LoopHeroGameData gameData = new LoopHeroGameData();
    private final static int USER_ACTION_DELAY = 1_500;

    
    
    private void loopHero(ApplicationContext context) {
    	LoopHeroGameData.generateDroppableItems();
        plateau.fill(); 
        plateau.createLoop(34);
        loopHeroGraphics.drawBoard(plateau, context);
        //loopHeroGraphics.drawInventory(context, hero);

        while (true) {
            loopHeroGraphics.drawInventory(context, hero);
        	loopHeroGraphics.drawLevel(context);
        //	loopHeroGraphics.drawOutlineLoop(plateau, context);
        	
        	if(Combat.combatAvailable(plateau.getlistCellsLoop().get(hero.getCurrentCellIndex()))) {
        		loopHeroGraphics.drawHero(plateau, context, hero, loopHeroTimeData,hero.getCurrentCellIndex());
        		Combat.startCombat(context, hero, loopHeroTimeData,plateau.getlistCellsLoop().get(hero.getCurrentCellIndex()),gameData, loopHeroGraphics, plateau);
        	}
        	loopHeroGraphics.drawHealthInfos(context, hero);
        	moveHeroAndDraw(context);
        	if (loopHeroTimeData.isDayPased()) 
        		plateau.spawnEntity();	
        	loopHeroGraphics.drawMobs(context, plateau);
        	//loopHeroGraphics.drawHero(plateau, context, hero, loopHeroTimeData,hero.getCurrentCellIndex());
        	doEvent(context);
        	

        }
        
    }
    

    
    private void doKeyAction(ApplicationContext context, Event event) {
		switch (event.getKey()) {
		case SPACE -> {
			System.out.println("Fin du jeu");
			context.exit(0);
			throw new AssertionError("ne devrait pas arriver");
		}
		case S -> {loopHeroTimeData.stop(); System.out.println("Jeux mis en pause");}
		case D -> {loopHeroTimeData.start(); System.out.println("Reprise du jeux");}
		default -> System.out.println("Touche inactive : " + event.getKey());
		}
	}
    
    private  void moveHeroAndDraw(ApplicationContext context) {
    	int pos;
    	if (loopHeroTimeData.elapsedBob() >= TimeData.HERO_DELAY) {
			pos = hero.addCurrentNumOfCell();
    		loopHeroTimeData.resetElapsedBob();
    	}else {
    		pos = hero.getCurrentCellIndex();
    	}
    	
		loopHeroGraphics.drawHero(plateau, context, hero, loopHeroTimeData,pos);
		
		if (pos <= 0 ) 
			pos = plateau.getlistCellsLoop().size();
		System.out.println("ok");
		loopHeroGraphics.drawOneCell(plateau, context, plateau.getlistCellsLoop().get(pos-1).i(),plateau.getlistCellsLoop().get(pos-1).j());
		System.out.println("ok");

    	
	}
    
    
    

    
    private void doEvent(ApplicationContext context) {
    	Event event = context.pollOrWaitEvent(USER_ACTION_DELAY);
		if (event == null) { // no event
			return;
		}
		switch (event.getAction()) {
		case KEY_PRESSED:
			doKeyAction(context, event);
			break;
		case POINTER_DOWN:
			if (loopHeroTimeData.stopped()) {
				doMouseAction(context, event);
			}
			break;
		}
	
    	
    	
    	
    	
    }
    
	private void doMouseAction(ApplicationContext context, Event event) {
		Point2D.Float location = event.getLocation();
		//System.out.println("X " + location.x+" y "+location.y);
		int i = loopHeroGraphics.lineFromY(location.y);
		int j = loopHeroGraphics.columnFromX(location.x);
	//	System.out.println("i "+loopHeroGraphics.lineFromY(location.y)+" j "+loopHeroGraphics.columnFromX(location.x));
		
		// Si la sélection est invalide
		if(i< 0 || j < 0) 
			return;

		if(i < 12 && j < 21) {
			// Selection d'un Cell dans le plateau de jeu
			Cell selectedCell = gameData.getSelectedCell();

			
			if(selectedCell != null) {
				loopHeroGraphics.drawOneCell(plateau, context, selectedCell.i() , selectedCell.j());
				//System.out.println("DDEEES");
			}
			gameData.selectCell(plateau.getBoardMatrix()[i][j]);
			loopHeroGraphics.drawSelection(plateau, context, i, j,Color.red);
			
			if(gameData.getSelectedCard() != null)
				placeCard(context);			
			return;
		}
		
		if(i == 12) {
			// En dehors de la liste
			if(j >= hero.getInventory().size() )
				return;
			Card selectedCard = gameData.getSelectedCard();

			if(selectedCard != null) {
				System.out.println("Une seule selection possible");
				return;
			}
			gameData.selectCard(hero.getInventory().get(j));
			loopHeroGraphics.drawSelection(plateau, context, i, j,Color.RED);
			return;
		}
		
		

	}
    
    
	private void placeCard(ApplicationContext context) {
		//System.out.println("PLACEEEEJINOZERIHOZOHI");
		if(gameData.getSelectedCard() == null || gameData.getSelectedCell() == null)
			
			return;

		// Un carte et une cellule sont choisies, on vérifie si elle peut être posée
			
		
		if(!gameData.canBePlaced()) {
			Cell selectedCell = gameData.getSelectedCell();
			loopHeroGraphics.drawOneCell(plateau, context, selectedCell.i() , selectedCell.j());
			gameData.selectCell(null);
			return;
		}

		
	//	System.out.println("PLACEEEEJINOZERIHOZOHI");
		
		Cell selectedCell = gameData.getSelectedCell();
		Card selectedCard = gameData.getSelectedCard();
		int i = selectedCell.i();
		int j = selectedCell.j();
		
		if((selectedCell.type()  instanceof Road)) {
			plateau.getBoardMatrix()[i][j].setType(new Road(selectedCard.displayName(), selectedCard.cardType().getColor()));
		}

		if((selectedCell.type()  instanceof LandScape)) {
			plateau.getBoardMatrix()[i][j].setType(new LandScape(selectedCard.displayName(), selectedCard.cardType().getColor()));
		}

		if((selectedCell.type()  instanceof RoadSide)) {
			plateau.getBoardMatrix()[i][j].setType(new RoadSide(selectedCard.displayName(), selectedCard.cardType().getColor()));
		}
		
		loopHeroGraphics.drawOneCell(plateau, context, i, j);
		hero.deleteFromInventory(selectedCard);
		
		//On supprime la sélection du joueur
		gameData.selectCard(null);
		gameData.selectCell(null);
		
	

		
		
	}
	
	
    public static void main(String[] args) {
        Main controller = new Main();
        Application.run(Color.WHITE,controller::loopHero);
        
    }

}