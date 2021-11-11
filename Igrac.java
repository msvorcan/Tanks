package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends Figura {

	public Igrac(Polje polje) {
		super(polje);
	}

	@Override
	public void iscrtaj() {
		if(polje != null) {			
			Graphics g = polje.getGraphics();
			g.setColor(Color.RED);
			racunajDimenzije();
		
			g.drawLine(sirina/2, 0, sirina/2, visina);
		
			g.drawLine(0, visina/2, sirina, visina/2);
		}
	}

}
