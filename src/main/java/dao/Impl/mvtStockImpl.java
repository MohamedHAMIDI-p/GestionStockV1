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
				+ "(idArticle, typeMouvement, variation, motif, idFournisseur, reference, commentaire, dateMouvement) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
 
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
					ps.setString(6, mvtStock.getReference());
					ps.setString(7, mvtStock.getCommentaire());
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
	public List<mvtStockDto> findHistorique(String idArticle) throws SQLException {
		List<mvtStockDto> liste = new ArrayList<mvtStockDto>();
		String sql = "SELECT m.id, m.typeMouvement, m.variation, m.motif, m.reference, m.commentaire, "
				+ "m.dateMouvement, f.nomFournisseur "
				+ "FROM mouvement m "
				+ "LEFT JOIN fournisseur f ON m.idFournisseur = f.id "
				+ "WHERE m.idArticle = ? "
				+ "ORDER BY m.dateMouvement DESC, m.id DESC";
 
		try (Connection cn = util.utilConnexion.getConn();
				PreparedStatement ps = cn.prepareStatement(sql)) {
			ps.setString(1, idArticle);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					mvtStockDto d = new mvtStockDto();
					d.setId(rs.getInt("id"));
					d.setTypemvtStock(TypeMvtStock.valueOf(rs.getString("typeMouvement")));
					String motif = rs.getString("motif");
					d.setMotif(motif == null ? null : MotifCorrection.valueOf(motif));
					int variation = rs.getInt("variation");
					d.setVariation(variation);
					d.setQteArticle(Math.abs(variation));
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
	
}
