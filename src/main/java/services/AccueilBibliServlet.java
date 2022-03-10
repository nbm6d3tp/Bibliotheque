package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mediatek2022.Document;
import mediatek2022.Mediatheque;
import mediatek2022.Utilisateur;

@WebServlet(name = "AccueilBibliServlet", value = "/AccueilBibliServlet")

public class AccueilBibliServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<Document> listdocsDoc = Mediatheque.getInstance().tousLesDocumentsDisponibles();

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession(true);

		out.println("<html>");
		out.println("<head>");
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		String title = "Bonjour " + user.name();
		out.println("<title>" + title + "</title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");

		out.println("<!DOCTYPE html>\r\n" + "<html>\r\n" + "    <head>\r\n"
				+ "        <title>Mediatheque - Ajouter un document</title>\r\n" + "    </head>\r\n" + "    <body>\r\n"
				+ "        <h2>Formulaire d'ajout de document</h2>\r\n"
				+ "        <form method=\"GET\" action = \"http://localhost:8080/MediaWebJDBC/AjouterDocServlet\">\r\n"
				+ "            <label for=\"titre\">Titre du document:</label><br>\r\n"
				+ "            <input type=\"text\" name=\"titre\"><br>\r\n" + "            <br>\r\n"
				+ "            <label for=\"titre\">Auteur du document:</label><br>\r\n"
				+ "            <input type=\"text\" name=\"auteur\"><br>\r\n" + "            <br>\r\n"
				+ "            <label for=\"type\">Type de document:</label><br>\r\n"
				+ "            <label for=\"cd\">CD</label>\r\n"
				+ "            <input type=\"radio\" id=\"cd\" name=\"typeDoc\" value=1><br>\r\n"
				+ "            <label for=\"livre\">Livre</label>\r\n"
				+ "            <input type=\"radio\" id =\"livre\"name=\"typeDoc\" value=2><br>\r\n"
				+ "            <label for=\"dvd\">DVD</label>\r\n"
				+ "            <input type=\"radio\" id=\"dvd\" name=\"typeDoc\" value=3><br>\r\n"
				+ "            <br>\r\n" + "\r\n"
				+ "            <label for=\"adultes\">Réservé aux adultes</label><br>\r\n"
				+ "            <label for=\"ADOUI\">Oui</label>\r\n"
				+ "            <input type=\"radio\" id=\"ADOUI\" name=\"pourAdulte\" value=0><br>\r\n"
				+ "            <label for=\"ADNON\">Non</label>\r\n"
				+ "            <input type=\"radio\" id=\"ADNON\" name=\"pourAdulte\" value=1><br>\r\n"
				+ "            <br>\r\n" + "            <label for=\"etudiant\">Réservé aux étudiants:</label><br>\r\n"
				+ "            <label for=\"ETOUI\">OUI</label>\r\n"
				+ "            <input type=\"radio\" id=\"ETOUI\" name=\"pourEtudiant\" value=0><br>\r\n"
				+ "            <label for=\"ETNON\">NON</label>\r\n"
				+ "            <input type=\"radio\" id=\"ETNON\" name=\"pourEtudiant\" value=1><br>\r\n" + "\r\n"
				+ "            <input type=\"submit\" value=\"Ajouter\">\r\n" + "  </form>  </body>\r\n" + "</html>");
		for (Document doc : listdocsDoc) {
			out.println("<p>" + doc.toString() + "</p>");
			out.println("<br");
		}
		out.println("</body>");
		out.println("</html>");
	}

}
