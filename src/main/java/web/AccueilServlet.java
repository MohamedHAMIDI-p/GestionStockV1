package web;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
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
import model.client;
import service.ArticleService;
import service.ClientService;
import service.MvtStockService;
import service.Impl.ArticleServiceImpl;
import service.Impl.ClientServiceImpl;
import service.Impl.MvtStockServiceImpl;
import util.JsonUtil;

@WebServlet(name = "accueil",urlPatterns = "/accueil")
public class AccueilServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ArticleDao articledao = new ArticleDaoImpl();
	private mvtStockDao mvststocDao=new mvtStockImpl();
	private ClientDao clientDao= new ClientDaoImpl();
	
	private ArticleService articleService=new ArticleServiceImpl(articledao);
	
	private ClientService clientService=new ClientServiceImpl(clientDao);
	
	private MvtStockService mvtService=new MvtStockServiceImpl(articledao, mvststocDao, clientDao);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			
			List<ArticleDto> articleDtos=articleService.findListArticleDto();
			
			List<mvtStockDto> LsitMvts=mvtService.findAllMouvement();
			
			List<client> clientList=clientService.findListClientALL();
			
			LocalDate aujourdHui=LocalDate.now(ZoneId.of("Africa/Casablanca"));
			java.sql.Date dateDuJour=java.sql.Date.valueOf(aujourdHui);
			
			//recherche les movement aujoudh'ui
			
			List<mvtStockDto> mouvementDuJour=mvtService.rechercherMouvements(
					null, null, null, null, null, dateDuJour);
			request.setAttribute("derniersMouvements", mouvementDuJour);
			
			int totalArticles=articleDtos.size();
			request.setAttribute("totalArticles", totalArticles);
			
			int totalMouvements=LsitMvts.size();
			request.setAttribute("totalMouvements", totalMouvements);
			
			int totalClient=clientList.size();
			request.setAttribute("totalClients", totalClient);
			
			if("json".equals(request.getParameter("format"))) 
			{
				JsonUtil.repondreJson(response,HttpServletResponse.SC_OK,JsonUtil.toPrettyJson(articleDtos));
				JsonUtil.repondreJson(response, HttpServletResponse.SC_OK, JsonUtil.toPrettyJson(LsitMvts));
				JsonUtil.repondreJson(response, HttpServletResponse.SC_OK, JsonUtil.toPrettyJson(mouvementDuJour));
				return;
			}
			
		} catch (SQLException e) {
			throw new ServletException("Erreur lors du chargement du tableau de bord.",e);
		}
		
		
		
		
		request.setAttribute("totalFournisseurs", 0);
		request.setAttribute("commandesEnAttente", 0);
		request.setAttribute("articlesStockFaible", 0);
		
		
		request.setAttribute("activePage", "accueil");
		request.setAttribute("titrePage", "Accueil");
		request.getRequestDispatcher("accueil.jsp").forward(request, response);
		
		
	}
}
