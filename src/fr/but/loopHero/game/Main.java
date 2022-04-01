package fr.but.loopHero.game;

import fr.but.loopHero.game.graphics.GameGraphics;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.game.objects.tiles.Tile;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.mobs.Slime;
import fr.but.loopHero.player.Player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.ArrayList;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;



public class Main {
    private final GameGraphics loopHeroGraphics  = new GameGraphics(150,50,50,50,60);
    private final TimeData loopHeroTimeData = new TimeData();
    private final Board plateau = new Board(12,21);
    private final Player hero = new Player();
    private final LoopHeroGameData gameData = new LoopHeroGameData();
    private final static int USER_ACTION_DELAY = 1500;
    
    
    private void loopHero(ApplicationContext context) {
    	LoopHeroGameData.generateDroppableItems();
        plateau.fill(); 
        plateau.createLoop(34);
        loopHeroGraphics.drawBoard(plateau, context);

        while (true) {
        	loopHeroGraphics.drawLevel(context);
        	if(Combat.combatAvailable(plateau.getlistCellsLoop().get(hero.getCurrentCellIndex())))
        		Combat.startCombat(context, hero, loopHeroTimeData,plateau.getlistCellsLoop().get(hero.getCurrentCellIndex()),gameData, loopHeroGraphics, plateau);
        	loopHeroGraphics.drawHealthInfos(context, hero);
        	moveHeroAndDraw(context);
        	if (loopHeroTimeData.isDayPased()) 
        		plateau.spawnEntity();	
        	loopHeroGraphics.drawMobs(context, plateau);
        	doEventActionAndDraw(context);
        	

        }
        
    }
    
    private void doEventActionAndDraw(ApplicationContext context) {
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
				//doMouseAction(context, event);
			}
			break;
		}
		//view.draw(context, data, loopHeroTimeData);
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
    	if (loopHeroTimeData.elapsedBob() >= TimeData.HERO_DELAY) {
    		loopHeroGraphics.drawHero(plateau, context, hero, loopHeroTimeData);
    		loopHeroTimeData.resetElapsedBob();
    	}
	}
    
    
    

    
    	
    
    
    public static void main(String[] args) {
        Main controller = new Main();
        Application.run(Color.WHITE,controller::loopHero);
        
    }

}