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
import fr.but.loopHero.game.objects.tiles.Wasteland;
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
    	boardMatrix[3][9].setIndex(0);
    	
    	for (int i=0;i<6;i++) {
    		boardMatrix[3][10+i].setType(new Wasteland()); // Première ligne 
    		listCellsLoop.add(boardMatrix[3][10+i]);
    		boardMatrix[3][10+i].setIndex(i+1);
    	
    	}
    	
    	boardMatrix[4][15].setType(new Wasteland());
    	boardMatrix[4][15].setIndex(7);
    	listCellsLoop.add(boardMatrix[4][15]);
    	
    	
    	boardMatrix[5][15].setType(new Wasteland());
    	boardMatrix[5][15].setIndex(8);
    	listCellsLoop.add(boardMatrix[5][15]);
    	
    	
    	
    	boardMatrix[5][14].setType(new Wasteland());
    	boardMatrix[5][14].setIndex(9);
    	listCellsLoop.add(boardMatrix[5][14]);
    	
    	
    	for (int i=0;i<5;i++) {boardMatrix[6][14-i].setType(new Wasteland());listCellsLoop.add(boardMatrix[6][14-i]);boardMatrix[6][14-i].setIndex(10+i);}
    	for (int i=0;i<2;i++) {boardMatrix[7+i][10].setType(new Wasteland());listCellsLoop.add(boardMatrix[7+i][10]);boardMatrix[7+i][10].setIndex(15+i);;}
    	
    	boardMatrix[8][9].setType(new Wasteland());
    	boardMatrix[8][9].setIndex(17);
    	listCellsLoop.add(boardMatrix[8][9]);
    	
    	
    	boardMatrix[9][9].setType(new Wasteland());
    	boardMatrix[9][9].setIndex(18);
    	listCellsLoop.add(boardMatrix[9][9]);
    	
    	
    	for (int i=0;i<1;i++) {boardMatrix[9][8-i].setType(new Wasteland());listCellsLoop.add(boardMatrix[9][8-i]);boardMatrix[9][8-i].setIndex(19+i);}
    	for (int i=0;i<4;i++) {boardMatrix[9-i][7].setType(new Wasteland());listCellsLoop.add(boardMatrix[9-i][7]);boardMatrix[9-i][7].setIndex(20+i);}
    	
    	boardMatrix[6][6].setType(new Wasteland());
    	boardMatrix[6][6].setIndex(24);
    	listCellsLoop.add(boardMatrix[6][6]);
    	
    	
    	boardMatrix[6][5].setType(new Wasteland());
    	boardMatrix[6][5].setIndex(25);
    	listCellsLoop.add(boardMatrix[6][5]);
    	
    	for (int i=0;i<2;i++) {boardMatrix[5-i][5].setType(new Wasteland());listCellsLoop.add(boardMatrix[5-i][5]);boardMatrix[5-i][5].setIndex(26+i);}
    	
    	boardMatrix[4][6].setType(new Wasteland());
    	listCellsLoop.add(boardMatrix[4][6]);
    	boardMatrix[4][6].setIndex(28);
    	
    	for (int i=0;i<2;i++) {boardMatrix[3-i][6].setType(new Wasteland());listCellsLoop.add(boardMatrix[3-i][6]);boardMatrix[3-i][6].setIndex(29+i);}
    	for (int i=0;i<3;i++) {boardMatrix[2][7+i].setType(new Wasteland());listCellsLoop.add(boardMatrix[2][7+i]);boardMatrix[2][7+i].setIndex(31+i);}
    	
    	
    	// Permet de générer les RoadSide en fonction de la boucle (des Road)
    	for (Cell cell : listCellsLoop) {
    		int i = cell.i();
    		int j = cell.j();
    		for (int x=-1; x<=1;x= x+2) {
    			int newI = i+x;
    			//System.out.println(i+x + " "+ j);
    			//System.out.println(listCellsLoop.contains(boardMatrix[newI][j]));
    			if (!(listCellsLoop.contains(boardMatrix[newI][j]) || boardMatrix[newI][j].type().name().equalsIgnoreCase("RoadSide")))
    				boardMatrix[i+x][j].setType(new RoadSide("Wasteland",Color.DARK_GRAY.brighter()));
    		}
    		for (int y=-1; y<=1; y=y+2) {
    			int newJ = j+y;
    			if (!(listCellsLoop.contains(boardMatrix[i][newJ]) || boardMatrix[i][newJ].type().name().equalsIgnoreCase("RoadSide")))
    				boardMatrix[i][newJ].setType(new RoadSide("Wasteland",Color.DARK_GRAY.brighter()));
    			
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
    

    	
    	
	
    
    
    public Cell[][] getBoardMatrix() {return boardMatrix;}
    public ArrayList<Cell> getlistCellsLoop() {return listCellsLoop;}
}