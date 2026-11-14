package dao;

import java.sql.SQLException;
import java.util.List;

import model.client;

public interface ClientDao {
	
	public boolean ajouterClient(client cl) throws SQLException;
	public client findByCodeClient(String CodeClient) throws SQLException;
	public List<client> findAllClient() throws SQLException;

}
