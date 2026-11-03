package service;

import java.sql.SQLException;
import java.util.List;

import dto.ArticleDetailDto;
import dto.ArticleDto;
import model.Article;

public interface ArticleService {
	
	public void ajouterArticle(Article a) throws SQLException;
	public Article trouverArticle(String codeArticle) throws SQLException;
	public List<Article> findListArticle() throws SQLException;
	public List<ArticleDto> findListArticleDto() throws SQLException;
	public void modifierArticle(Article a);
	public void supprimerArticle(Article a);
	public ArticleDetailDto trouverDetailArticle(String codeArticle)throws SQLException;
	
	

}
