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
import dao.Impl.ArticleDaoImpl;
import dto.ArticleDto;
import model.Article;
import service.ArticleService;
import service.Impl.ArticleServiceImpl;
import util.JsonUtil;

@WebServlet(name = "accueil",urlPatterns = "/accueil")
public class AccueilServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ArticleDao dao = new ArticleDaoImpl();
	private ArticleService articleService=new ArticleServiceImpl(dao);;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			List<ArticleDto> articleDtos=articleService.findListArticleDto();
			if("json".equals(request.getParameter("format"))) 
			{
				JsonUtil.repondreJson(response,HttpServletResponse.SC_OK,JsonUtil.toPrettyJson(articleDtos));
				return;
			}
			int totalArticles=articleDtos.size();
			request.setAttribute("totalArticles", totalArticles);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		request.setAttribute("totalClients", 0);
		request.setAttribute("totalFournisseurs", 0);
		request.setAttribute("commandesEnAttente", 0);
		request.setAttribute("articlesStockFaible", 0);
		request.setAttribute("totalMouvements", 0);
		
		request.setAttribute("activePage", "accueil");
		request.setAttribute("titrePage", "Accueil");
		request.getRequestDispatcher("accueil.jsp").forward(request, response);
		
		
	}
}
