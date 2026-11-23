package web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FournisseurDao;
import dao.Impl.FournisseurDaoImpl;
import model.Fournisseur;
import service.FournisseurService;
import service.Impl.FournisseurServiceImpl;
import util.JsonUtil;
import util.pageLire;

@WebServlet(name = "fournisseur",urlPatterns = {"/fournisseurs"})
public class FournisseurServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final int TAILLE_PAGE=10;
	
	private FournisseurDao  fournisseurDao;
	private FournisseurService fournisseurService;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		fournisseurDao = new FournisseurDaoImpl();
		fournisseurService = new FournisseurServiceImpl(fournisseurDao);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");
			List<Fournisseur> listFou=fournisseurService.findAllFournisseur();
			if (listFou==null)
			{
				JsonUtil.repondreJson(response, HttpServletResponse.SC_BAD_REQUEST, 
						JsonUtil.erreurJson("List Fournisseur Vide", "Creer des Fournisseur"));
				return;
			}
			
			int total=listFou.size();
			int nbrPages=Math.max(
					1, (int)Math.ceil(total/(double)TAILLE_PAGE));
			int page=pageLire.lireEntier(request.getParameter("page"), 1);
			page=Math.min(Math.max(page, 1),nbrPages);
			int debut=(page-1)*TAILLE_PAGE;
			int fin=Math.min(debut+TAILLE_PAGE, total);
			
			request.setAttribute("fournisseur", listFou.subList(debut, fin));
			request.setAttribute("totalFournisseur", total);
			
			request.setAttribute("pageCourante", page);
			request.setAttribute("nbPages", nbrPages);
			
			request.setAttribute("activePage", "fournisseur");
			request.setAttribute("titrePage", "ListFournisseur");
			
			
			request.getRequestDispatcher("Fournisseur.jsp").forward(request, response);	
		}catch (SQLException e) {
			// TODO: handle exception
			throw new ServletException("Erreur Recuperatiob List Fournisseur",e);
			
		}

	}
}
