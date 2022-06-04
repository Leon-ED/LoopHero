package fr.but.loopHero.game.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.plaf.FontUIResource;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.droppable.Equipement;
import fr.but.loopHero.droppable.Ressource;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.TimeData;
import fr.but.loopHero.game.objects.Board;
import fr.but.loopHero.game.objects.Cell;
import fr.but.loopHero.player.Player;
import fr.umlv.zen5.ApplicationContext;

public record GameGraphics(int xOrigin, int yOrigin, int length, int width, int taille) {
	
		public void drawBoard(Board plateau, ApplicationContext context) {
			context.renderFrame( graphics ->{
				Cell[][] boardMatrix = plateau.getBoardMatrix();
				
				graphics.setColor(Color.DARK_GRAY);
				
				graphics.fill(new Rectangle2D.Float(xOrigin,yOrigin,21*taille,12*taille)); // Grand rectangle de toute la surface
				graphics.draw(new Rectangle2D.Float(xOrigin,yOrigin,21*taille,12*taille));				
				
				for (int i = 0; i < boardMatrix.length; i++) {
					for (int j = 0; j < boardMatrix[0].length; j++) {
						//System.out.println(boardMatrix[i][j]);
						
						if (boardMatrix[i][j].isEmpty()) 
							continue;
							
						this.drawOneCell(plateau, context, i, j);
						//graphics.setColor(boardMatrix[i][j].getColor()); // On dï¿½finis la couleur de la Tile
						//int x = xOrigin + j*taille;
						//int y = yOrigin + (i*taille);
						//graphics.fill(new Rectangle2D.Float(x,y,taille,taille));
						//graphics.draw(new Rectangle2D.Float(x,y,taille,taille));	
					}
					}
				
				drawSpeedIndicator(context,1);
	
			});
		}
		
		public void drawOutlineLoop(Board plateau, ApplicationContext context) {
			context.renderFrame( graphics ->{
				graphics.setColor(Color.BLACK);
				for (Cell cell : plateau.getlistCellsLoop()) {
					int i = cell.i();
					int j = cell.j();
					int x = xOrigin + j*taille;
					int y = yOrigin + (i*taille);
					graphics.draw(new Rectangle.Float(x,y,taille,taille));
					
					
				}
				
				
				});
			
		}
		
		
		public void drawHero(Board plateau, ApplicationContext context, Player hero, TimeData timeData,int heroNextPos) {
			context.renderFrame( graphics ->{
				if (heroNextPos-1 >=0)
					this.drawOneCell(plateau, context, plateau.getlistCellsLoop().get(heroNextPos-1).i(),plateau.getlistCellsLoop().get(heroNextPos-1).j());
				graphics.setColor(Color.BLACK);
				drawBar(graphics, 350, timeData.timeFraction(),0,0,Color.GREEN,10);
				

				//System.out.println(heroNextPos);
				int startingPointx = xOrigin + plateau.getlistCellsLoop().get(heroNextPos).j() * taille+taille/4;
				int startingPointy = yOrigin + plateau.getlistCellsLoop().get(heroNextPos).i()* taille +taille/4;
				
				graphics.fillOval(startingPointx,startingPointy,taille/2,taille/2);
				

			
			});
			
		}
		
		public void drawOneCell(Board plateau, ApplicationContext context, int i, int j) {
			context.renderFrame( graphics ->{
				graphics.setColor(plateau.getBoardMatrix()[i][j].getColor()); // On dï¿½finis la couleur de la Tile
				int x = xOrigin + j*taille;
				int y = yOrigin + (i*taille);
				graphics.fill(new Rectangle2D.Float(x,y,taille,taille));
				graphics.draw(new Rectangle2D.Float(x,y,taille,taille));
			});
		}
		
		private void drawBar(Graphics2D graphics, int width, double timeFraction, int xOffset, int yOffset,Color color, int height) {
			graphics.setColor(Color.LIGHT_GRAY);
			graphics.fill(new Rectangle2D.Double(xOrigin+xOffset, (yOrigin - 20)+yOffset, width, height));
			graphics.setColor(color);
			graphics.fill(new Rectangle2D.Double(xOrigin+xOffset, (yOrigin - 20)+yOffset, width * timeFraction, height));
			graphics.setColor(Color.BLACK);
			graphics.draw(new Rectangle2D.Double(xOrigin+xOffset, (yOrigin - 20)+yOffset, width, height));
			
		}
		
		private Color getHealthColor(int[] healths) {
			double currentHealth = healths[0];
			double maxHealth = healths[1];
			double healthRatio = (currentHealth/maxHealth);
		//	System.out.println(healthRatio);
			//System.out.println(currentHealth);
			if(healthRatio >= 0.4)
				return Color.green;
			if(healthRatio >= 0.15)
				return Color.orange;
			if(healthRatio < 0.15)
				return Color.red;
			
			return Color.GREEN;
			
			
			
		}
		
		public void drawHealthInfos(ApplicationContext context,Player hero) {
			int[] healths = hero.getHealths();
			double currentHealth = healths[0];
			double maxHealth = healths[1];
			double healthRatio = (currentHealth/maxHealth);
			Color color = getHealthColor(healths);

			context.renderFrame(graphics ->{
				//System.out.println(healthRatio);
				drawBar(graphics, 400, healthRatio, 1300,450,color,30);
				graphics.setFont(new FontUIResource("Arial", 0, 25));
				graphics.drawString((int)currentHealth+"/"+(int)maxHealth,1500, 505);
				
				
				
			});
			
			
		}
		
		public void drawLevel(ApplicationContext context) {
			context.renderFrame(graphics ->{
				graphics.setColor(LoopHeroGameData.BG_COLOR);
				graphics.fillRect(1800, 20, 100, 35);
				
				graphics.setColor(Color.RED);
				graphics.setFont(new FontUIResource("Arial", 0, 25));
				graphics.drawString("Niveau: "+LoopHeroGameData.LEVEL, 1360, 570);
			});	
		}
		
		public void drawMobs(ApplicationContext context,Board plateau) {
			context.renderFrame( graphics ->{
				ArrayList<Cell> listCellsLoop = plateau.getlistCellsLoop();
				for (Cell cell : listCellsLoop ) {
					if (cell.hasMob()) {
						cell.getFirstMob().draw(graphics,taille);
					}
				}
			});
		}


		public void drawInventory(ApplicationContext context,Player hero) {
			ArrayList<ArrayList<Droppable>> inventory = hero.getInventory();
			drawInventoryCards(context,hero,inventory.get(0));
			//drawInventoryEquipements(context,hero,inventory.get(1));
			drawInventoryRessources(context,hero,inventory.get(2));

		}
		
		public void drawStaticInventory(ApplicationContext context) {
			int startX = taille*23;
			int startY = taille*4;
			context.renderFrame(graphics->{
				
				for(int i = 0; i< LoopHeroGameData.INV_HEIGHT; i++) {
					for(int j = 0; j<LoopHeroGameData.INV_WIDTH; j++) {
						int x = startX + (xOrigin + j*taille);
						int y = startY + (yOrigin + (i*taille));		
						graphics.drawRect(x, y, taille, taille);
						
						graphics.drawRect(x, y-taille*4, taille, taille);
						
					}
					
				}
				
				
				
				
				
			});
			
			
			
		}
	

		
		private void drawInventoryCards(ApplicationContext context,Player hero,ArrayList<Droppable> Cards) {
			context.renderFrame( graphics ->{
				graphics.setColor(LoopHeroGameData.BG_COLOR);
				graphics.fill(new Rectangle2D.Float(xOrigin, yOrigin+(12*taille), taille*12, taille*2));
			});
			for (int i=0; i<Cards.size();i++)
				drawCard(context, Cards.get(i), i);
		}
		
		
		private void drawInventoryEquipements(ApplicationContext context,Player hero,ArrayList<Droppable> Equipements) {
			context.renderFrame( graphics ->{
				graphics.setColor(LoopHeroGameData.BG_COLOR);
				graphics.fill(new Rectangle2D.Float(xOrigin, yOrigin+(12*taille), taille*12, taille*2));
			});
			for (int i=0; i<Equipements.size();i++)
				drawEquipement(context, Equipements.get(i), i);
		}
		
		private void drawInventoryRessources(ApplicationContext context,Player hero,ArrayList<Droppable> Ressources) {
			
			context.renderFrame( graphics ->{
				graphics.setColor(LoopHeroGameData.BG_COLOR);
				//graphics.setColor(Color.red);
				graphics.fill(new Rectangle2D.Float(xOrigin, yOrigin+(12*taille)+taille*2, taille*12, taille*2));
			});
			 
			HashMap<String,Integer> dict_ressources = new HashMap<String,Integer>();
			
			for (Droppable droppable : Ressources) {
				if (dict_ressources.containsKey(droppable.displayName())) {
					dict_ressources.put(droppable.displayName(), dict_ressources.get(droppable.displayName())+1);
				}
				else 
					dict_ressources.put(droppable.displayName(), 1);
			}
			
			
			
			int count = 0;
			
			
			
			for (HashMap.Entry<String, Integer> ressource : dict_ressources.entrySet()) {
				
				count++;
				String key = ressource.getKey();
				Integer val = ressource.getValue();
				drawRessource(context,key,count,val);
				
			}
				
				
			
		}
		

		private void drawRessource(ApplicationContext context, String name, int position, int occurence) {
			
			context.renderFrame( graphics ->{
				graphics.setColor(Color.BLACK);
				graphics.draw(new Rectangle2D.Float(xOrigin+(taille*position), yOrigin+(12*taille)+taille*2, taille, taille));
									
				graphics.setColor(Color.RED); 
				graphics.setFont(new FontUIResource("Arial", 0, 10));
				graphics.drawString(name, xOrigin+(taille*position), yOrigin+(12*taille)+taille*2+taille+15);
				
				graphics.setFont(new FontUIResource("Arial", 0, 20));
				graphics.drawString(occurence+"", xOrigin+(taille*position), yOrigin+(12*taille)+taille*2+taille);
				
			});	
		}
		
		private void drawEquipement(ApplicationContext context,Droppable droppable,int i) {

		}

		private void drawCard(ApplicationContext context,Droppable droppable,int i) { 
			context.renderFrame( graphics ->{
				graphics.setColor(Color.BLACK);
				graphics.draw(new Rectangle2D.Float(xOrigin+(taille*i), yOrigin+(12*taille), taille, taille));
				String cardName = droppable.displayName();
				
				graphics.setColor(Color.RED);
				graphics.setFont(new FontUIResource("Arial", 0, 17));
				graphics.drawString(cardName, xOrigin+(taille*i), yOrigin+(12*taille)+taille+15);
			
			});	
		}






		private int indexFromReaCoord(float coord, int origin) {
			return (int) ((coord - origin) / taille);
		}


		public int lineFromY(float y) {
			return indexFromReaCoord(y, yOrigin);
		}


		public int columnFromX(float x) {
			return indexFromReaCoord(x, xOrigin);
		}

		public void drawSelection(Board plateau, ApplicationContext context, int i, int j, Color color) {
			
			context.renderFrame( graphics ->{
				graphics.setColor(color); // On dï¿½finis la couleur de la Tile
				int x = xOrigin + j*taille;
				int y = yOrigin + (i*taille);
				graphics.draw(new Rectangle2D.Float(x,y,taille,taille));
			});
		}

		public void drawSpeedIndicator(ApplicationContext context, int speedFactor) {
			context.renderFrame(graphics ->{
				graphics.setColor(LoopHeroGameData.BG_COLOR);
				graphics.fillRect(500, 15, 200, 35);
				
				graphics.setColor(LoopHeroGameData.TXT_COLOR_BLK);
				
				graphics.setFont(new FontUIResource("Arial", 0, 30));
				graphics.drawString("Vitesse : x"+speedFactor, 500, 40);
				//drawBar(graphics, 400, healthRatio, 1300,400,color);
			});
		}
		
		// MARCHE PASS
		private void drawImage(ApplicationContext context,int x, int y, Path path) {
	        try (InputStream in = Files.newInputStream(path)) {
	            BufferedImage img = ImageIO.read(in);
	            context.renderFrame(graphics ->{
	            	graphics.drawImage(img, x, y, null);
	            //graphics.drawImage(img, scaling, xOrigin + column * squareSize, yOrigin + line * squareSize);
	            });
	        } catch (IOException e) {
	            throw new RuntimeException("problÃ¨me d'affichage : " + path.getFileName());
	        }
	    }

		// Affiche les informations du héros
		public void drawHeroInformations(ApplicationContext context, Player hero) {
			
			int startX = taille*23;
			int startY = taille*3;
			
			context.renderFrame(graphics ->{
				//graphics.setColor(LoopHeroGameData.BG_COLOR);
				graphics.setColor(Color.BLACK);
				graphics.fillRect(1350, 500, 400, 400);
				
				graphics.setColor(LoopHeroGameData.TXT_COLOR_WHT);
				graphics.setFont(new FontUIResource("Arial", 0, 30));
				graphics.drawString("Statistiques", 1475, 540);
				
				graphics.setFont(new FontUIResource("Arial", 0, 25));
				graphics.drawString("Dégâts : "+hero.getIntervalDamage(), 1360, 610);
				
				graphics.setFont(new FontUIResource("Arial", 0, 25));
				graphics.drawString("PV max : +"+(int)hero.maxHealth(), 1360, 650);
				
				graphics.setFont(new FontUIResource("Arial", 0, 25));
				graphics.drawString("Défense : "+hero.defensePoints(), 1360, 690);
				
				graphics.setFont(new FontUIResource("Arial", 0, 25));
				graphics.drawString("Contre : "+(int)hero.counterPercent()+"%", 1360, 730);
				
				graphics.setFont(new FontUIResource("Arial", 0, 25));
				graphics.drawString("Esquive : "+(int)hero.evadePercent()+"%", 1360, 770);
				
				graphics.setFont(new FontUIResource("Arial", 0, 25));
				graphics.drawString("Régénération par sec : +"+(int)hero.regenPerSecond(), 1360, 810);
				
				graphics.setFont(new FontUIResource("Arial", 0, 25));
				graphics.drawString("Vampirisme : "+(int)hero.vampirismPercent()+"%", 1360, 850);
			});
			
		}


}


	
	
	