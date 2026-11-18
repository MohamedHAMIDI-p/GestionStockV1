package web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "accueil",urlPatterns = "/accueil")
public class AccueilServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("totalArticles", 0);
		request.setAttribute("totalClients", 0);
		request.setAttribute("totalFournisseurs", 0);
		request.setAttribute("commandesEnAttente", 0);
		request.setAttribute("articlesStockFaible", 0);
		request.setAttribute("totalMouvements", 0);
		
		request.setAttribute("activePage", "accueil");
		request.setAttribute("titrePage", "Tableau de bord");
		request.getRequestDispatcher("accueil.jsp").forward(request, response);
		
		
	}
}
