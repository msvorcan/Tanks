package igra;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import igra.Igra.Rezim;

@SuppressWarnings("serial")
public abstract class Polje extends Canvas {
	
	private Mreza mreza;
	protected Color boja;

	public Polje(Mreza mreza) {
		super();
		setBackground(boja);
		this.mreza = mreza;
		
		dodajOsluskivac();
	}

	public Mreza getMreza() {
		return mreza;
	}
	
	public Polje dohvatiPolje(int x, int y) {
		int kolona = pozicija()[0] + x;
		int vrsta = pozicija()[1] + y;
		if(kolona < 0 || kolona >= mreza.getDim() || vrsta < 0 || vrsta >= mreza.getDim())
			return null;
		return mreza.getPolja()[kolona][vrsta];
	}
	
	public int[] pozicija() {
		for (int i = 0; i < mreza.getDim(); i++) {
			for (int j = 0; j < mreza.getDim(); j++) {
				if(this == mreza.getPolja()[i][j]) {
					int a[] = {i, j};
					return a;
				}
			}
		}
		return new int[1];
	}

	public abstract boolean dozvoljenaFigura(Figura figura);
	
	private void dodajOsluskivac() {
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (mreza.dohvatiRezim() == Rezim.IZMENA) {
					mreza.zameniPolje(Polje.this);
				}
			}
			
		});
	}
}
