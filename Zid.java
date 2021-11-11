package igra;

import java.awt.Color;

@SuppressWarnings("serial")
public class Zid extends Polje {

	public Zid(Mreza mreza) {
		super(mreza);
		boja = Color.LIGHT_GRAY;
		setBackground(boja);
	}

	@Override
	public boolean dozvoljenaFigura(Figura figura) {
		return false;
	}

}
