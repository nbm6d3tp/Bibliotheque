package persistance;

public class CD extends DocumentAbs {

	public CD(int id, String titre, String auteur) {
		super(id, titre, auteur);
		// TODO Auto-generated constructor stub
	}

	public CD(int id, String titre, String auteur, int idEmprunt) {
		super(id, titre, auteur, idEmprunt);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CD [id=" + id + ", titre=" + titre + ", auteur=" + auteur + "]";
	}

}
