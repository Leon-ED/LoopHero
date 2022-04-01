package fr.but.loopHero.mobs;

import java.awt.Color;

import fr.but.loopHero.game.objects.Cell;

public class Slime implements Mobs{
	private final int percentageOfSpawn;
	private  int healthPoint;
	private final double strenght;
	private final double speed;
	private  Color color; // a remettre en final quand on aura fait despawn les mobs morts
	private final Cell cell;
	private boolean isDead; // a supprimer quand on aura fait despawn les mobs morts
	public Slime(Cell cell) {
		percentageOfSpawn = 5;
		healthPoint = 13;
		strenght = 3.3;
		speed = 0.6;
		color = Color.green;
		this.cell = cell;
		isDead = false;
		
	}
	
	public boolean isDead() {
		if (health() <= 0) { // a supprimer quand on aura fait despawn les mobs morts
			color = Color.red;
			isDead = true;
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
		return this.getPos() == slime.getPos();
		
	}

	@Override
	public int[] getPos() {
		int[] pos = new int[2];
		pos[0] = cell.i();
		pos[1] = cell.j();
		return pos;
	}
	
	
}
