package dto;

import model.AbstractEntity;
import model.client;

public class ClientDto extends AbstractEntity {
	
	private String codeClient;
	private String nomClient;
	private String prenomClient;

	public ClientDto() {super();}
	public ClientDto(String codeClient,String nomClient,String prenomClient)
{
		super();
		this.codeClient=codeClient;
		this.nomClient=nomClient;
		this.prenomClient=prenomClient;
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
	//mappers transfert object client vers ClientDto
	public static ClientDto fromEntity(client c) {
		if(c==null) {
			return null;
		}
		ClientDto Cdto=new ClientDto();
		Cdto.setId(c.getId());
		Cdto.setCodeClient(c.getCodeClient());
		Cdto.setNomClient(c.getNomClient());
		Cdto.setPrenomClient(c.getPrenomClient());
		return Cdto;
	}
	// mappers transfert objet de type clientDto vers Client
	public static client toEntity(ClientDto Cdto) {
		if(Cdto==null) {
			return null;
		}
		client c=new client();
		c.setId(Cdto.getId());
		c.setCodeClient(Cdto.getCodeClient());
		c.setNomClient(Cdto.getNomClient());
		c.setPrenomClient(Cdto.getPrenomClient());
		return c;
	}
}
