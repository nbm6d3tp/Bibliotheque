package persistance;

import java.util.Date;

public class Abonne extends User {

	private boolean estEtudiant;
	private boolean abonnementActif;
	private Date birthDate;
	// private ArrayList<Document> documents;

	public Abonne(String name, String log, String pass, Date birth, boolean student, boolean abonne) {
		super(name, log, pass);
		estEtudiant = student;
		abonnementActif = abonne;
		birthDate = birth;
		// documents = new ArrayList<Document>();
	}

	@Override
	public Object[] data() {
		Object[] data = new Object[5];
//		data[0] = super.getId();
		data[1] = estEtudiant;
		data[2] = abonnementActif;
		data[3] = birthDate;
		// data[4] = documents;
		return data;
	}

	@Override
	public boolean isBibliothecaire() {
		return false;
	}

	@Override
	public String toString() {
		return "Abonne [estEtudiant=" + estEtudiant + ", abonnementActif=" + abonnementActif + ", birthDate="
				+ birthDate + ", toString()=" + super.toString() + "]";
	}

}
