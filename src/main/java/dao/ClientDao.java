package dao;

import java.sql.SQLException;
import java.util.List;

import model.client;

public interface ClientDao {
	
	 boolean ajouterClient(client cl) throws SQLException;
	 client findByCodeClient(String CodeClient) throws SQLException;
	 client findByIdClient(int idClient) throws SQLException;
	 List<client> findAllClient() throws SQLException;

}
