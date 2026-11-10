package dto;

import java.util.Date;

import exception.MotifCorrection;
import exception.TypeMvtStock;
import model.AbstractEntity;

public class mvtStockDto extends AbstractEntity {

	private String CodeArticle;
	private String Cnuf;
	private String nomFournisseur;
	private TypeMvtStock typemvtStock;
	private Date dateMvtStock;
	private int QteArticle;
	private MotifCorrection motif;
	private int variation;
	private String reference;
	private String commentaire;
	
	public mvtStockDto() {
		super();
	}

	public String getCodeArticle() {
		return CodeArticle;
	}

	public void setCodeArticle(String codeArticle) {
		CodeArticle = codeArticle;
	}

	public String getCnuf() {
		return Cnuf;
	}

	public void setCnuf(String cnuf) {
		Cnuf = cnuf;
	}

	public String getNomFournisseur() {
		return nomFournisseur;
	}

	public void setNomFournisseur(String nomFournisseur) {
		this.nomFournisseur = nomFournisseur;
	}

	public TypeMvtStock getTypemvtStock() {
		return typemvtStock;
	}

	public void setTypemvtStock(TypeMvtStock typemvtStock) {
		this.typemvtStock = typemvtStock;
	}

	public Date getDateMvtStock() {
		return dateMvtStock;
	}

	public void setDateMvtStock(Date dateMvtStock) {
		this.dateMvtStock = dateMvtStock;
	}

	public int getQteArticle() {
		return QteArticle;
	}

	public void setQteArticle(int qteArticle) {
		QteArticle = qteArticle;
	}

	public MotifCorrection getMotif() {
		return motif;
	}

	public void setMotif(MotifCorrection motif) {
		this.motif = motif;
	}

	public int getVariation() {
		return variation;
	}

	public void setVariation(int variation) {
		this.variation = variation;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	
	
}
