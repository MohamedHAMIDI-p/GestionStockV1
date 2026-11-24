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

import dao.ArticleDao;
import dao.ClientDao;
import dao.FournisseurDao;
import dao.mvtStockDao;
import dao.Impl.ArticleDaoImpl;
import dao.Impl.ClientDaoImpl;
import dao.Impl.FournisseurDaoImpl;
import dao.Impl.mvtStockImpl;
import dto.mvtStockDto;
import service.MvtStockService;
import service.Impl.MvtStockServiceImpl;
import util.pageLire;

@WebServlet(name = "TypeMouvements",urlPatterns = 
{"/commandes","/receptions","/retours-fournisseurs","/ventes","/retours-clients","/corrections"})
public class TypeMouvementServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final int TAILLE_PAGE=10;
	
	private ArticleDao articleDao;
	private ClientDao clientDao;
	private FournisseurDao fournisseurDao;
	private mvtStockDao mvtStockDao;
	private MvtStockService mvtStockService;
	
	
	@Override
	public void init() throws ServletException {

		articleDao = new ArticleDaoImpl();
		clientDao = new ClientDaoImpl();
		fournisseurDao = new FournisseurDaoImpl();
		mvtStockDao = new mvtStockImpl();
		mvtStockService = new MvtStockServiceImpl(articleDao,mvtStockDao , clientDao);
		
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String chemin=request.getServletPath();
		
		switch(chemin) {	
					case "/commandes": afficherCommandes(request,response); break; 
					case "/receptions": try {
				afficherReception(request,response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} break;
					case "/retours-fournisseurs" : try {
				afficherRetourFourn(request,response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}break;
		}
	}
	
	
	//Méthode Privé 
	
	private void afficherCommandes( HttpServletRequest request,
	        HttpServletResponse response)
	                throws ServletException, IOException {
		request.getRequestDispatcher("/MvtReception.jsp").forward(request, response);
	}
	
	private void afficherReception(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,IOException, SQLException {
		List<mvtStockDto> ListMouvement;
		List<mvtStockDto> ListMouvementNouveau;
		ListMouvementNouveau=new ArrayList<mvtStockDto>();
		ListMouvement=mvtStockService.findAllMouvement();
		for(mvtStockDto mvtDto: ListMouvement) {
			if(("Reception Fournisseur").equals(mvtDto.getTypemvtStock().getLibelle())) {
				ListMouvementNouveau.add(mvtDto);
			}
		}
		int total=ListMouvementNouveau.size();
		int nbrPages=Math.max(
				1, (int)Math.ceil(total/(double)TAILLE_PAGE));
		int page=pageLire.lireEntier(request.getParameter("page"), 1);
		page=Math.min(Math.max(page, 1),nbrPages);
		int debut=(page-1)*TAILLE_PAGE;
		int fin=Math.min(debut+TAILLE_PAGE, total);
		
		request.setAttribute("reception", ListMouvementNouveau.subList(debut, fin));
		request.setAttribute("totalreception", total);
		
		request.setAttribute("pageCourante", page);
		request.setAttribute("nbPages", nbrPages);
		
		request.setAttribute("activePage", "reception");
		request.setAttribute("titrePage", "ListReception");
		request.getRequestDispatcher("/MvtReception.jsp").forward(request, response);
	}
	
	private void afficherRetourFourn(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,IOException, SQLException {
		List<mvtStockDto> ListMouvement;
		List<mvtStockDto> ListMouvementNouveau;
		ListMouvementNouveau=new ArrayList<mvtStockDto>();
		ListMouvement=mvtStockService.findAllMouvement();
		for(mvtStockDto mvtDto: ListMouvement) {
			if(("Retour Client").equals(mvtDto.getTypemvtStock().getLibelle())) {
				ListMouvementNouveau.add(mvtDto);
			}
		}
		int total=ListMouvementNouveau.size();
		int nbrPages=Math.max(
				1, (int)Math.ceil(total/(double)TAILLE_PAGE));
		int page=pageLire.lireEntier(request.getParameter("page"), 1);
		page=Math.min(Math.max(page, 1),nbrPages);
		int debut=(page-1)*TAILLE_PAGE;
		int fin=Math.min(debut+TAILLE_PAGE, total);
		
		request.setAttribute("retourFournisseur", ListMouvementNouveau.subList(debut, fin));
		request.setAttribute("totalretour", total);
		
		request.setAttribute("pageCourante", page);
		request.setAttribute("nbPages", nbrPages);
		
		request.setAttribute("activePage", "retourFournisseur");
		request.setAttribute("titrePage", "ListretourFournisseur");
		request.getRequestDispatcher("/MvtRetourFournisseur.jsp").forward(request, response);
	}

}
