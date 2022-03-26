package fr.but.loopHero.player;

import java.util.ArrayList;

import fr.but.loopHero.droppable.Droppable;

public class Player {

	private int maxHealth;
	private int currentHealth;
	private double regenPerSecond;
	
	private int defensePoints;
	
	private int minDamagePoints;
	private int maxDamagePoints;

	private double counterPercent;
	
	private final ArrayList<Droppable> playerInventory;
	
	private int currentNumOfCell = 0;
	
	
	
	public Player() {
		this.playerInventory = new ArrayList<>();
		
		this.maxHealth = 250;
		this.currentHealth = 250;
		this.regenPerSecond = 0;
		
		this.defensePoints = 0;
		this.counterPercent = 0.0;
	
		this.minDamagePoints = 4;
		this.maxDamagePoints = 6;
		
	}
	
	public boolean isDead() {
		return currentHealth <= 0;
	}
	
	
	
	public int addCurrentNumOfCell() {
		if (currentNumOfCell >= 34)
			currentNumOfCell = 0;
		
		currentNumOfCell++; 
		return currentNumOfCell-1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
