package web;

import java.io.IOException;
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
import service.MvtStockService;
import service.Impl.MvtStockServiceImpl;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String typeMvt = request.getParameter("typeMouvement");

		if("/receptions".equals(request.getServletPath())) {
			
			request.getRequestDispatcher("/MvtReception.jsp")
	           .forward(request, response);
		}
	
	}

}
