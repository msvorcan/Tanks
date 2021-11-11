package igra;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.List;

import igra.Igra.Rezim;
import igra.Igra.Smer;

@SuppressWarnings("serial")
public class Mreza extends Panel implements Runnable {
	
	private int dim;
	private Polje[][] polja;
	private Igra igra;
	private Igrac igrac;
	private Thread nit;
	
	private int poeni = 0;
	
	private List<Igrac> igraci = new ArrayList<Igrac>();
	private List<Novcic> novcici = new ArrayList<Novcic>();
	private List<Tenk> tenkovi = new ArrayList<Tenk>();
	
// KONSTRUKTORI............................................................
	
	public Mreza(int dim, Igra igra) {
		super(new GridLayout(dim, dim, 0, 0));
		this.dim = dim;
		this.igra = igra;
		polja = new Polje[dim][dim];
		dodajPolja();
	}

	public Mreza(Igra igra) {
		super(new GridLayout(17, 17, 0, 0));
		this.dim = 17;
		this.igra = igra;
		polja = new Polje[dim][dim];
		dodajPolja();
	}

// GETERI.................................................................
	
	public List<Igrac> getIgraci() {
		return igraci;
	}

	public List<Novcic> getNovcici() {
		return novcici;
	}

	public List<Tenk> getTenkovi() {
		return tenkovi;
	}

	public Polje[][] getPolja() {
		return polja;
	}

	public int getDim() {
		return dim;
	}
		
	public int getPoeni() {
		return poeni;
	}
	
// DODAVANJE POLJA............................................................	

	void dodajPolja() {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				int r = (int)(Math.random()*100);
				if(r < 20)
					add(polja[i][j] = new Zid(this));
				else 
					add(polja[i][j] = new Trava(this));
				
				polja[i][j].repaint();
				
				
			}
		}
	}
	
	public Rezim dohvatiRezim() {
		return igra.getRezim();
	}
	
	public void zameniPolje(Polje polje) {
		int a = 0, b = 0;
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if(polje == polja[i][j]) {
					a = i;
					b = j;
				}
			}
		}
		if (igra.getRezim() == Rezim.IZMENA) {
			int x = polja[a][b].getX();
			int y = polja[a][b].getY();
			int sirina = polja[a][b].getWidth();
			int duzina = polja[a][b].getHeight();
			remove(polja[a][b]);
			switch (igra.getPodloga()) {
			case TRAVA:
				polja[a][b] = new Trava(Mreza.this);
				break;
			case ZID:
				polja[a][b] = new Zid(Mreza.this);
				break;
			}
			polja[a][b].setSize(sirina, duzina);
			polja[a][b].setLocation(x, y);
			add(polja[a][b], b*dim + a);
			polja[a][b].repaint();
		}
	}
	
	
//PONASANJE TOKOM IGRE.........................................................
	
	public void pokreni() {
		nit = new Thread(this);
		nit.start();
		for (Tenk tenk : tenkovi) {
			tenk.pokreni();
		}
		requestFocus();
	}
	
	public void zaustavi() {
		for (Tenk tenk : tenkovi) {
			tenk.zaustavi();
		}
		if(nit != null) {
			nit.interrupt();
					
		}
	}
	
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				Thread.sleep(40);
				azuriraj();
				repaint();
			}	
		} catch (InterruptedException e) {}
		
	}
	private void azuriraj() {
labela:	for (Novcic novcic : novcici) {
			if(igrac.polje == novcic.polje) {
				novcici.remove(novcic);
				novcic = null;
				poeni++;
				igrac.polje.repaint();
				String string = "Poena: " + poeni;
				igra.promeniPoene(string);
				if(novcici.isEmpty())
				zaustavi();
				break labela;
			}
		}
	
		for(Tenk tenk : tenkovi) {
			if(igrac.polje == tenk.polje) {
				zaustavi();
				Polje tmp = igrac.getPolje();
				igraci.remove(igrac);  
				tmp.repaint();
			}
		}
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if(igrac != null)
			igrac.iscrtaj();
		for (Novcic novcic : novcici) {
			if(novcic != null)
				novcic.iscrtaj();
		}
		for (Tenk tenk : tenkovi) {
			if(tenk != null)
				tenk.iscrtaj();
		}
		
	}

	public void pomeriIgraca(Smer smer) {
		Polje polje, staro = null;
		if(!igraci.contains(igrac)) {
			staro = igrac.polje;
			zaustavi();
			staro.repaint();
			return;
		}
		switch (smer) {
		
		case LEVO:
			polje = igrac.polje.dohvatiPolje(0, -1);
			if(polje != null && polje.dozvoljenaFigura(igrac)) {
				staro = igrac.polje;
				igrac.pomeriNaPolje(polje);			
			}
			break;

		case GORE:
			polje = igrac.polje.dohvatiPolje(-1, 0);
			if(polje != null && polje.dozvoljenaFigura(igrac)) {
				staro = igrac.polje;
				igrac.pomeriNaPolje(polje);
			}
			break;
			
		case DESNO:
			polje = igrac.polje.dohvatiPolje(0, 1);
			if(polje != null && polje.dozvoljenaFigura(igrac)) {	
				staro = igrac.polje;
				igrac.pomeriNaPolje(polje);
			}
			break;
			
		case DOLE:
			polje = igrac.polje.dohvatiPolje(1, 0);
			if(polje != null && polje.dozvoljenaFigura(igrac)) {
				staro = igrac.polje;
				igrac.pomeriNaPolje(polje);
			}
			break;
		}
		if(staro != null)
			staro.repaint();
	}
	
//INICIJALIZACIJA................................................................
	
	public void inicijalizuj(int brojac) {
		int brNovcica = brojac;
		while(brNovcica > 0) {			
			int kolona = (int)(Math.random()*dim);
			int vrsta = (int)(Math.random()*dim);
			Novcic novcic = new Novcic(polja[kolona][vrsta]);
			boolean slobodan = true;
			if(!polja[kolona][vrsta].dozvoljenaFigura(novcic))
				slobodan = false;
labela:		for (Novcic n : novcici) {
				if(novcic.getPolje() == n.getPolje()) {	
					slobodan = false;
					break labela;
				}
			}
			if(slobodan) {
				novcici.add(novcic);
				brNovcica--;
			}
					
		}
		int brTenkova = brojac / 3;
		while (brTenkova > 0) {
			int kolona = (int)(Math.random()*dim);
			int vrsta = (int)(Math.random()*dim);
			Tenk tenk = new Tenk(polja[kolona][vrsta]);
			boolean slobodan = true;
			if(!polja[kolona][vrsta].dozvoljenaFigura(tenk))
				slobodan = false;
labela:			for (Novcic n : novcici) {
				if(tenk.getPolje() == n.getPolje()) {
					slobodan = false;
					break labela;
				}
			}
			if(slobodan) {
labela:			for (Tenk t : tenkovi) {
					if(tenk.getPolje() == t.getPolje()) {
						slobodan = false;
						break labela;
					}
				}
			}
			if (slobodan) {
				tenkovi.add(tenk);
				brTenkova--;
			}
			
		}
labela:		while(true) {
			int kolona = (int)(Math.random()*dim);
			int vrsta = (int)(Math.random()*dim);
			Igrac igrac2 = new Igrac(polja[kolona][vrsta]);
			boolean slobodan = true;
			if(!polja[kolona][vrsta].dozvoljenaFigura(igrac2))
				slobodan = false;
forlabela:		for (Novcic n : novcici) {
				if(igrac2.getPolje() == n.getPolje()) {
					slobodan = false;
					break forlabela;
				}
			}
			if(slobodan)
forlabela:			for (Tenk t : tenkovi) {
					if(igrac2.getPolje() == t.getPolje()) {
						slobodan = false;
						break forlabela;
					}
				}
			if (slobodan) {
				igrac = igrac2;
				igraci.add(igrac);
				break labela;
			}
		}
		
	repaint();
	}
	
	void isprazni() {
		igraci.clear();
		igrac = null;
		tenkovi.clear();
		novcici.clear();
		
		for (Polje[] poljes : polja) {
			for (Polje polje : poljes) {
				polje.repaint();
			}
		}
		poeni = 0;
	}

}
