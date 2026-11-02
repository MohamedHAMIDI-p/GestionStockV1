package model;

import java.util.Date;

public class Fournisseur extends AbstractEntity {
	
	private int idFournisseur;
	private String cnuf;
	private String nomFournisseur;
	private String prenomFournisseur;
	private String telephoneFournisseur;
	private String CodePostal;
	private String villeFournisseur;
	private String mailFournisseur;
	private String RIB;
	
	
	
	public Fournisseur() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Fournisseur(int id, Date dateCreation, Date dateModification) {
		super(id, dateCreation, dateModification);
		// TODO Auto-generated constructor stub
	}

	public Fournisseur(int idFournisseur, String cnuf,
			String nomFournisseur, String prenomFournisseur, String telephoneFournisseur, String codePostal,
			String villeFournisseur, String mailFournisseur,
			String rib, Date dateCreationFournisseur, Date dateMiseJourFournisseur) {
		super(idFournisseur, dateCreationFournisseur, dateMiseJourFournisseur);
		this.idFournisseur = idFournisseur;
		this.cnuf = cnuf;
		this.nomFournisseur = nomFournisseur;
		this.prenomFournisseur = prenomFournisseur;
		this.telephoneFournisseur = telephoneFournisseur;
		CodePostal = codePostal;
		this.villeFournisseur = villeFournisseur;
		this.dateCreation = dateCreationFournisseur;
		this.dateModification = dateMiseJourFournisseur;
		this.mailFournisseur = mailFournisseur;
		RIB = rib;
	}
	
	public Fournisseur(String cnuf,String nomFournisseur, String prenomFournisseur, String telephoneFournisseur, String codePostal,
			String villeFournisseur, String mailFournisseur,String rib,Date dateCreationFournisseur,Date dateMiseJourFournisseur) {
		super(dateCreationFournisseur, dateMiseJourFournisseur);
		this.cnuf = cnuf;
		this.nomFournisseur = nomFournisseur;
		this.prenomFournisseur = prenomFournisseur;
		this.telephoneFournisseur = telephoneFournisseur;
		CodePostal = codePostal;
		this.villeFournisseur = villeFournisseur;
		this.dateCreation = dateCreationFournisseur;
		this.dateModification = dateMiseJourFournisseur;
		this.mailFournisseur = mailFournisseur;
		RIB = rib;
	}
	public int getIdFournisseur() {
		return idFournisseur;
	}
	public void setIdFournisseur(int idFournisseur) {
		this.idFournisseur = idFournisseur;
	}
	public String getCnuf() {
		return cnuf;
	}
	public void setCnuf(String cnuf) {
		this.cnuf = cnuf;
	}
	public String getNomFournisseur() {
		return nomFournisseur;
	}
	public void setNomFournisseur(String nomFournisseur) {
		this.nomFournisseur = nomFournisseur;
	}
	public String getPrenomFournisseur() {
		return prenomFournisseur;
	}
	public void setPrenomFournisseur(String prenomFournisseur) {
		this.prenomFournisseur = prenomFournisseur;
	}
	public String getTelephoneFournisseur() {
		return telephoneFournisseur;
	}
	public void setTelephoneFournisseur(String telephoneFournisseur) {
		this.telephoneFournisseur = telephoneFournisseur;
	}
	public String getCodePostal() {
		return CodePostal;
	}
	public void setCodePostal(String codePostal) {
		CodePostal = codePostal;
	}
	public String getVilleFournisseur() {
		return villeFournisseur;
	}
	public void setVilleFournisseur(String villeFournisseur) {
		this.villeFournisseur = villeFournisseur;
	}
	public String getMailFournisseur() {
		return mailFournisseur;
	}
	public void setMailFournisseur(String mailFournisseur) {
		this.mailFournisseur = mailFournisseur;
	}
	public String getRIB() {
		return RIB;
	}
	public void setRIB(String rIB) {
		RIB = rIB;
	}
	
	@Override
	public String toString() {
		return "Fournisseur [" + super.toString()
		+ ", cnuf=" + cnuf
		+ ", nomFournisseur=" + nomFournisseur
		+ ", villeFournisseur=" + villeFournisseur + "]";
	}

}
