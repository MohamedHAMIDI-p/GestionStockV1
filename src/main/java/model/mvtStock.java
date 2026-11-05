package model;

import java.util.Date;

import exception.TypeMvtStock;

public class mvtStock extends AbstractEntity
{	
	private int CodeMvtArticle;
	private Article article;
	private Fournisseur fournisseur;
	private TypeMvtStock typemvtStock;
	private Date dateMvtStock;
	private Double QteReception;
	private Double QteVendu;
	private Double QteRetour;
	private Double QteCorrect;
	private Double QteArticleTotal;
	
	
	public mvtStock() {
		super();
		// TODO Auto-generated constructor stub
	}
	public mvtStock(int id, Date dateCreation, Date dateModification, Article article, Fournisseur fournisseur,
			TypeMvtStock mvtStock, Date dateMvtStock, Double qteReception, Double qteVendu, Double qteRetour,
			Double qteCorrect, Double qteArticleTotal) {
		super(id, dateCreation, dateModification);
		this.article = article;
		this.fournisseur = fournisseur;
		this.typemvtStock = mvtStock;
		this.dateMvtStock = dateMvtStock;
		QteReception = qteReception;
		QteVendu = qteVendu;
		QteRetour = qteRetour;
		QteCorrect = qteCorrect;
		QteArticleTotal = qteArticleTotal;
	}
	
	public int getVariation() {
		return typemvtStock==null ? 0 :typemvtStock.getSens();
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
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
	public Double getQteReception() {
		return QteReception;
	}
	public void setQteReception(Double qteReception) {
		QteReception = qteReception;
	}
	public Double getQteVendu() {
		return QteVendu;
	}
	public void setQteVendu(Double qteVendu) {
		QteVendu = qteVendu;
	}
	public Double getQteRetour() {
		return QteRetour;
	}
	public void setQteRetour(Double qteRetour) {
		QteRetour = qteRetour;
	}
	public Double getQteCorrect() {
		return QteCorrect;
	}
	public void setQteCorrect(Double qteCorrect) {
		QteCorrect = qteCorrect;
	}
	public Double getQteArticleTotal() {
		return QteArticleTotal;
	}
	public void setQteArticleTotal(Double qteArticleTotal) {
		QteArticleTotal = qteArticleTotal;
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
		// TODO Auto-generated method stub
		return "Article : [\r\n"
				+ "           Code Mouvement Stock=" + CodeMvtArticle+",\r\n"
				+ "           Code Article ="+ article.getCodeArticle()+",\r\n"
				+ "           Quantite="+ QteArticleTotal+",\r\n"
				+ "           Fournisseur ="+fournisseur.getCnuf()+",\r\n"
				+ "           Date Mouvement ="+dateMvtStock+",\r\n"
				+super.toString()+"]";
	}
}
