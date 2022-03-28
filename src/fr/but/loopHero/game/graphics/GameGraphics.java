package fr.but.loopHero.game.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import fr.but.loopHero.game.LoopHeroData;
import fr.but.loopHero.game.TimeData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public record GameGraphics(int xOrigin, int yOrigin, int length, int width, int taille) {
	
		public void drawBoard(Board plateau, ApplicationContext context) {
			context.renderFrame( graphics ->{
				Cell[][] boardMatrix = plateau.getBoardMatrix();
				
				graphics.setColor(Color.BLACK);
				graphics.draw(new Rectangle2D.Float(taille,taille*2,21*taille,12*taille)); // Grand rectangle de toute la surface
				
				for (int i = 0; i < boardMatrix.length; i++) {
					for (int j = 0; j < boardMatrix[0].length; j++) {
						//System.out.println(boardMatrix[i][j]);
						
						if (boardMatrix[i][j].isEmpty()) 
							continue;
							
							
						graphics.setColor(boardMatrix[i][j].getColor()); // On définis la couleur de la Tile
						int x = taille + j*taille;
						int y = taille + (i*taille)+ taille;
						graphics.fill(new Rectangle2D.Float(x,y,taille,taille));
						}
					}
	
			});
		}
		
		public void drawOutlineLoop(Board plateau, ApplicationContext context) {
			context.renderFrame( graphics ->{
				graphics.setColor(Color.BLACK);
				for (Cell cell : plateau.getlistCellsLoop()) {
					int i = cell.i();
					int j = cell.j();
					int x = taille + j*taille;
					int y = taille + (i*taille)+ taille;
					graphics.draw(new Rectangle.Float(x,y,taille,taille));
					
				}
				
				
				});
			
		}
		
		
		public void drawHero(Board plateau, ApplicationContext context, Player hero, TimeData timeData) {
			context.renderFrame( graphics ->{
				graphics.setColor(Color.BLACK);
				drawBar(graphics, 350, timeData.timeFraction());
				int heroNextPos = hero.addCurrentNumOfCell();
				

				System.out.println(heroNextPos);
				int startingPointx = taille + plateau.getlistCellsLoop().get(heroNextPos).j() * taille+taille/4;
				int startingPointy = taille*2 + plateau.getlistCellsLoop().get(heroNextPos).i()* taille +taille/4;
				
				graphics.fillOval(startingPointx,startingPointy,taille/2,taille/2);
				
				if (heroNextPos <= 0 ) {
					heroNextPos = plateau.getlistCellsLoop().size();
				}
					
				this.drawOneCell(plateau, context, plateau.getlistCellsLoop().get(heroNextPos-1).i(),plateau.getlistCellsLoop().get(heroNextPos-1).j());
			
			
			});
			
		}
		
		public void drawOneCell(Board plateau, ApplicationContext context, int i, int j) {
			context.renderFrame( graphics ->{
				graphics.setColor(plateau.getBoardMatrix()[i][j].getColor()); // On définis la couleur de la Tile
				int x = taille + j*taille;
				int y = taille + (i*taille)+ taille;
				graphics.fill(new Rectangle2D.Float(x,y,taille,taille));
			});
		}
		
		private void drawBar(Graphics2D graphics, int width, double timeFraction) {
			graphics.setColor(Color.LIGHT_GRAY);
			graphics.fill(new Rectangle2D.Double(xOrigin, yOrigin - 20, width, 10));
			graphics.setColor(Color.GREEN);
			graphics.fill(new Rectangle2D.Double(xOrigin, yOrigin - 20, width * timeFraction, 10));
			graphics.setColor(Color.BLACK);
			graphics.draw(new Rectangle2D.Double(xOrigin, yOrigin - 20, width, 10));
		}
		
		public void drawMobs(ApplicationContext context,Board plateau) {
			context.renderFrame( graphics ->{
				ArrayList<Cell> listCellsLoop = plateau.getlistCellsLoop();
				for (Cell cell : listCellsLoop ) {
					if (cell.hasMob()) {
						graphics.setColor(cell.getFirstMob().getColor()); // Couleur du slime
						int startingPointx = taille + cell.j() * taille;
						int startingPointy = taille*2 + cell.i()* taille;
						graphics.fillOval(startingPointx,startingPointy,taille/2,taille/2);
					}
				
				}
			});
		}
}


	
	
	