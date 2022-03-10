package services;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(urlPatterns = "/notused", loadOnStartup = 1)
public class InitServlet extends HttpServlet {
	/**
	 *
	 */

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		try {
			Class.forName("persistance.MediathequeData");
		} catch (ClassNotFoundException | IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
		}
		System.out.println("****************************************************************");

	}

}
