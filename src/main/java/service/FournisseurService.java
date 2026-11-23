package service;

import java.sql.SQLException;
import java.util.List;

import model.Fournisseur;

public interface FournisseurService {

	public void ajouterFournisseur(Fournisseur f) throws SQLException;
	public Fournisseur trouverFournisseur(String cnuf) throws SQLException;
	List<Fournisseur> findAllFournisseur() throws  SQLException;
}
