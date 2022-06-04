package fr.but.loopHero.player;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.droppable.Equipement;
import fr.but.loopHero.droppable.Ressource;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.graphics.GameGraphics;
import fr.but.loopHero.game.objects.tiles.LandScape;
import fr.umlv.zen5.ApplicationContext;

public class Player {

	private final int maxHealth_default = 250;
	private int maxHealth = 250;
	private int currentHealth = maxHealth;
	private double regenPerSecond = 0;
	
	private int defensePoints = 0;
	
	private int minDamagePoints = 4;
	private int maxDamagePoints = 6;

	private double counterPercent = 0.40;
	private double evadePercent = 0.0;
	private double vampirismPercent = 0.0;
	
	private final ArrayList<ArrayList<Droppable>> playerInventory = new ArrayList<ArrayList<Droppable>>();
	
	private int currentNumOfCell = 0;
	
	
	
	public Player() {
		playerInventory.add(new ArrayList<Droppable>());
		playerInventory.add(new ArrayList<Droppable>());
		playerInventory.add(new ArrayList<Droppable>());
		
		playerInventory.get(0).addAll(LoopHeroGameData.START_CARDS);
		
	}
	
	public boolean isDead() {
		return currentHealth <= 0;
	}
	
	public int getCurrentCellIndex(){
		return (currentNumOfCell == 34 ?  0 :  currentNumOfCell);
	}
	
	public int addCurrentNumOfCell() {
		if (currentNumOfCell >= 34) {
			currentNumOfCell = 0;
			LoopHeroGameData.LEVEL++;
		}
		currentNumOfCell++; 
		return currentNumOfCell-1;
	}

	public int attack() {
		Random r = new Random();
		return r.nextInt(minDamagePoints, maxDamagePoints+1);
		//return r.nextInt(maxDamagePoints-minDamagePoints)+minDamagePoints;
	}

	
	// Prends en param les degats supposements subis et retourne la vraie valeur en fonction des autres param.
	public int takeDamage(int damages, CombatEffects usedSpecialEffect, GameGraphics graphics,ApplicationContext context,int attaque) {
		if(usedSpecialEffect == null) {
			graphics.showEffect(context, attaque, "Le héro a perdu : "+damages+" HP", Color.RED);
		int realDamages = damages-defensePoints;
		currentHealth -= realDamages;
		return realDamages;
			
		}
		
	
		switch (usedSpecialEffect) {
		case Counter -> {
			
			graphics.showEffect(context, attaque, "Le héro contre l'attaque, le mob perds : "+damages+" HP", Color.RED);
			return -damages;
			}
		
		case Evade ->{	
		graphics.showEffect(context, attaque, "Le héro a utilisé l'esquive et perds : 0 HP", Color.RED);
		return 0;
		}
		case Vampirism ->{
			graphics.showEffect(context, attaque, "Le héro a utilisé le vampirisme et obtient :"+damages+" HP en plus !", Color.RED);
			regenHero(damages);
			return 0;}			
		
		default -> {	
			throw new IllegalArgumentException("Impssible");
		}
	}
	
	
	}
	public int[] getHealths() {
		int[] vies = new int[2];
		vies[0] = currentHealth;
		vies[1] = maxHealth;
		
		return vies;
	}
	
	public void regenHero(int hp) {
		currentHealth = (currentHealth+hp > maxHealth ? maxHealth : currentHealth+hp);
	}

	public void addInventory(ArrayList<ArrayList<Droppable>> droppedItems) {
		playerInventory.get(0).addAll(droppedItems.get(0));
		playerInventory.get(1).addAll(droppedItems.get(1));
		playerInventory.get(2).addAll(droppedItems.get(2));
		
	}
	
	public void addCard(Card card) {
		playerInventory.get(0).add(card);
	}
	public void addEquipement(Equipement equipement) {
		playerInventory.get(1).add(equipement);
	}
	public void addRessource(Ressource ressource) {
		playerInventory.get(2).add(ressource);
	}

	public ArrayList<ArrayList<Droppable>> getInventory() {
		
		return playerInventory;
	}

	
	public void deleteCardFromInventory(Droppable drop) {
		playerInventory.get(0).remove(drop);
		
		
	}

	public void increaseMaxHealth(int i) {
		maxHealth = maxHealth+ (int) i;
		
	}
	
	public String getIntervalDamage() {
		return this.minDamagePoints+" - "+this.maxDamagePoints;
	}
	
	
	// Accesseurs pour l'affichage
	public double maxHealth() {
		return this.maxHealth-maxHealth_default;
	}
	
	public int defensePoints() {
		return this.defensePoints;
	}
	
	public double counterPercent() {
		return this.counterPercent;
	}
	
	public double evadePercent() {
		return this.evadePercent;
	}
	
	public double regenPerSecond() {
		return this.regenPerSecond;
	}
	
	public double vampirismPercent() {
		return this.vampirismPercent;
	}

	public CombatEffects useSpecialEffect() {
		Random r = new Random();
		
		
		ArrayList<CombatEffects> liste = new ArrayList<>();
		liste.add(CombatEffects.Counter);
		liste.add(CombatEffects.Evade);
		liste.add(CombatEffects.Vampirism);
		
		int chance = r.nextInt(liste.size());
		CombatEffects effet = liste.get(chance);
		
		chance = r.nextInt(99);
		
		switch (effet) {
		case Counter -> {
			if(chance < counterPercent*100)
				return CombatEffects.Counter;
		}
		
		case Evade ->{
			if(chance < evadePercent*100)
				return CombatEffects.Evade;
		}		
		
		case Vampirism ->{
			if(chance < vampirismPercent*100)
				return CombatEffects.Vampirism;
		}			
		
		
		
		default -> {
			return null;
		}
				
		}
		
		
		return null;
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
