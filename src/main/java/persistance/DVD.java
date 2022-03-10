package persistance;

public class DVD extends DocumentAbs {
	private boolean pourAdult;

	public DVD(int id, String titre, String auteur, boolean pourAdult) {
		super(id, titre, auteur);
		this.pourAdult = pourAdult;
	}

	public DVD(int id, String titre, String auteur, int idEmprunt, boolean pourAdult) {
		super(id, titre, auteur, idEmprunt);
		this.pourAdult = pourAdult;
	}

	@Override
	public String toString() {
		return "DVD [pourAdult=" + pourAdult + ", id=" + id + ", titre=" + titre + ", auteur=" + auteur + "]";
	}

}
