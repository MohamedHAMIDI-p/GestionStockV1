package dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ArticleDao;
import dto.ArticleDto;
import exception.TvaArticle;
import model.Article;

public class ArticleDaoImpl implements ArticleDao {

	@Override
	public void saveArticle(Article a) throws SQLException {

		String sql="INSERT INTO article (codeArticle,designation,idCategory,idFournisseur,dateCreation,dateModification,prixAchatArticle,prixVenteArticle,prixRevient,tvaArticle) VALUES (?,?,?,?,NOW(), NOW(),?,?,?,?)";
		Connection cn=util.utilConnexion.getConn();
		PreparedStatement ps = cn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, a.getCodeArticle());
		ps.setString(2, a.getDesignation());
		ps.setInt(3, a.getIdCategory());
		ps.setInt(4, a.getIdFournisseur());
		//ps.setTimestamp(5, (Timestamp) a.getDateCreation());
		//ps.setTimestamp(6, (Timestamp) a.getDateModification());
		ps.setDouble(5, a.getPrixAchatArticle());
		ps.setDouble(6, a.getPrixVenteArticle());
		ps.setDouble(7, a.getPrixRevient());
		ps.setDouble(8, a.getTvaArticle().getTaux());
		ps.executeUpdate();
		ResultSet keys=ps.getGeneratedKeys();
		if(keys.next()) {
			a.setId(keys.getInt(1));
		}
		ps.close();
	}

	@Override
	public Article FindArticlebyCodeArticle(String codeArticle) throws SQLException {
		
		String sql="select * from article where codeArticle = ?";
		try(Connection cn=util.utilConnexion.getConn();
		PreparedStatement ps =cn.prepareStatement(sql))
		{	
		ps.setString(1, codeArticle);
		try (ResultSet rs =ps.executeQuery())
		{
			return rs.next()? mapArticle(rs): null;
		}
		}
	}

	@Override
	public List<Article> findAllArticle() throws SQLException {
		
		List<Article> listArticle=new ArrayList<Article>();
		String sql="select * from article order by codeArticle asc";
		try (Connection cn=util.utilConnexion.getConn();
				PreparedStatement ps=cn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()){
					while(rs.next()) {
						listArticle.add(mapArticle(rs));
					}
				}
		return listArticle;
	}

	@Override
	public List<ArticleDto> findAllArticleDto() throws SQLException {

		List<ArticleDto> lisArtdto = new ArrayList<ArticleDto>();
		String sql="SELECT a.codeArticle, a.designation, "
	            + "a.prixVenteArticle, a.tvaArticle, "
	            + "f.cnuf,c.nomCategory, f.nomFournisseur "
	            + "FROM article a "
	            + "LEFT JOIN category c ON a.idCategory = c.id "
	            + "LEFT JOIN fournisseur f ON a.idFournisseur = f.id "
	            + "ORDER BY a.codeArticle ASC";
		
		try (Connection cn=util.utilConnexion.getConn();
				PreparedStatement ps =cn.prepareStatement(sql);
						ResultSet rs=ps.executeQuery()){
			while(rs.next()) {
				ArticleDto artDto = new ArticleDto();
				artDto.setCodeArticle(rs.getString("codeArticle"));
				artDto.setDesignation(rs.getString("designation"));
				artDto.setPrixVente(rs.getDouble("prixVenteArticle"));
				artDto.setTvaArt(TvaArticle.fromTaux(rs.getInt("tvaArticle")));
				artDto.setCnufFournisseur(rs.getString("cnuf"));
				artDto.setNomCategory(rs.getString("nomCategory"));
				artDto.setNomFournisseur(rs.getString("nomFournisseur"));
				lisArtdto.add(artDto);
			}
		}
		
		return lisArtdto;
	}

	@Override
	public void updateArticle(Article a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteArticle(int idArticle) {
		// TODO Auto-generated method stub
		
	}
	
	private Article mapArticle(ResultSet rs) throws SQLException {
		
		Article a=new Article();
		a.setId(rs.getInt("id"));
		a.setCodeArticle(rs.getString("codeArticle"));
		a.setDesignation(rs.getString("designation"));
		a.setIdCategory(rs.getInt("idCategory"));
		a.setIdFournisseur(rs.getInt("idFournisseur"));
		a.setDateCreation(rs.getTimestamp("dateCreation"));
		a.setDateModification(rs.getTimestamp("dateModification"));
		a.setPrixAchatArticle(rs.getDouble("prixAchatArticle"));
		a.setPrixVenteArticle(rs.getDouble("prixVenteArticle"));
		a.setPrixRevient(rs.getDouble("prixRevient"));
		a.setTvaArticle(TvaArticle.fromTaux(rs.getInt("tvaArticle")));
		return a;
		
	}

	
}
