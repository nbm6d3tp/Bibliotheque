package persistance;

import mediatek2022.Document;
import mediatek2022.Utilisateur;

public abstract class DocumentAbs implements Document {

	int id;
	String titre;
	String auteur;
	int idEmprunt = 0;

	/**
	 * @param id
	 * @param titre
	 * @param idEmprunt
	 */
	public DocumentAbs(int id, String titre, String auteur) {
		super();
		this.id = id;
		this.titre = titre;
		this.auteur = auteur;
	}

	public DocumentAbs(int id, String titre, String auteur, int idEmprunt) {
		super();
		this.id = id;
		this.titre = titre;
		this.idEmprunt = idEmprunt;
		this.auteur = auteur;

	}

	@Override
	public boolean disponible() {
		// TODO Auto-generated method stub
		return idEmprunt == 0;
	}

	@Override
	public void emprunt(Utilisateur u) throws Exception {

	}

	@Override
	public void retour() {
		// TODO Auto-generated method stub

	}

}
