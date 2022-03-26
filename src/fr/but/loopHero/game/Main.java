package fr.but.loopHero.game;

import fr.but.loopHero.game.graphics.GameGraphics;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
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


	private void loopHero(ApplicationContext context) {

		
		
		Board plateau = new Board(12,21);
		plateau.fill(); // Remplis de cellule vide
		plateau.createLoop(34); // Créé la boucle du jeux (les cases ou le héro se déplace)
		//System.out.println(plateau);
		
		GameGraphics loopHeroGraphics  = new GameGraphics(0,0,0,0,60);
		
		TimeData loopHeroTimeData = new TimeData();
		
		loopHeroGraphics.drawBoard(plateau, context);
		//loopHeroGraphics.drawOutlineLoop(plateau, context);

		Player hero = new Player();
		
		for (int i=0; i<40;i++) {
			Event event = context.pollOrWaitEvent(200);
				
			loopHeroGraphics.drawHero(plateau, context, hero);

		}
		
		
		
		Event event = context.pollOrWaitEvent(100000000);
		context.exit(0);

	}
	
	public static void main(String[] args) {
		Main controller = new Main();
		Application.run(Color.WHITE,controller::loopHero);
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
