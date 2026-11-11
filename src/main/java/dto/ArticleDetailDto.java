package dto;

import exception.TvaArticle;
import model.AbstractEntity;

public class ArticleDetailDto extends AbstractEntity {
	
	//private int idArticle;
	private String codeArticle;
	private String designation;
	private int idCategory;
	private int idFournisseur;
	private String nomFournisseur;
	private String nomCategory;
	private TvaArticle tvaArticle;
	private double prixVente;
	private String cnufFournisseur;
	private double PrixAchatArticle;
	private double PrixVenteArticle;
	private double PrixRevient;
	private int stockActuel;
	
	
	public ArticleDetailDto() {
		super();
	}


/*	public int getIdArticle() {
		return idArticle;
	}


	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}*/


	public String getCodeArticle() {
		return codeArticle;
	}


	public void setCodeArticle(String codeArticle) {
		this.codeArticle = codeArticle;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public int getIdCategory() {
		return idCategory;
	}


	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}


	public int getIdFournisseur() {
		return idFournisseur;
	}


	public void setIdFournisseur(int idFournisseur) {
		this.idFournisseur = idFournisseur;
	}


	public String getNomFournisseur() {
		return nomFournisseur;
	}


	public void setNomFournisseur(String nomFournisseur) {
		this.nomFournisseur = nomFournisseur;
	}


	public String getNomCategory() {
		return nomCategory;
	}


	public void setNomCategory(String nomCategory) {
		this.nomCategory = nomCategory;
	}


	public TvaArticle getTvaArticle() {
	    return tvaArticle;
	}

	public void setTvaArticle(TvaArticle tvaArticle) {
	    this.tvaArticle = tvaArticle;
	}


	public double getPrixVente() {
		return prixVente;
	}


	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}


	public String getCnufFournisseur() {
		return cnufFournisseur;
	}


	public void setCnufFournisseur(String cnufFournisseur) {
		this.cnufFournisseur = cnufFournisseur;
	}


	public double getPrixAchatArticle() {
		return PrixAchatArticle;
	}


	public void setPrixAchatArticle(double prixAchatArticle) {
		PrixAchatArticle = prixAchatArticle;
	}


	public double getPrixVenteArticle() {
		return PrixVenteArticle;
	}


	public void setPrixVenteArticle(double prixVenteArticle) {
		PrixVenteArticle = prixVenteArticle;
	}


	public double getPrixRevient() {
		return PrixRevient;
	}


	public void setPrixRevient(double prixRevient) {
		PrixRevient = prixRevient;
	}


	public int getStockActuel() {
		return stockActuel;
	}


	public void setStockActuel(int stockActuel) {
		this.stockActuel = stockActuel;
	}
	
	

}
