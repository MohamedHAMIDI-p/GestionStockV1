package service.Impl;

import java.sql.SQLException;
import java.util.List;

import dao.ArticleDao;
import dto.ArticleDetailDto;
import dto.ArticleDto;
import exception.EntityNotFoundException;
import exception.ErreurCodes;
import exception.InvalidEntityException;
import model.Article;
import service.ArticleService;

public class ArticleServiceImpl implements ArticleService {
	
	private final ArticleDao articleDao;

	
	public ArticleServiceImpl(ArticleDao articleDao) {
		super();
		this.articleDao = articleDao;
	}
	
	@Override
	public void ajouterArticle(Article a) throws SQLException {

		valider(a);
		
		 Article idA=articleDao.FindArticlebyCodeArticle(a.getCodeArticle());
		 if(idA!=null) {
				throw new InvalidEntityException("Code Article : "+a.getCodeArticle()+" déja existe ", ErreurCodes.ARTICLE_ALREADY_IN_USE);
			}
			else {
				articleDao.saveArticle(a);
			}
		
	}

	private void valider(Article a) throws SQLException {
		
		if(a==null) {
			throw new InvalidEntityException("Article manquant", ErreurCodes.ARTICLE_NOT_VALID);
		}
		
		if (a.getCodeArticle()==null || a.getCodeArticle().trim().isEmpty() ) {
			throw new InvalidEntityException("Code article Obligatoire",ErreurCodes.ARTICLE_NOT_VALID);
		}
		if (a.getDesignation()==null || a.getDesignation().trim().isEmpty()) {
			throw new InvalidEntityException("Designation est obligatoire", ErreurCodes.CATEGORY_NOT_FOUND);
		}
		
		a.setCodeArticle(a.getCodeArticle().trim());
		a.setDesignation(a.getDesignation().trim());
	}

	@Override
	public Article trouverArticle(String codeArticle) throws SQLException {

		Article a =articleDao.FindArticlebyCodeArticle(codeArticle);
		
		if (a==null) {
			throw new EntityNotFoundException("Article introuvable :"+codeArticle, ErreurCodes.ARTICLE_NOT_FOUND);
		}
		return a;
	}
	
	

	@Override
	public void modifierArticle(Article a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void supprimerArticle(Article a) {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public List<Article> findListArticle() throws SQLException {
		
		return articleDao.findAllArticle();
	}

	@Override
	public List<ArticleDto> findListArticleDto() throws SQLException {
		return articleDao.findAllArticleDto();
	}

	@Override
	public ArticleDetailDto trouverDetailArticle(String codeArticle) throws SQLException {
		
		ArticleDetailDto a =articleDao.FindArtDetbyCodeArticle(codeArticle);
		
		if (a==null) {
			throw new EntityNotFoundException("Article introuvable :"+codeArticle, ErreurCodes.ARTICLE_NOT_FOUND);
		}
		return a;
	}
}
