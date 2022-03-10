package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mediatek2022.Document;
import mediatek2022.Mediatheque;
import mediatek2022.PersistentMediatheque;
import mediatek2022.Utilisateur;

// classe mono-instance  dont l'unique instance est connue de la médiatheque
// via une auto-déclaration dans son bloc static

public class MediathequeData implements PersistentMediatheque {
// Jean-François Brette 01/01/2018
	static {
		Mediatheque.getInstance().setData(new MediathequeData());
	}

	private Connection connection;

	private MediathequeData() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bibliothequebd", "root", "");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void ajoutDocument(int type, Object... args) {
		// args[0] -> le titre
		// args [1] --> l'auteur
		// etc... variable suivant le type de document
		int res;
		try {
			PreparedStatement request = null;
			String sql = "insert into documents(typeDoc, titre, auteur, pourAdulte, pourEtudiant,idEmprunt) VALUES (";
			switch (type) {
			case 1:
				request = connection.prepareStatement(sql + "?, ?, ?, ?, NULL, NULL)");
				request.setInt(1, type);
				request.setString(2, (String) args[0]);
				request.setString(3, (String) args[1]);
				request.setInt(4, (int) args[2]);
				break;
			case 2:
				request = connection.prepareStatement(sql + "?, ?, ?, NULL, ?, Null)");
				request.setInt(1, type);
				request.setString(2, (String) args[0]);
				request.setString(3, (String) args[1]);
				request.setInt(4, (int) args[2]);
				break;
			case 3:
				request = connection.prepareStatement(sql + "?, ?, ?, NULL, NULL, Null)");
				request.setInt(1, type);
				request.setString(2, (String) args[0]);
				request.setString(3, (String) args[1]);
				break;
			}
			res = request.executeUpdate();
			System.out.println(res);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// va récupérer le document de numéro numDocument dans la BD
	// et le renvoie
	// si pas trouvé, renvoie null
	@Override
	public Document getDocument(int numDocument) {
		try {

			PreparedStatement requete = connection.prepareStatement("select * from documents where idDoc = ?");
			requete.setInt(1, numDocument);
			ResultSet tableResultat = requete.executeQuery();

			if (!tableResultat.next()) {
				return null;
			} else {
				int id = tableResultat.getInt("idDoc");
				String titre = tableResultat.getString("titre");
				String auteur = tableResultat.getString("auteur");
				int typeDoc = tableResultat.getInt("typeDoc");
				int idEmprunt = tableResultat.getInt("idEmprunt");
				boolean pourAdult = tableResultat.getBoolean("pourAdulte");
				boolean pourEtudiant = tableResultat.getBoolean("pourEtudiant");

				if (typeDoc == 1) {

					return new DVD(id, titre, auteur, idEmprunt, pourAdult);
				} else if (typeDoc == 2) {

					return new Livre(id, titre, auteur, idEmprunt, pourEtudiant);

				} else if (typeDoc == 3) {
					return new CD(id, titre, auteur, idEmprunt);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// va récupérer le User dans la BD et le renvoie
	// si pas trouvé, renvoie null
	@Override
	public Utilisateur getUser(String login, String password) {
		User user;
		ResultSet res;
		try {
			PreparedStatement request = connection
					.prepareStatement("select * from v_utilisateur where loginUser = ? and passwordUser = ?");
			request.setString(1, login);
			request.setString(2, password);
			res = request.executeQuery();
			if (!res.next()) {
				return null;
			} else {
				if (res.getString("DTYPE").equals("ABONNE")) {
					user = new Abonne(res.getString("nameUser"), res.getString("loginUser"),
							res.getString("passwordUser"), res.getDate("birthDate"), res.getBoolean("etudiant"),
							res.getBoolean("abonnementActif"));
				} else {
					user = new Bibliothecaire(res.getString("nameUser"), res.getString("loginUser"),
							res.getString("passwordUser"), res.getDate("dateEmbauche"));
				}
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// renvoie la liste de tous les documents disponibles de la médiathèque
	@Override
	public List<Document> tousLesDocumentsDisponibles() {
		List<Document> listdocsDoc = new ArrayList<Document>();
		Statement requeteStatiqueDVD;
		Statement requeteStatiqueCD;
		Statement requeteStatiqueLivre;

		try {
			requeteStatiqueDVD = connection.createStatement();
			requeteStatiqueCD = connection.createStatement();
			requeteStatiqueLivre = connection.createStatement();
			ResultSet tableDVD = requeteStatiqueDVD.executeQuery(
					"SELECT idDoc,titre, auteur, pourAdulte FROM documents where idEmprunt IS NULL and typeDoc=1");
			ResultSet tableCD = requeteStatiqueCD
					.executeQuery("SELECT idDoc,titre, auteur FROM documents where idEmprunt IS NULL and typeDoc=3");
			ResultSet tableLivre = requeteStatiqueLivre.executeQuery(
					"SELECT idDoc,titre, auteur, pourEtudiant FROM documents where idEmprunt IS NULL and typeDoc=2");

			if (!tableDVD.next()) {
				System.out.println("Il y a aucun DVD dans la bibliotheque");
			} else {
				do {
					Document doc = new DVD(tableDVD.getInt("idDoc"), tableDVD.getString("titre"),
							tableDVD.getString("auteur"), tableDVD.getBoolean("pourAdulte"));
					listdocsDoc.add(doc);
				} while (tableDVD.next());
			}

			if (!tableLivre.next()) {
				System.out.println("Il y a aucun Livre dans la bibliotheque");
			} else {
				do {
					Document doc = new Livre(tableLivre.getInt("idDoc"), tableLivre.getString("titre"),
							tableLivre.getString("auteur"), tableLivre.getBoolean("pourEtudiant"));
					listdocsDoc.add(doc);
				} while (tableLivre.next());
			}
			if (!tableCD.next()) {
				System.out.println("Il y a aucun CD dans la bibliotheque");
			} else {
				do {
					Document doc = new CD(tableCD.getInt("idDoc"), tableCD.getString("titre"),
							tableCD.getString("auteur"));
					listdocsDoc.add(doc);
				} while (tableCD.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listdocsDoc;
	}
}
