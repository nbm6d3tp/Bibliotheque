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

@WebServlet(name = "AccueilAbonServlet", value = "/AccueilAbonServlet")
public class AccueilAbonServlet extends HttpServlet {
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
		for (Document doc : listdocsDoc) {
			out.println("<p>" + doc.toString() + "</p>");
			out.println("<br");
		}
		out.println("</body>");
		out.println("</html>");
	}
	/*
	 * @Override protected void doPost(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException { //
	 * doGet(request,response); // Gestion d'un appel post (utilisez postman pour
	 * tester) }
	 */
}
