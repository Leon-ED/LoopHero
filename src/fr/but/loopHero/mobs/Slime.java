package fr.but.loopHero.mobs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

import fr.but.loopHero.droppable.Card;
import fr.but.loopHero.droppable.Droppable;
import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.objects.Cell;

public class Slime implements Mobs{
	private final int percentageOfSpawn;
	private  int healthPoint;
	private final double strenght;
	private final double speed;
	private  Color color; // a remettre en final quand on aura fait despawn les mobs morts
	private final Cell cell;
	
	private final double dropChance;
	
	
	public Slime(Cell cell) {
		percentageOfSpawn = 5;
		healthPoint = 13;
		strenght = 3.3;
		speed = 0.6;
		color = Color.green;
		dropChance = 35;
		this.cell = cell;
		
	}
	
	public boolean isDead() {
		if (health() <= 0) { // a supprimer quand on aura fait despawn les mobs morts
			color = Color.red;
		}
		
		
		return healthPoint <=0;
	}
	
	public Color getColor() {
		return color;
	}

	@Override
	public void takeDamage(int damages) {
		this.healthPoint -= damages;
		
		
	}

	@Override
	public int health() {
		// TODO Auto-generated method stub
		return healthPoint;
	}
	

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Slime))
			return false;
		Slime slime = (Slime) o;
		return this.getPos()[0] == slime.getPos()[0] && this.getPos()[1] == slime.getPos()[1] && healthPoint == slime.healthPoint && color.equals(slime.color);
		
	}
	@Override
	public int hashCode() {
		return Objects.hash(cell, color, healthPoint);
	}
	

	@Override
	public int[] getPos() {
		int[] pos = new int[2];
		pos[0] = cell.i();
		pos[1] = cell.j();
		return pos;
	}

	@Override
	public int attack() {
		int lvl = LoopHeroGameData.LEVEL;
		return (int) ((int) (strenght*lvl)*(1+(lvl-1)*0.02));
	}

	@Override
	public ArrayList<Droppable> getDroppedItems() {
		ArrayList<Droppable> toDrop = new ArrayList<>();
		for(Droppable drop : LoopHeroGameData.MOBS_DROPPABLE_ITEMS) {
    		Random rand = new Random();
    		int rand_number = rand.nextInt(99);
    		if (rand_number < dropChance) {
    			if(drop instanceof Card)
    				drop = (Card) drop;
    			toDrop.add(drop);
    		}
		}
		System.out.println(toDrop.size());
		return toDrop;
	}

	public void draw(Graphics2D graphics, int taille) {
		graphics.setColor(cell.getFirstMob().getColor()); // Couleur du slime
		int startingPointx = taille + cell.j() * taille;
		int startingPointy = taille + cell.i()* taille;
		graphics.fillOval(startingPointx,startingPointy,taille/2,taille/2);
		
		graphics.setColor(Color.black); // Contour du slime
		graphics.drawOval(startingPointx,startingPointy,taille/2,taille/2);
		
	}
	
	
}
