package dao;

import java.sql.SQLException;
import java.util.List;

import dto.ArticleDto;
import model.Article;

public interface ArticleDao {
	
	public void saveArticle(Article a) throws SQLException;
	public Article FindArticlebyCodeArticle(String codeArticle) throws SQLException;
	public List<Article> findAllArticle()throws SQLException;
	public List<ArticleDto> findAllArticleDto() throws SQLException;
	public void updateArticle(Article a);
	public void deleteArticle(int idArticle);
	

}
