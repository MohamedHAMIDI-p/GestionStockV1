package dto;

import exception.TvaArticle;

public class ArticleDto {

	private int idArticle;
	private String codeArticle;
	private String designation;
	private int idCategory;
	private int idFournisseur;
	private String nomFournisseur;
	private String nomCategory;
	private TvaArticle tvaArt;
	private double prixVente;
	private String cnufFournisseur;
	

	public ArticleDto() {
		super();
	}

	public int getIdArticle() {
		return idArticle;
	}


	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}

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
	
	
	public TvaArticle getTvaArt() {
		return tvaArt;
	}

	public void setTvaArt(TvaArticle tvaArt) {
		this.tvaArt = tvaArt;
	}

	public String getCnufFournisseur() {
		return cnufFournisseur;
	}


	public void setCnufFournisseur(String cnufFournisseur) {
		this.cnufFournisseur = cnufFournisseur;
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


	public double getPrixVente() {
		return prixVente;
	}


	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
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
	
	@Override
	public String toString() {
		return "Article : [\r\n"
				+ "           codeArticle=" + codeArticle+",\r\n"
				+ "           designation="+ designation+",\r\n"
				+ "           prixVenteArticle="+prixVente+",\r\n"
				+ "           Cnuf="+cnufFournisseur+",\r\n"
				+ "           Fournisseur="+nomFournisseur+",\r\n"
				+ "           Category="+nomCategory+",\r\n"
				+ "           tvaArticle="+tvaArt+",\r\n"
						+ "]";
	}
}
