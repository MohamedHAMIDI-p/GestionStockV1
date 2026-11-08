package web;
 
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import dao.ArticleDao;
import dao.Impl.ArticleDaoImpl;
import dao.Impl.mvtStockImpl;
import dto.ArticleDetailDto;
import dto.mvtStockDto;
import exception.EntityNotFoundException;
import exception.InvalidEntityException;
import model.Article;
import exception.MotifCorrection;
import exception.TypeMvtStock;
import service.ArticleService;
import service.MvtStockService;
import service.Impl.ArticleServiceImpl;
import service.Impl.MvtStockServiceImpl;
 

@WebServlet(name = "mvtStock", urlPatterns = { "/mouvements" })
public class controleurServletMvtStock extends HttpServlet {
 
	private static final long serialVersionUID = 1L;
 
	private ArticleService articleService;
	private MvtStockService mvtStockService;
 
	@Override
	public void init() throws ServletException {
		ArticleDao articleDao = new ArticleDaoImpl();
		articleService = new ArticleServiceImpl(articleDao);
		mvtStockService = new MvtStockServiceImpl(articleDao, new mvtStockImpl());
	}
 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		afficher(req, resp, null);
	}
 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
 
		String code = req.getParameter("codeArticle");
 
		try {
			mvtStockService.enregistrerMvtStock(
					code,
					lireType(req.getParameter("typeMvt")),
					lireEntier(req.getParameter("quantite")),
					lireMotif(req.getParameter("motif")),
					null,                                         // fournisseur : celui de l'article par défaut
					req.getParameter("reference"),
					req.getParameter("commentaire"));
 
			// Redirection après un POST réussi : un rechargement de la page ne renvoie pas le mouvement
			resp.sendRedirect(req.getContextPath() + "/mouvements?codeArticle="
					+ URLEncoder.encode(code.trim(), "UTF-8") + "&ok=1");
 
		} catch (InvalidEntityException | EntityNotFoundException e) {
			afficher(req, resp, e.getMessage());                   // l'erreur s'affiche dans la page
 
		} catch (SQLException | RuntimeException e) {
			log("Erreur lors de l'enregistrement d'un mouvement sur l'article " + code, e);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
 
	// ---------- affichage ----------
 
	private void afficher(HttpServletRequest req, HttpServletResponse resp, String erreur)
			throws ServletException, IOException {
 
		String code = req.getParameter("codeArticle");
		if (code == null || code.trim().isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Code article obligatoire");
			return;
		}
 
		try {
			ArticleDetailDto article = articleService.trouverDetailArticle(code.trim());
			List<mvtStockDto> mouvements = mvtStockService.historique(code.trim());
 
			req.setAttribute("article", article);
			req.setAttribute("mouvements", mouvements);
			req.setAttribute("types", TypeMvtStock.values());
			req.setAttribute("motifs", MotifCorrection.values());
			req.setAttribute("typeChoisi", lireType(req.getParameter("typeMvt")));
			req.setAttribute("motifChoisi", lireMotif(req.getParameter("motif")));
			req.setAttribute("erreur", erreur);
			req.setAttribute("ok", "1".equals(req.getParameter("ok")));
 
			if (erreur != null) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			req.getRequestDispatcher("/ListeMouvements.jsp").forward(req, resp);
 
		} catch (EntityNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
		} catch (SQLException | RuntimeException e) {
			log("Erreur lors de la lecture des mouvements de l'article " + code, e);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
 
	// ---------- lecture des paramètres (une valeur invalide donne null ou 0, le service la refuse) ----------
 
	private TypeMvtStock lireType(String valeur) {
		if (valeur == null || valeur.trim().isEmpty()) {
			return null;
		}
		try {
			return TypeMvtStock.valueOf(valeur.trim());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
 
	private MotifCorrection lireMotif(String valeur) {
		if (valeur == null || valeur.trim().isEmpty()) {
			return null;
		}
		try {
			return MotifCorrection.valueOf(valeur.trim());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
 
	private int lireEntier(String valeur) {
		if (valeur == null) {
			return 0;
		}
		try {
			return Integer.parseInt(valeur.trim());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
 