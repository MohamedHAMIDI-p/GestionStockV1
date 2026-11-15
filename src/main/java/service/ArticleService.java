package service;

import java.sql.SQLException;
import java.util.List;

import dto.ArticleDetailDto;
import dto.ArticleDto;
import model.Article;

public interface ArticleService {
	
	 void ajouterArticle(Article a) throws SQLException;
	 Article trouverArticle(String codeArticle) throws SQLException;
	 List<Article> findListArticle() throws SQLException;
	 List<ArticleDto> findListArticleDto() throws SQLException;
	 void modifierArticle(Article a);
	 void supprimerArticle(Article a);
	 ArticleDetailDto trouverDetailArticle(String codeArticle)throws SQLException;
	
	

}
