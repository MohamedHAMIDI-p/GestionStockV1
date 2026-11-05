package dto;

import java.util.Date;

import exception.TypeMvtStock;
import model.AbstractEntity;
import model.Article;
import model.Fournisseur;

public class mvtStockDto extends AbstractEntity {

	private int idmvtStock;
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
	
	public mvtStockDto() {
		super();
	}
	public int getCodeMvtArticle() {
		return CodeMvtArticle;
	}
	public void setCodeMvtArticle(int codeMvtArticle) {
		CodeMvtArticle = codeMvtArticle;
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
	public int getIdmvtStock() {
		return idmvtStock;
	}
	public void setIdmvtStock(int idmvtStock) {
		this.idmvtStock = idmvtStock;
	}
	public TypeMvtStock getTypemvtStock() {
		return typemvtStock;
	}
	public void setTypemvtStock(TypeMvtStock typemvtStock) {
		this.typemvtStock = typemvtStock;
	}
}
