package web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ArticleDao;
import dao.ClientDao;
import dao.mvtStockDao;
import dao.Impl.ArticleDaoImpl;
import dao.Impl.ClientDaoImpl;
import dao.Impl.mvtStockImpl;
import dto.mvtStockDto;
import exception.TypeMvtStock;
import service.MvtStockService;
import service.Impl.MvtStockServiceImpl;
import util.JsonUtil;
import util.pageLire;

@WebServlet(name = "listMouvement",urlPatterns = "/listMouvement")
public class ListMouvementServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final int TAILLE_PAGE=10;
	private ArticleDao articleDao;
	private ClientDao clientDao;
	private mvtStockDao mvtDao;
	private MvtStockService mvtService;

	@Override
	public void init() 
			throws ServletException {

		articleDao=new ArticleDaoImpl();
		clientDao=new ClientDaoImpl();
		mvtDao=new mvtStockImpl();
		mvtService=new MvtStockServiceImpl(articleDao,mvtDao,clientDao);
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response)
	        throws ServletException, IOException {

	    try {
	        // Récupération des critères
	        String codeArticle = request.getParameter("codeArticle");
	        String reference = request.getParameter("reference");
	        String cnuf = request.getParameter("cnuf");
	        String typeParam = request.getParameter("typeMouvement");
	        String dateParam = request.getParameter("dateMouvement");
	        String idParam = request.getParameter("idMouvement");
	        Integer idMouvement = null;
	        
	        TypeMvtStock typeMouvement = null;
	        java.sql.Date dateMouvement = null;

	        if (typeParam != null && !typeParam.trim().isEmpty()) {
	            try {
	                typeMouvement = TypeMvtStock.valueOf(typeParam);
	            } catch (IllegalArgumentException e) {
	                request.setAttribute("erreurRecherche",
	                    "Le type de mouvement sélectionné est invalide.");
	            }
	        }
	        
	        if (dateParam != null && !dateParam.trim().isEmpty()) {
	            try {
	                dateMouvement = java.sql.Date.valueOf(dateParam.trim());
	            } catch (IllegalArgumentException e) {
	                request.setAttribute(
	                    "erreurRecherche",
	                    "La date du mouvement est invalide."
	                );
	            }
	        }
	        // Conversion de identifiant
	        if (idParam != null && !idParam.trim().isEmpty()) {
	            try {
	                idMouvement = Integer.valueOf(idParam.trim());

	                if (idMouvement <= 0) {
	                    throw new NumberFormatException();
	                }

	            } catch (NumberFormatException e) {
	                request.setAttribute(
	                    "erreurRecherche",
	                    "Le numéro de mouvement doit être un entier positif."
	                );
	            }
	        }

	        List<mvtStockDto> listeMVT;

	        // Si identifiant est invalide, ne pas lancer la recherche
	        if (request.getAttribute("erreurRecherche") != null) {
	            listeMVT = java.util.Collections.emptyList();
	        } else {
	            listeMVT = mvtService.rechercherMouvements(
	                codeArticle,
	                idMouvement,
	                reference,
	                cnuf,
	                typeMouvement,
	                dateMouvement 
	            );
	        }

	        // Réponse JSON si demandée
	        if ("json".equals(request.getParameter("format"))) {
	            JsonUtil.repondreJson(
	                response,
	                HttpServletResponse.SC_OK,
	                JsonUtil.toPrettyJson(listeMVT)
	            );
	            return;
	        }

	        // Pagination
	        int total = listeMVT.size();

	        int nbrPages = Math.max(
	            1,
	            (int) Math.ceil(total / (double) TAILLE_PAGE)
	        );

	        int page = pageLire.lireEntier(
	            request.getParameter("page"), 1
	        );

	        page = Math.min(Math.max(page, 1), nbrPages);

	        int debut = (page - 1) * TAILLE_PAGE;
	        int fin = Math.min(debut + TAILLE_PAGE, total);

	        request.setAttribute(
	            "listMouvement",
	            listeMVT.subList(debut, fin)
	        );

	        request.setAttribute("totalMouvements", total);
	        request.setAttribute("pageCourante", page);
	        request.setAttribute("nbPages", nbrPages);

	        // Conserver les critères pour le formulaire
	        request.setAttribute("codeArticle", codeArticle);
	        request.setAttribute("idMouvement", idParam);
	        request.setAttribute("reference", reference);
	        request.setAttribute("cnuf", cnuf);

	        request.setAttribute("activePage", "mouvements");
	        request.setAttribute("titrePage", "Mouvements de stock");

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new ServletException(
	            "Erreur lors de la recherche des mouvements.", e
	        );
	    }

	    request.getRequestDispatcher("/listMvt.jsp")
	           .forward(request, response);
	}

}
