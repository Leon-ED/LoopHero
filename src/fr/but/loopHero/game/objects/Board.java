package fr.but.loopHero.game.objects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import fr.but.loopHero.game.LoopHeroGameData;
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
    public final ArrayList<Cell> listCellsLoop;
    
    
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
    	
    	
    	ReadDatas readData = new ReadDatas();
        
        try {
        	readData.readPathFromFile(LoopHeroGameData.BOUCLE_PATH, this, listCellsLoop);
        }
        catch (Exception e){
        	System.out.println("Problème dans la lecture du fichier");
        }
    	
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
    

    public boolean isOutOfBounds(int i, int j) {
    	try {
    		Cell cell = boardMatrix[i][j];
    		return false;
		} catch (Exception e) {
			return true;
		}
    }
    	
	
    
    
    public Cell[][] getBoardMatrix() {return boardMatrix;}
    public ArrayList<Cell> getlistCellsLoop() {return listCellsLoop;}
}