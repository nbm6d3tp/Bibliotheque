package persistance;

import java.util.Date;

public class Bibliothecaire extends User {
	private Date dateEmbauche;

	public Bibliothecaire(String name, String log, String pass, Date embauche) {
		super(name, log, pass);
		dateEmbauche = dateEmbauche;
	}

	public void ajouterDocument() {

	}

	@Override
	public boolean isBibliothecaire() {
		return true;
	}

	@Override
	public String toString() {
		return "Bibliothecaire [dateEmbauche=" + dateEmbauche + ", toString()=" + super.toString() + "]";
	}

}
