package igra;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class Igra extends Frame {
	
	public enum Smer {LEVO, GORE, DESNO, DOLE};
	public enum Rezim {IZMENA, IGRANJE};
	public enum Podloga {TRAVA, ZID};
	
	private Rezim rezim = Rezim.IZMENA;
	private Podloga podloga = Podloga.TRAVA;
	
	private Mreza mreza;
	private Panel donjiPanel, bocniPanel;
	private Button kreni = new Button("Pocni");
	private TextField brNovcica = new TextField("15");
	private Label poena = new Label("Poena: 0");
	private Label novcicaLabel;
	private boolean uToku = false;
	
	public Igra() {
		super("Igra");
		setSize(550, 500);
		setResizable(false);
		mreza = new Mreza(this);
		
		donjiPanel = new Panel(new GridLayout(1, 0));
		popuniPanele();
		add(mreza, BorderLayout.CENTER);
		add(donjiPanel, BorderLayout.SOUTH);
		add(bocniPanel, BorderLayout.EAST);
		dodajMeni();
		setVisible(true);
		
		//OSLUSKIVACI....................................................
		
		mreza.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W: mreza.pomeriIgraca(Smer.GORE); break;
				case KeyEvent.VK_S: mreza.pomeriIgraca(Smer.DOLE); break;
				case KeyEvent.VK_A: mreza.pomeriIgraca(Smer.LEVO); break;
				case KeyEvent.VK_D: mreza.pomeriIgraca(Smer.DESNO); break;
				}
			}
			
		});
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				mreza.zaustavi();
				dispose();
			}
			
		});
		
		kreni.addActionListener(e -> {
			if(!uToku && rezim == Rezim.IGRANJE) {
				mreza.inicijalizuj(brNovcica());
				mreza.pokreni();
				uToku = !uToku;
				
			} 
		});
		
		
	}
	
// SETERI I GETERI ................................................................................
	
	public Rezim getRezim() {
		return rezim;
	}

	public Podloga getPodloga() {
		return podloga;
	}
	
	public void promeniPoene(String string) {
		poena.setText(string);
	}
	
	public int brNovcica() {
		return Integer.parseInt(brNovcica.getText());
	}
	
// GUI METODI...................................................................................
	
	private void dodajMeni() {
		MenuBar bar = new MenuBar();
		setMenuBar(bar);
		Menu menu = new Menu("Rezim");
		bar.add(menu);
		MenuItem rezimIzmene = new MenuItem("Rezim izmene");
		menu.add(rezimIzmene);
		menu.addSeparator();
		MenuItem rezimIgre = new MenuItem("Rezim igranja");
		menu.add(rezimIgre);
		
		rezimIzmene.addActionListener(e -> {
			rezim = Rezim.IZMENA;
			mreza.zaustavi();
			mreza.isprazni();	
			uToku = false;
		});
		rezimIgre.addActionListener(e -> {
			rezim = Rezim.IGRANJE;
		});
		
	}
	
	private void popuniPanele() {
		Label label = new Label("Podloga: ");
		label.setAlignment(Label.CENTER);
		
		CheckboxGroup cGroup = new CheckboxGroup();
		Checkbox travaCheckbox = new Checkbox("Trava", true, cGroup);
		Checkbox zidCheckbox = new Checkbox("Zid", false, cGroup);
		
		travaCheckbox.setBackground(Color.GREEN);
		zidCheckbox.setBackground(Color.LIGHT_GRAY);
		
		travaCheckbox.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent arg0) {
				if(rezim == Rezim.IZMENA) {
					podloga = Podloga.TRAVA;
				}
			}
			
		});
		zidCheckbox.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if(rezim == Rezim.IZMENA)
					podloga = Podloga.ZID;
			}
			
		});
		
		Panel panel = new Panel(new GridLayout(0, 1));
		panel.add(travaCheckbox);
		panel.add(zidCheckbox);
		
		bocniPanel = new Panel(new GridLayout(1, 0));
		
		bocniPanel.add(label);
		bocniPanel.add(panel);
		
		novcicaLabel = new Label("Novcica: ");
		
		donjiPanel.add(novcicaLabel);
		donjiPanel.add(brNovcica);
		donjiPanel.add(poena);
		donjiPanel.add(kreni);
		
	}
	
// main() ..............................................................................
	
	public static void main(String[] args) {
		new Igra();
	}

}
