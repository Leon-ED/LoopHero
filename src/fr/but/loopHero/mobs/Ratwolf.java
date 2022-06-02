package fr.but.loopHero.mobs;

import java.awt.Color;
import java.awt.Graphics2D;
import fr.but.loopHero.game.objects.Cell;

public class Ratwolf extends Mobs {
	

	

	
	public Ratwolf(Cell cell) {
		super(-1,16,3.6,0.75,new Color(139,69,19),0.40,cell);

		
	}

	@Override
	public void draw(Graphics2D graphics, int taille) {
		graphics.setColor(super.getColor()); // Couleur du slime
		int startingPointx = taille + super.getCurrentCell().j() * taille;
		int startingPointy = taille + super.getCurrentCell().i()* taille;
		graphics.fillOval(startingPointx,startingPointy,taille/2,taille/2);
		
		graphics.setColor(Color.black); // Contour du slime
		graphics.drawOval(startingPointx,startingPointy,taille/2,taille/2);
		
		
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Ratwolf))
			return false;
		return true;
	}



}
