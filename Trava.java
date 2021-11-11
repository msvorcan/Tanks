package igra;

import java.awt.Color;

@SuppressWarnings("serial")
public class Trava extends Polje {

	public Trava(Mreza mreza) {
		super(mreza);
		boja = Color.GREEN;
		setBackground(boja);
	}

	@Override
	public boolean dozvoljenaFigura(Figura figura) {
		return true;
	}

}
