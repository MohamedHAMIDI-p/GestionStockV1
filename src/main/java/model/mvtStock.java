package model;

import java.util.Date;

import exception.MotifCorrection;
import exception.TypeMvtStock;

public class mvtStock extends AbstractEntity
{	
	private int CodeMvtArticle;
	private int idArticle;
	private int idFournisseur;
	private TypeMvtStock typemvtStock;
	private Date dateMvtStock;
	private MotifCorrection motif; 
	private int QteArticle;
	private String reference;
	private String commentaire;
	
	
	public mvtStock() {
		super();
	}
	public mvtStock(int id, Date dateCreation, Date dateModification, int article, int fournisseur,
			TypeMvtStock mvtStock, Date dateMvtStock, MotifCorrection motif, int Quantite) {
		super(id, dateCreation, dateModification);
		this.CodeMvtArticle=id;
		this.idArticle = article;
		this.idFournisseur = fournisseur;
		this.typemvtStock = mvtStock;
		this.dateMvtStock = dateMvtStock;
		this.motif=motif;
		this.QteArticle=Quantite;
	}
	
	public mvtStock(int idArticle,TypeMvtStock type,int quantite)
	{
		this.idArticle=idArticle;
		this.typemvtStock=type;
		this.QteArticle=quantite;
		
	}
	
	public int getVariation() {
		return typemvtStock==null ? 0 :typemvtStock.getSens();
	}
	public TypeMvtStock getMvtStock() {
		return typemvtStock;
	}
	public void setMvtStock(TypeMvtStock typemvtStock) {
		this.typemvtStock = typemvtStock;
	}
	public Date getDateMvtStock() {
		return dateMvtStock;
	}
	public void setDateMvtStock(Date dateMvtStock) {
		this.dateMvtStock = dateMvtStock;
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
	public int getIdArticle() {
		return idArticle;
	}
	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}
	public int getIdFournisseur() {
		return idFournisseur;
	}
	public void setIdFournisseur(int idFournisseur) {
		this.idFournisseur = idFournisseur;
	}
	public MotifCorrection getMotif() {
		return motif;
	}
	public void setMotif(MotifCorrection motif) {
		this.motif = motif;
	}
	public int getQteArticle() {
		return QteArticle;
	}
	public void setQteArticle(int qteArticle) {
		QteArticle = qteArticle;
	}
	public int getCodeMvtArticle() {
		return CodeMvtArticle;
	}
	public void setCodeMvtArticle(int codeMvtArticle) {
		CodeMvtArticle = codeMvtArticle;
	}
	public TypeMvtStock getTypemvtStock() {
		return typemvtStock;
	}
	public void setTypemvtStock(TypeMvtStock typemvtStock) {
		this.typemvtStock = typemvtStock;
	}
	
	@Override
	public String toString() {
		return "MvtStock [Code Mvt=" + CodeMvtArticle + ", idArticle=" + idArticle + ", type=" + typemvtStock
				+ ", variation=" + getVariation() + ", motif=" + motif +super.toString()+"]";
	}
}
