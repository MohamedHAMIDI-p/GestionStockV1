package dto;

import java.util.Date;

import exception.MotifCorrection;
import exception.TypeMvtStock;
import model.AbstractEntity;
import model.mvtStock;

public class mvtStockDto extends AbstractEntity {

	private static final long serialVersionUID = 1L;
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
	private String nomClient;
	private String prenomClient;
	
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

	public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public String getPrenomClient() {
		return prenomClient;
	}

	public void setPrenomClient(String prenomClient) {
		this.prenomClient = prenomClient;
	}
	
	//entity => dto
	public static mvtStockDto fromEntity(mvtStock mvtStkDto) {
		if(mvtStkDto==null) {
			return null;
		}
		mvtStockDto mvStkDto=new mvtStockDto();
		mvStkDto.setId(mvtStkDto.getId());
		return mvStkDto;
	}
	//Dto => entity
	public static mvtStock toEntity(mvtStockDto mvtstkDto) {
		if(mvtstkDto==null) {
			return null;
		}
		mvtStock mvtStoc= new mvtStock();
		mvtStoc.setId(mvtstkDto.getId());
		return mvtStoc;
	}
}
