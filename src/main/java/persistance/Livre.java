package persistance;

public class Livre extends DocumentAbs {
	private boolean pourEtudiant;

	public Livre(int id, String titre, String auteur, boolean pourEtudiant) {
		super(id, titre, auteur);
		this.pourEtudiant = pourEtudiant;
	}

	public Livre(int id, String titre, String auteur, int idEmprunt, boolean pourEtudiant) {
		super(id, titre, auteur, idEmprunt);
		this.pourEtudiant = pourEtudiant;
	}

	@Override
	public String toString() {
		return "Livre [pourEtudiant=" + pourEtudiant + ", id=" + id + ", titre=" + titre + ", auteur=" + auteur + "]";
	}

}
