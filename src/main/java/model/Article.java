package model;

import java.util.Date;

import exception.TvaArticle;

public class Article extends AbstractEntity {
	
	private String codeArticle;
	private String designation;
	private double prixAchatArticle;
	private double prixVenteArticle;
	private double prixRevient;
	private TvaArticle tvaArticle;
	private int idCategory;
	private int idFournisseur;
	
	public Article(int id, String codeArticle, String designation, int idCategory, int idFournisseur,Date dateCreation,Date dateModification) {
		super(id, dateCreation, dateModification);
		this.codeArticle = codeArticle;
		this.designation = designation;
		this.idCategory = idCategory;
		this.idFournisseur = idFournisseur;
	}
	
	public Article(String codeArticle, String designation, int idCategory, int idFournisseur,Date dateCreation,Date dateModification) {
		super(dateCreation, dateModification);
		this.codeArticle = codeArticle;
		this.designation = designation;
		this.idCategory = idCategory;
		this.idFournisseur = idFournisseur;
	}
	
	
	public Article(String codeArticle, String designation,Date dateCreation, Date dateModification,
			double prixAchatArticle, double prixVenteArticle, double prixRevient, TvaArticle tvaArticle, int idCategory,
			int idFournisseur) {
		super(dateCreation, dateModification);
		this.codeArticle = codeArticle;
		this.designation = designation;
		this.prixAchatArticle = prixAchatArticle;
		this.prixVenteArticle = prixVenteArticle;
		this.prixRevient = prixRevient;
		this.tvaArticle = tvaArticle;
		this.idCategory = idCategory;
		this.idFournisseur = idFournisseur;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}
	public void setIdFournisseur(int idFournisseur) {
		this.idFournisseur = idFournisseur;
	}
	public Article(int idArticle, String codeArticle, String designation) {
		super();
		this.codeArticle = codeArticle;
		this.designation = designation;
	}
	public Article() {
		super();
		// TODO Auto-generated constructor stub
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
	public int getIdCategory() {
		return idCategory;
	}
	public int getIdFournisseur() {
		return idFournisseur;
	}

	public double getPrixAchatArticle() {
		return prixAchatArticle;
	}

	public void setPrixAchatArticle(double prixAchatArticle) {
		this.prixAchatArticle = prixAchatArticle;
	}

	public double getPrixVenteArticle() {
		return prixVenteArticle;
	}

	public void setPrixVenteArticle(double prixVenteArticle) {
		this.prixVenteArticle = prixVenteArticle;
	}

	public double getPrixRevient() {
		return prixRevient;
	}

	public void setPrixRevient(double prixRevient) {
		this.prixRevient = prixRevient;
	}

	public TvaArticle getTvaArticle() {
		return tvaArticle;
	}

	public void setTvaArticle(TvaArticle tvaArticle) {
		this.tvaArticle = tvaArticle;
	}
	
	
	@Override
	public String toString() {

		return "Article : [\r\n"
				+ "           CodeArticle=" + codeArticle+",\r\n"
				+ "           Designation="+ designation+",\r\n"
				+ "           PrixAchatArticle="+ prixAchatArticle+",\r\n"
				+ "           PrixVenteArticle="+prixVenteArticle+",\r\n"
				+ "           PrixRevient="+ prixRevient+",\r\n"
				+ "           TvaArticle="+ tvaArticle+",\r\n"
				+ "           IdCategory="+idCategory+",\r\n"
				+ "           IdFournisseur="+idFournisseur+",\r\n"
				+super.toString()+"]";
	}
	
}
