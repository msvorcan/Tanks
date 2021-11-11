package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Tenk extends Figura implements Runnable {
	
	private Thread nit;

	public Tenk(Polje polje) {
		super(polje);
	}
	
	public synchronized void pokreni() {
		nit = new Thread(this);
		nit.start();
	}
	
	public synchronized void zaustavi() {
		if(nit != null)
			nit.interrupt();
	}

	@Override
	public void run() {
		while(!Thread.interrupted()) {
			try {
				synchronized (this) {
					while(!nit.isAlive()) wait();
				}				
				Thread.sleep(500);
				pomeri();
			} catch (InterruptedException e) {
				break;
			}
		}	

	}
	
	public void pomeri() {
		int i, j = 0;
		while (true) {
			int smer = (int)(Math.random() * 4);
			i = 0;
			j = 0;
			switch (smer) {
			case 0:
				i = -1;
				break;
			case 1:
				j = -1;
				break;
			case 2:
				i = 1;
				break;
			case 3:
				j = 1;
				break;
			}
			if (polje.dohvatiPolje(i, j) != null && polje.dohvatiPolje(i, j).dozvoljenaFigura(this)) {
				polje.repaint();
				pomeriNaPolje(polje.dohvatiPolje(i, j));
				return;
			}
		}
	}

	
	@Override
	public void iscrtaj() {
		Graphics g = polje.getGraphics();
		g.setColor(Color.BLACK);
		racunajDimenzije();
		
		g.drawLine(0, 0, 
				sirina, visina);
		
		g.drawLine(0,  visina, 
				sirina, 0);
	}
}
