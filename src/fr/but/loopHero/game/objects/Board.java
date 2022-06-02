package fr.but.loopHero.game.objects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import fr.but.loopHero.game.objects.tiles.CampFire;
import fr.but.loopHero.game.objects.tiles.Road;
import fr.but.loopHero.game.objects.tiles.RoadSide;
import fr.but.loopHero.mobs.Mobs;
import fr.but.loopHero.mobs.Slime;

public class Board {
    
    private final int boardHeight;
    private final int boardWidht;
    private final Cell[][] boardMatrix;
    private final ArrayList<Cell> listCellsLoop;
    
    
    public Board(int boardHeight, int boardWidht) {
        this.boardHeight = Objects.requireNonNull(boardHeight);
        this.boardWidht = Objects.requireNonNull(boardWidht);
        boardMatrix = new Cell[boardHeight][boardWidht];
		this.listCellsLoop = new ArrayList<Cell>();
        
    }
    
    public void fill() {
    	for (int i = 0; i < boardHeight; i++) {
    		for (int j = 0; j < boardWidht; j++) {
    			boardMatrix[i][j] = new Cell(i,j);
			}
    	}
    }
    
    public void createLoop(int numberOfCells) {
    	
    	
    	
    	boardMatrix[4-1][10-1].setType(new CampFire("CampFire")); // Feu de camps (10,4)
    	listCellsLoop.add(boardMatrix[3][9]);
    	
    	for (int i=0;i<6;i++) {
    		boardMatrix[3][10+i].setType(new Road("Wasteland")); // Première ligne 
    		listCellsLoop.add(boardMatrix[3][10+i]);}
    	
    	boardMatrix[4][15].setType(new Road("Wasteland"));
    	listCellsLoop.add(boardMatrix[4][15]);
    	boardMatrix[5][15].setType(new Road("Wasteland"));
    	listCellsLoop.add(boardMatrix[5][15]);
    	boardMatrix[5][14].setType(new Road("Wasteland"));
    	listCellsLoop.add(boardMatrix[5][14]);
    	
    	
    	for (int i=0;i<5;i++) {boardMatrix[6][14-i].setType(new Road("Wasteland"));listCellsLoop.add(boardMatrix[6][14-i]);}
    	for (int i=0;i<2;i++) {boardMatrix[7+i][10].setType(new Road("Wasteland"));listCellsLoop.add(boardMatrix[7+i][10]);}
    	boardMatrix[8][9].setType(new Road("Wasteland"));
    	listCellsLoop.add(boardMatrix[8][9]);
    	boardMatrix[9][9].setType(new Road("Wasteland"));
    	listCellsLoop.add(boardMatrix[9][9]);
    	for (int i=0;i<1;i++) {boardMatrix[9][8-i].setType(new Road("Wasteland"));listCellsLoop.add(boardMatrix[9][8-i]);}
    	for (int i=0;i<4;i++) {boardMatrix[9-i][7].setType(new Road("Wasteland"));listCellsLoop.add(boardMatrix[9-i][7]);}
    	boardMatrix[6][6].setType(new Road("Wasteland"));
    	listCellsLoop.add(boardMatrix[6][6]);
    	boardMatrix[6][5].setType(new Road("Wasteland"));
    	listCellsLoop.add(boardMatrix[6][5]);
    	for (int i=0;i<2;i++) {boardMatrix[5-i][5].setType(new Road("Wasteland"));listCellsLoop.add(boardMatrix[5-i][5]);}
    	boardMatrix[4][6].setType(new Road("Wasteland"));
    	listCellsLoop.add(boardMatrix[4][6]);
    	for (int i=0;i<2;i++) {boardMatrix[3-i][6].setType(new Road("Wasteland"));listCellsLoop.add(boardMatrix[3-i][6]);}
    	for (int i=0;i<3;i++) {boardMatrix[2][7+i].setType(new Road("Wasteland"));listCellsLoop.add(boardMatrix[2][7+i]);}
    	
    	for (int i = 0; i < listCellsLoop.size(); i++) {
    		int iCell = listCellsLoop.get(i).i();
    		int jCell = listCellsLoop.get(i).j();
    		for (int x=-1; x<=1;x= x+2) {
    			System.out.println(iCell+x + " "+ jCell);
    			if (!boardMatrix[iCell+x][jCell].type().name().equals("ROAD"))
    				boardMatrix[iCell+x][jCell].setType(new RoadSide("Wasteland",Color.RED));
    		}
    		for (int y=-1; y<=1;y=y+2) {
    			
    		}	
						
    	}
    	
	}
    
    
    @Override
    public String toString() {
    	System.out.println("Plateau de jeux :");
    	for (int i = 0; i < boardHeight; i++) {
    		System.out.println("\n");
    		for (int j = 0; j < boardWidht; j++) {
    			System.out.print(boardMatrix[i][j]);
    			System.out.print("  ");
    		}
    	}
    	return "";
    }
    
    public void getSpawnableEntity(HashMap<Mobs, Cell> dict_mob_cell) {
		// Pour les phases suivantes, boucler sur tous le tableau et vï¿½rif les impacts sur les tiles d'a cotï¿½ et ajouter une liste de carte qui se fait impacter par les tiles d'a cotï¿½.
	}
    
    public void spawnEntity() {
		// Spawn des slimes
    	
    	for (Cell cell : listCellsLoop ) {
    		// Toutes les cell sont celles de la boucle
    		if (!cell.hasMob()) {
    			if (cell.type() instanceof CampFire) {
					continue;	
				}
    			
    			
	    		Random rand = new Random();
	    		int rand_number = rand.nextInt(99);
	    		if (rand_number <50) { // < 5
	    			cell.addMob(new Slime(cell));
	    		}
    		}
    			
    	}
    	// Spawns des xxx, vï¿½rifier si il y a x card a cotï¿½...
    }
    	
    	
	
    
    
    public Cell[][] getBoardMatrix() {return boardMatrix;}
    public ArrayList<Cell> getlistCellsLoop() {return listCellsLoop;}
}