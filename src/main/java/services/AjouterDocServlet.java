package services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mediatek2022.Mediatheque;
import mediatek2022.Utilisateur;

@WebServlet(name = "AjouterDocServlet", value = "/AjouterDocServlet")
public class AjouterDocServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession(true);

		out.println("<html>");
		out.println("<head>");
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		out.println("<title> Ajout un nouveau document </title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");

		String titre = req.getParameter("titre");
		String auteur = req.getParameter("auteur");
		int type = Integer.parseInt(req.getParameter("typeDoc"));
		int pourAdulte = Integer.parseInt(req.getParameter("pourAdulte"));
		int pourEtudiant = Integer.parseInt(req.getParameter("pourEtudiant"));

		switch (type) {
		case 1:
			Mediatheque.getInstance().ajoutDocument(type, titre, auteur, pourAdulte);
			break;
		case 2:
			Mediatheque.getInstance().ajoutDocument(type, titre, auteur, pourEtudiant);
			break;
		case 3:
			Mediatheque.getInstance().ajoutDocument(type, titre, auteur);
			break;
		}

		resp.sendRedirect("http://localhost:8080/MediaWebJDBC/AccueilBibliServlet");

		out.println("</body>");
		out.println("</html>");
	}

}