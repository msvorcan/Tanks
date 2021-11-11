package igra;

public abstract class Figura {

	protected Polje polje;
	protected int sirina, visina;

	public Figura(Polje polje) {
		super();
		this.polje = polje;
	}

	public Polje getPolje() {
		return polje;
	}
	
	protected void racunajDimenzije() {
		sirina = polje.getWidth();
		visina = polje.getHeight();
		
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass().getSuperclass() != obj.getClass().getSuperclass())
			return false;
		Figura other = (Figura) obj;
		if (polje == null) {
			if (other.polje != null)
				return false;
		} else if (polje != other.polje)
			return false;
		return true;
	}
	
	public void pomeriNaPolje(Polje polje) {
		this.polje = polje;
	}
	
	public abstract void iscrtaj();
	
}
