package service;

import java.sql.SQLException;

import dto.ClientDto;

public interface ClientService {
	
	public void ajouterClient(ClientDto client) throws SQLException;
	

}
