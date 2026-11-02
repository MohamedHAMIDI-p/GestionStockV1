package dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.FournisseurDao;
import model.Fournisseur;

public class FournisseurDaoImpl implements FournisseurDao {

	@Override
	public void saveFournisseur(Fournisseur f) throws SQLException {

		String sql="INSERT INTO fournisseur (nomFournisseur,cnuf,prenomFournisseur,telephoneFournisseur,codePostal,villeFournisseur,mailFournisseur,rib,dateCreation,dateModification) values (?,?,?,?,?,?,?,?,NOW(),NOW())";
		Connection cn=util.utilConnexion.getConn();
		PreparedStatement ps=cn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, f.getNomFournisseur());
		ps.setString(2, f.getCnuf());
		ps.setString(3,f.getPrenomFournisseur());
		ps.setString(4, f.getTelephoneFournisseur());
		ps.setString(5,f.getCodePostal());
		ps.setString(6, f.getVilleFournisseur());
		ps.setString(7, f.getMailFournisseur());
		ps.setString(8, f.getRIB());
		ps.executeUpdate();
		ResultSet rs=ps.getGeneratedKeys();
		if(rs.next()) {
			f.setId(rs.getInt(1));
		}
		ps.close();
	}

	@Override
	public Fournisseur findFournisseurByCnuf(String cnuf) throws SQLException {
		
		String sql="select * from fournisseur where cnuf  = ?";
		try (Connection cn=util.utilConnexion.getConn();
		PreparedStatement ps=cn.prepareStatement(sql)){
		ps.setString(1, cnuf);
		try (ResultSet rs=ps.executeQuery())
		{
			return rs.next() ? mapFournisseur(rs) : null;
			}
		}
	}

	private Fournisseur mapFournisseur(ResultSet rs) throws SQLException {
		Fournisseur f = new Fournisseur();
		f.setId(rs.getInt("id"));
		f.setCnuf(rs.getString("nomFournisseur"));
		f.setCnuf(rs.getString("cnuf"));
		f.setPrenomFournisseur(rs.getString("prenomFournisseur"));
		f.setTelephoneFournisseur(rs.getString("telephoneFournisseur"));
		f.setCodePostal(rs.getString("codePostal"));
		f.setVilleFournisseur(rs.getString("villeFournisseur"));
		f.setMailFournisseur(rs.getString("mailFournisseur"));
		f.setRIB(rs.getString("rib"));
		f.setDateCreation(rs.getTimestamp("dateCreation"));
		f.setDateModification(rs.getTimestamp("dateModification"));
		return f;
	}
	
}
