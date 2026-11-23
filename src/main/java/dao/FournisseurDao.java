package dao;

import java.sql.SQLException;
import java.util.List;

import model.Fournisseur;

public interface FournisseurDao {
	
	public void saveFournisseur(Fournisseur f) throws SQLException;
	public Fournisseur findFournisseurByCnuf(String cnuf) throws SQLException;
	List<Fournisseur> lisFournisseurAllFournisseurs() throws SQLException;

}
