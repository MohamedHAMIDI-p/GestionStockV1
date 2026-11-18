package model;

public class client extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	private String codeClient;
	private String nomClient;
	private String prenomClient;
	private String telephoneClient;
	private String AdresseClient;
	public client() {
		super();
	}
	public client(String code,String nom,String prenom,String tele,String adress) {
		super();
		this.codeClient=code;
		this.nomClient=nom;
		this.prenomClient=prenom;
		this.telephoneClient=tele;
		this.AdresseClient=adress;
	}
	public String getCodeClient() {
		return codeClient;
	}
	public void setCodeClient(String codeClient) {
		this.codeClient = codeClient;
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
	public String getTelephoneClient() {
		return telephoneClient;
	}
	public void setTelephoneClient(String telephoneClient) {
		this.telephoneClient = telephoneClient;
	}
	public String getAdresseClient() {
		return AdresseClient;
	}
	public void setAdresseClient(String adresseClient) {
		AdresseClient = adresseClient;
	}
}
