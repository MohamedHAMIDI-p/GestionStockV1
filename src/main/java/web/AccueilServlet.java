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
import dto.ArticleDto;
import dto.mvtStockDto;
import model.Article;
import service.ArticleService;
import service.MvtStockService;
import service.Impl.ArticleServiceImpl;
import service.Impl.MvtStockServiceImpl;
import util.JsonUtil;

@WebServlet(name = "accueil",urlPatterns = "/accueil")
public class AccueilServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final int TAILLE_PAGE=5;
	private ArticleDao dao = new ArticleDaoImpl();
	private mvtStockDao mvststocDao=new mvtStockImpl();
	private ClientDao clientDao= new ClientDaoImpl();
	private ArticleService articleService=new ArticleServiceImpl(dao);
	private MvtStockService mvtService=new MvtStockServiceImpl(dao, mvststocDao, clientDao);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			List<ArticleDto> articleDtos=articleService.findListArticleDto();
			List<mvtStockDto> LsitMvts=mvtService.findAllMouvement();
			if("json".equals(request.getParameter("format"))) 
			{
				JsonUtil.repondreJson(response,HttpServletResponse.SC_OK,JsonUtil.toPrettyJson(articleDtos));
				return;
			}
			int totalArticles=articleDtos.size();
			int totalMouvements=LsitMvts.size();
			request.setAttribute("totalArticles", totalArticles);
			request.setAttribute("totalMouvements", totalMouvements);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		request.setAttribute("totalClients", 0);
		request.setAttribute("totalFournisseurs", 0);
		request.setAttribute("commandesEnAttente", 0);
		request.setAttribute("articlesStockFaible", 0);
		
		
		request.setAttribute("activePage", "accueil");
		request.setAttribute("titrePage", "Accueil");
		request.getRequestDispatcher("accueil.jsp").forward(request, response);
		
		
	}
}
