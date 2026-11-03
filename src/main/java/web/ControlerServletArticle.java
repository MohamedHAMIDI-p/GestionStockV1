package web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Impl.ArticleDaoImpl;
import dto.ArticleDetailDto;
import dto.ArticleDto;
import exception.EntityNotFoundException;
import exception.ErreurCodes;
import model.Article;
import service.ArticleService;
import service.Impl.ArticleServiceImpl;
import util.JsonUtil;
import util.pageLire;

@WebServlet(name="article",urlPatterns={"/article","/articles"})
public class ControlerServletArticle extends HttpServlet {
	
	private static final long serialVersionUID= 1L;
	private static final int TAILLE_PAGE=10;
	
	private ArticleService articleService;
	
	@Override
	public void init() throws ServletException {

		articleService = new ArticleServiceImpl(new ArticleDaoImpl());
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		if("/articles".equals(req.getServletPath())) 
		{
			try {
				afficherListArticle(req,resp);
			} catch (ServletException | IOException | SQLException e) {
				log("Erreur de lire la liste des articles",e);
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}else
		{
			afficherDetArticle(req,resp);
		}	
	}
	
	//listearticle 
	private void afficherListArticle(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException,SQLException
	{
		List<ArticleDto> articleDtos=articleService.findListArticleDto();
		if("json".equals(req.getParameter("format"))) 
		{
			JsonUtil.repondreJson(resp,HttpServletResponse.SC_OK,JsonUtil.toPrettyJson(articleDtos));
			return;
		}
		int total=articleDtos.size();
		int nbrPages=Math.max(1, (int)Math.ceil(total/(double)TAILLE_PAGE));
		int page=pageLire.lireEntier(req.getParameter("page"), 1);
		page=Math.min(Math.max(page, 1),nbrPages);
		int debut=(page-1)*TAILLE_PAGE;
		int fin=Math.min(debut+TAILLE_PAGE, total);
		req.setAttribute("articles",articleDtos.subList(debut,fin));
		//req.setAttribute("articles",articleDtos);
		req.setAttribute("totalArticles", total);
		req.setAttribute("pageCourante", page);
		req.setAttribute("nbPages", nbrPages);
		req.getRequestDispatcher("ListeArticles.jsp").forward(req, resp);
	}
	//DetailsArticle

private void afficherDetArticle(HttpServletRequest req,
        HttpServletResponse resp)
        throws ServletException, IOException {

    String code = req.getParameter("codeArticle");

    int status = HttpServletResponse.SC_OK;
    String json = "";

    ArticleDetailDto article = null;
    String erreur = null;

    if (code == null || code.trim().isEmpty()) {

        status = HttpServletResponse.SC_BAD_REQUEST;
        erreur = "Code article obligatoire";

        json = JsonUtil.erreurJson(erreur, "CODE_ARTICLE_OBLIGATOIRE");

    } else {

        try {

            article = articleService.trouverDetailArticle(code.trim());

            json = JsonUtil.toPrettyJson(article);

        } catch (EntityNotFoundException e) {

            status = HttpServletResponse.SC_NOT_FOUND;

            log("Article non trouvé : " + code, e);

            erreur = e.getMessage();

            json = JsonUtil.erreurJson(
                    e.getMessage(),
                    e.getErrorCode().name()
            );

        } catch (SQLException | RuntimeException e) {

            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

            log("Erreur lors de la lecture de l'article : " + code, e);

            erreur = "Erreur serveur BDD";

            json = JsonUtil.erreurJson(erreur, null);
        }
    }

    // Mode JSON
    if ("json".equals(req.getParameter("format"))) {

        JsonUtil.repondreJson(resp, status, json);
        return;
    }

    // Mode JSP
    resp.setStatus(status);

    if (article != null) {
        req.setAttribute("article", article);
    }

    if (erreur != null) {
        req.setAttribute("erreur", erreur);
    }

    req.getRequestDispatcher("/VueArticle.jsp").forward(req, resp);
}

	
}
