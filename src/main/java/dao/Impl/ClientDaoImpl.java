package dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import dao.ClientDao;
import model.client;
import util.utilConnexion;

public class ClientDaoImpl implements ClientDao {

	@Override
	public boolean ajouterClient(client cl) throws SQLException {

		String sql="INSERT INTO client (codeClient,nomClient,prenomClient,telephoneClient,AdresseClient,dateCreation) "
				+ " VALUES (?,?,?,?,?,NOW())";
		try (Connection cn=util.utilConnexion.getConn();
		PreparedStatement ps=cn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
			ps.setString(1, cl.getCodeClient());
			ps.setString(2, cl.getNomClient());
			ps.setString(3, cl.getPrenomClient());
			ps.setString(4, cl.getTelephoneClient());
			ps.setString(5, cl.getAdresseClient());
			int ligne=ps.executeUpdate();
			try (ResultSet keys=ps.getGeneratedKeys()){
				if(keys.next()) {
					cl.setId(keys.getInt(1));
				}
			}
			return ligne>0;
		}
		
	}

	@Override
	public client findByCodeClient(String codeClient) throws SQLException {
		String sql="select * from client where codeClient = ?";
		try(Connection cn=utilConnexion.getConn();
				PreparedStatement ps=cn.prepareStatement(sql)){
			ps.setString(1, codeClient);
			try(ResultSet rs=ps.executeQuery()){
				return rs.next() ? mapClient(rs) : null;
			}
		}
	}

	@Override
	public client findByIdClient(int idClient) throws SQLException {
		String sql="select * from client where id = ?";
		try(Connection cn=utilConnexion.getConn();
				PreparedStatement ps=cn.prepareStatement(sql)){
			ps.setInt(1, idClient);
			try(ResultSet rs=ps.executeQuery()){
				return rs.next() ? mapClient(rs) : null;
			}
		}
	}
	
	@Override
	public List<client> findAllClient() throws SQLException {
		
		List<client> listClient= new ArrayList<client>();
		String sql="select * from client order by id";
		try( Connection cn=utilConnexion.getConn();
				PreparedStatement ps=cn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery()
				){
				while(rs.next()) {
				  listClient.add(mapClient(rs));
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return listClient;
	}

	private client mapClient(ResultSet rs) throws SQLException {
		
		client nouveauC=new client();
		nouveauC.setId(rs.getInt("id"));
		nouveauC.setCodeClient(rs.getString("codeClient"));
		nouveauC.setNomClient(rs.getString("nomClient"));
		nouveauC.setPrenomClient(rs.getString("prenomClient"));
		nouveauC.setTelephoneClient(rs.getString("telephoneClient"));
		nouveauC.setAdresseClient(rs.getString("AdresseClient"));
		nouveauC.setDateCreation(rs.getTimestamp("dateCreation"));
		return nouveauC;
	}

}
