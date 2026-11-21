package dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.mvtStockDao;
import dto.mvtStockDto;
import exception.MotifCorrection;
import exception.TypeMvtStock;
import model.mvtStock;

public class mvtStockImpl implements mvtStockDao {
	
	
	@Override
	public boolean AjouterMvtStock(mvtStock mvtStock) throws SQLException {
		String sqlStock = "UPDATE article SET stockActuel = stockActuel + ? "
				+ "WHERE id = ? AND stockActuel + ? >= 0";
 
		String sqlMvt = "INSERT INTO mouvement "
				+ "(idArticle, typeMouvement, variation, motif, idFournisseur,idClient, reference, commentaire, dateMouvement) "
				+ "VALUES (?,?, ?, ?, ?, ?, ?, ?, NOW())";
 
		int variation = mvtStock.getVariation();
 
		try (Connection cn = util.utilConnexion.getConn()) {
			cn.setAutoCommit(false);
			try {
				int lignes;
				try (PreparedStatement ps = cn.prepareStatement(sqlStock)) {
					ps.setInt(1, variation);
					ps.setInt(2, mvtStock.getIdArticle());
					ps.setInt(3, variation);
					lignes = ps.executeUpdate();
				}
 
				if (lignes == 0) { 
					cn.rollback();
					return false;
				}
 
				try (PreparedStatement ps = cn.prepareStatement(sqlMvt, Statement.RETURN_GENERATED_KEYS)) {
					ps.setInt(1, mvtStock.getIdArticle());
					ps.setString(2, mvtStock.getTypemvtStock().name());
					ps.setInt(3, variation);
					ps.setString(4, mvtStock.getMotif() == null ? null : mvtStock.getMotif().name());
					Integer idFournisseur = mvtStock.getIdFournisseur();
					if (idFournisseur== null || idFournisseur <= 0) {
						ps.setNull(5, Types.INTEGER);
					} else {
						ps.setInt(5, idFournisseur);
					}
					Integer idClient=mvtStock.getIdClient();
					if(idClient==null || idClient<=0) {
						ps.setNull(6, Types.INTEGER);
					}else {
						ps.setInt(6, idClient);
					}
					ps.setString(7, mvtStock.getReference());
					ps.setString(8, mvtStock.getCommentaire());
					ps.executeUpdate();
					try (ResultSet keys = ps.getGeneratedKeys()) {
						if (keys.next()) {
							mvtStock.setId(keys.getInt(1));
						}
					}
				}
 
				cn.commit();
				return true;
 
			} catch (SQLException | RuntimeException e) {
				cn.rollback();
				throw e;
			}
		}
	}
	@Override
	public mvtStockDto consulterMvtStock(int CodeMvtArticle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<mvtStockDto> findHistorique(String codeArticle) throws SQLException {
		List<mvtStockDto> liste = new ArrayList<mvtStockDto>();
		String sql =  "SELECT m.id, m.typeMouvement, m.variation, m.motif, m.reference, m.commentaire, "
				+ "m.dateMouvement, f.nomFournisseur "
				+ "FROM mouvement m "
				+ "JOIN article a ON a.id = m.idArticle "
				+ "LEFT JOIN fournisseur f ON m.idFournisseur = f.id "
				+ "WHERE a.codeArticle = ? "
				+ "ORDER BY m.dateMouvement asc, m.id asc";
 
		try (Connection cn = util.utilConnexion.getConn();
				PreparedStatement ps = cn.prepareStatement(sql)) {
			ps.setString(1, codeArticle);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					mvtStockDto d = new mvtStockDto();
					d.setId(rs.getInt("id"));
					d.setTypemvtStock(TypeMvtStock.valueOf(rs.getString("typeMouvement")));
					String motif = rs.getString("motif");
					d.setMotif(motif == null ? null : MotifCorrection.valueOf(motif));
					d.setVariation(rs.getInt("variation"));
					d.setNomFournisseur(rs.getString("nomFournisseur"));
					d.setReference(rs.getString("reference"));
					d.setCommentaire(rs.getString("commentaire"));
					d.setDateMvtStock(rs.getTimestamp("dateMouvement"));
					liste.add(d);
				}
			}
		}
		return liste;
	}
	@Override
	public List<mvtStockDto> findAllMouvement() throws SQLException {
		
		List<mvtStockDto> lisMouvement=new ArrayList<mvtStockDto>();
		String sql="SELECT  a.codeArticle,m.typeMouvement, m.variation, m.motif, m.reference, m.commentaire,m.dateMouvement,f.cnuf, f.nomFournisseur, "
				+ " c. nomClient,c.prenomClient  "
				+ "  FROM mouvement m  "
				+ " JOIN article a ON a.id = m.idArticle "
				+ " LEFT JOIN fournisseur f ON m.idFournisseur = f.id "
				+ " LEFT JOIN client c ON m.idClient=c.id "
				+ " ORDER BY m.dateMouvement desc";
		try(Connection cn=util.utilConnexion.getConn();
				PreparedStatement ps=cn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery()){
						while(rs.next()) {
							lisMouvement.add(mapMouvement(rs));
						}
			
		}
		return lisMouvement;
	}
	@Override
	public List<mvtStockDto> rechercherMouvements(
	        String codeArticle,
	        Integer idMouvement,
	        String reference,
	        String cnuf,
	        TypeMvtStock typeMouvement,
	        java.sql.Date dateMouvement
	) throws SQLException {

	    List<mvtStockDto> liste = new ArrayList<>();

	    StringBuilder sql = new StringBuilder(
	        "SELECT m.id, a.codeArticle, m.typeMouvement, " +
	        "m.variation, m.motif, m.reference, m.commentaire, " +
	        "m.dateMouvement, f.cnuf, f.nomFournisseur, " +
	        "c.nomClient, c.prenomClient " +
	        "FROM mouvement m " +
	        "JOIN article a ON a.id = m.idArticle " +
	        "LEFT JOIN fournisseur f ON f.id = m.idFournisseur " +
	        "LEFT JOIN client c ON c.id = m.idClient " +
	        "WHERE 1=1 "
	    );

	    List<Object> parametres = new ArrayList<>();

	    if (codeArticle != null && !codeArticle.trim().isEmpty()) {
	        sql.append(" AND a.codeArticle LIKE ? ");
	        parametres.add("%" + codeArticle.trim() + "%");
	    }

	    if (idMouvement != null) {
	        sql.append(" AND m.id = ? ");
	        parametres.add(idMouvement);
	    }

	    if (reference != null && !reference.trim().isEmpty()) {
	        sql.append(" AND m.reference LIKE ? ");
	        parametres.add("%" + reference.trim() + "%");
	    }

	    if (cnuf != null && !cnuf.trim().isEmpty()) {
	        sql.append(" AND f.cnuf LIKE ? ");
	        parametres.add("%" + cnuf.trim() + "%");
	    }
	    
	    if (typeMouvement != null) {
	        sql.append(" AND m.typeMouvement = ? ");
	        parametres.add(typeMouvement.name());
	    }
	    if (dateMouvement != null) {
	        sql.append(" AND m.dateMouvement >= ? ");
	        sql.append(" AND m.dateMouvement < DATE_ADD(?, INTERVAL 1 DAY) ");

	        parametres.add(dateMouvement);
	        parametres.add(dateMouvement);
	    }

	    sql.append(" ORDER BY m.dateMouvement DESC, m.id DESC");

	    try (Connection cn = util.utilConnexion.getConn();
	         PreparedStatement ps = cn.prepareStatement(sql.toString())) {

	        for (int i = 0; i < parametres.size(); i++) {
	            ps.setObject(i + 1, parametres.get(i));
	        }

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                mvtStockDto mouvement = mapMouvement(rs);
	                mouvement.setId(rs.getInt("id"));
	                liste.add(mouvement);
	            }
	        }
	    }

	    return liste;
	}
	
	/****** Methode Prives *******/
	private mvtStockDto mapMouvement(ResultSet rs) throws SQLException {

		mvtStockDto mvtStkDto= new mvtStockDto();
		mvtStkDto.setCodeArticle(rs.getString("codeArticle"));
		mvtStkDto.setTypemvtStock(TypeMvtStock.valueOf(rs.getString("typeMouvement")));
		String motif=rs.getString("motif");
		mvtStkDto.setMotif(motif== null ? null :MotifCorrection.valueOf(motif));
		mvtStkDto.setVariation(rs.getInt("variation"));
		mvtStkDto.setReference(rs.getString("reference"));
		mvtStkDto.setCommentaire(rs.getString("commentaire"));
		mvtStkDto.setDateMvtStock(rs.getTimestamp("dateMouvement"));
		mvtStkDto.setCnuf(rs.getString("cnuf"));
		mvtStkDto.setNomFournisseur(rs.getString("nomFournisseur"));
		mvtStkDto.setNomClient(rs.getString("nomClient"));
		mvtStkDto.setPrenomClient(rs.getString("prenomClient"));
		return mvtStkDto;
	}
	
}
