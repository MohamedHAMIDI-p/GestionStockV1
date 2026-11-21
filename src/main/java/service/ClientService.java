package service;

import java.sql.SQLException;
import java.util.List;

import dto.ClientDto;
import model.client;

public interface ClientService {
	
	 void ajouterClient(ClientDto client) throws SQLException;
	 ClientDto findByCodeClientSer(String codeClient) throws SQLException;
	 ClientDto findByIdClientSer(int idClient) throws SQLException;
	 List<client> findListClientALL() throws SQLException;

}
