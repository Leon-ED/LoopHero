package fr.but.loopHero.game;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.game.graphics.GameGraphics;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.player.Player;

import java.awt.Color;
import java.awt.geom.Point2D;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;



public class Main {

    private final TimeData loopHeroTimeData = new TimeData();
    private final Board plateau = new Board(12,21);
    private final GameGraphics loopHeroGraphics  = new GameGraphics(50,50,1920,1080,60,plateau);
    private final Player hero = new Player();
    private final LoopHeroGameData gameData = new LoopHeroGameData();
    

    
    
    private void loopHero(ApplicationContext context) {
    	//Boucle principale du jeu
    	
    	LoopHeroGameData.generateDroppableItems();
        plateau.fill(); 
        plateau.createLoop(34);
        loopHeroGraphics.drawBoard(plateau, context);
        loopHeroGraphics.drawStaticInventory(context);
        //loopHeroGraphics.drawInventory(context, hero);

        while (true) {
        	if(! loopHeroTimeData.stopped()) {
	        	loopHeroGraphics.drawHeroInformations(context, hero);
	            loopHeroGraphics.drawInventory(context, hero);
	        	loopHeroGraphics.drawBar(context,350, loopHeroTimeData.timeFraction(),0,0,Color.GREEN,10);
        	}
        	
        	Cell heroCurrentCell = plateau.getlistCellsLoop().get(hero.getCurrentCellIndex());
        	
        	if(Combat.combatAvailable(heroCurrentCell) && ! loopHeroTimeData.stopped() ) {
        		loopHeroGraphics.drawHero(plateau, context, hero);
        		new Combat(hero, heroCurrentCell, context, loopHeroTimeData, gameData, loopHeroGraphics);
        		loopHeroGraphics.drawBoard(plateau, context);
        
        	}
        	
        	moveHeroAndDraw(context);
        	if (loopHeroTimeData.isDayPased()) {
        		gameData.doNewDayEffects(context,hero,plateau);
        		

        		
        		
        	}
        	plateau.getlistCellsLoop().get(0).type().doNewDayEffects(context, hero, plateau, gameData, null);
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
		case RIGHT ->  { loopHeroGraphics.drawSpeedIndicator(context,loopHeroTimeData.accelerateTime()); System.out.println("Accélération du temps");}
		case LEFT ->  { loopHeroGraphics.drawSpeedIndicator(context,loopHeroTimeData.decelerateTime()); System.out.println("Ralentissement du temps");}
		
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
    	
		loopHeroGraphics.drawHero(plateau, context, hero);
		
		if (pos <= 0 ) 
			pos = plateau.getlistCellsLoop().size();
		loopHeroGraphics.drawOneCell(plateau, context, plateau.getlistCellsLoop().get(pos-1).i(),plateau.getlistCellsLoop().get(pos-1).j());
		

    	
	}
    
    
    

    
    private void doEvent(ApplicationContext context) {
    	Event event = context.pollOrWaitEvent(TimeData.HERO_DELAY);
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
		System.out.println("X " + location.x+" y "+location.y);
		int i = loopHeroGraphics.lineFromY(location.y);
		int j = loopHeroGraphics.columnFromX(location.x);
		System.out.println("i "+loopHeroGraphics.lineFromY(location.y)+" j "+loopHeroGraphics.columnFromX(location.x));
		
		// Si la sélection est invalide
		if(i< 0 || j < 0) 
			return;

		if(i < 12 && j < 21) {
			// Selection d'un Cell dans le plateau de jeu
			Cell selectedCell = gameData.getSelectedCell();

			
			if(selectedCell != null) {
				//System.out.println("ok");
				loopHeroGraphics.drawOneCell(plateau, context, selectedCell.i() , selectedCell.j());
				
			}
			gameData.selectCell(plateau.getBoardMatrix()[i][j]);
			loopHeroGraphics.drawSelection(context, i, j,Color.red);
			
			if(gameData.getSelectedCard() != null) { 
				placeCard(context);	
				}
			
			return;
		}
		if(i == 12) {
			// En dehors de la liste
			if(j >= hero.getInventory().get(0).size() )
				return;
			Card selectedCard = gameData.getSelectedCard();

			if(selectedCard != null) {
				System.out.println("Une seule selection possible");
				return;
			}
			gameData.selectCard(hero.getInventory().get(0).get(j));
			loopHeroGraphics.drawSelection(context, i, j,Color.RED);
			return;
		}

		
		//Selection dans l'equipement disponible :
		if((i>= 4 && i<= 6) && (j>= 23 && j<=26)) {
			int offsetI = 4;
			int offsetJ = 23;
			int index = (i-offsetI)*4+(j-offsetJ);
			
			if(gameData.selectEquipement(hero.getEquipementInventory(),index)){ //Equipement selectionne avec succes
				loopHeroGraphics.drawSelection(context, i, j,Color.RED);
				System.out.println(index);
				System.out.println("Selected : "+gameData.getSelectedEquipement().displayName());
				return;
			}
			//Echec de selection
			System.out.println("Selection invalide");
			System.out.println(index);
			return;
			
		}
		// Si on clique dans les cases pour s'equiper ET qu'on a deja un item selectionne		
		if(((i>= 0 && i<= 2) && (j>= 23 && j<=26)) && gameData.getSelectedEquipement() != null) { 
			int offsetJ = 23;
			int offsetI = 0;
			int index = (i-offsetI)*4+(j-offsetJ);
			gameData.selectEquipementPlacement(index);
			System.out.println(gameData.getSelectedInventoryPlacement());
			if(gameData.getSelectedInventoryPlacement() != null) {
				placeEquipement(context,i,j);
		
			}
		}
		
		

	}
    
	private void placeEquipement(ApplicationContext context,int i,int j) {
		if(gameData.getSelectedEquipement() == null || gameData.getSelectedInventoryPlacement() == null)
			return;
		System.out.println(gameData.getSelectedInventoryPlacement().toString());
		if(!gameData.canPlaceEquipement()) {
			System.out.println("non");
			return;
		}
		System.out.println("oui");
		loopHeroGraphics.drawEquipement(context, gameData.getSelectedEquipement(), i, j);
		hero.equipEquipement(gameData.getSelectedEquipement());
		loopHeroGraphics.drawHeroInformations(context, hero);
		loopHeroGraphics.drawInventory(context, hero);
		
		
		
		gameData.selectEquipementPlacement(-1);
		gameData.selectEquipementPlacement(-1);
		
	}
    
	private void placeCard(ApplicationContext context) {
		//System.out.println("PLACEEEEJINOZERIHOZOHI");
		if(gameData.getSelectedCard() == null || gameData.getSelectedCell() == null)
			
			return;
			
		
		if(!gameData.canBePlaced()) {
			Cell selectedCell = gameData.getSelectedCell();
			loopHeroGraphics.drawOneCell(plateau, context, selectedCell.i() , selectedCell.j());
			gameData.selectCell(null);
			return;
		}


		
		Cell selectedCell = gameData.getSelectedCell();
		Card selectedCard = gameData.getSelectedCard();
		int i = selectedCell.i();
		int j = selectedCell.j();
		
		plateau.getBoardMatrix()[i][j].setType(selectedCard.cardType().generateNew());		
		gameData.getSelectedCard().cardType().doEffects(context, hero,plateau,gameData); // On applique l'effet de la carte posée	
		loopHeroGraphics.drawOneCell(plateau, context, i, j);
		hero.deleteCardFromInventory(selectedCard);
		
		
		
		//On supprime la sélection du joueur
		gameData.selectCard(null);
		gameData.selectCell(null);
		loopHeroGraphics.drawInventory(context, hero);
		
	

		
		
	}
	
	
    public static void main(String[] args) {
        Main controller = new Main();
        Application.run(Color.WHITE,controller::loopHero);
        
    }

}