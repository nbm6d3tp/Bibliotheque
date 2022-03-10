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

@WebServlet(name = "VerifServlet", value = "/VerifServlet")

public class VerifServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		HttpSession session = req.getSession(true);

		out.println("<html>");

		out.println("<head>");
		String title = "Login/password enregsitré";
		out.println("<title>" + title + "</title>");
		out.println("</head>");

		out.println("<body bgcolor=\"white\">");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		// vérification sur la BD
		try {
			Utilisateur user = Mediatheque.getInstance().getUser(login, password);
			if (user == null) {
				out.println("Vous n'avez pas été reconnu");
				out.println("<A href = 'http://localhost:8080/MediaWebJDBC/'> Re-saisir</A");
			} else {
				session.setAttribute("user", user);
				if (user.isBibliothecaire()) {
					resp.sendRedirect("http://localhost:8080/MediaWebJDBC/AccueilBibliServlet");

				} else {
					resp.sendRedirect("http://localhost:8080/MediaWebJDBC/AccueilAbonServlet");
				}

			}
		} catch (Exception e) {
			out.println(e);
		}
		out.println("</body>");

		out.println("</html>");
	}
}
