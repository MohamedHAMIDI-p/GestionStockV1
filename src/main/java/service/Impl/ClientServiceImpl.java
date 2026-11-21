package service.Impl;

import java.sql.SQLException;
import java.util.List;

import dao.ClientDao;
import dto.ClientDto;
import exception.EntityNotFoundException;
import exception.ErreurCodes;
import exception.InvalidEntityException;
import model.client;
import service.ClientService;

public class ClientServiceImpl implements ClientService {
	
	private final ClientDao clientDao;
	
	public ClientServiceImpl(ClientDao c) {
		this.clientDao=c;
	}

	@Override
	public void ajouterClient(ClientDto clientDto) throws SQLException {


		if(clientDto== null) {
			throw new InvalidEntityException ("Client Vide",ErreurCodes.CLIENT_NOT_FOUND);
		}
		
		if(clientDto.getCodeClient()==null || clientDto.getCodeClient().trim().isEmpty()) {
			throw new InvalidEntityException("Code Client Obligatoire", ErreurCodes.CLIENT_NOT_VALID);
		}
		
		 client nouveau=ClientDto.toEntity(clientDto);
		 nouveau.setCodeClient(nouveau.getCodeClient().trim());
			
		if (clientDao.findByCodeClient(nouveau.getCodeClient())!=null) {
			throw new InvalidEntityException("Code client deja existe :"+nouveau.getCodeClient(), ErreurCodes.CATEGORY_ALREADY_IN_USE);
			
		}else {
			clientDao.ajouterClient(nouveau);
		}
		
	}

	@Override
	public ClientDto findByCodeClientSer(String codeClient) throws SQLException {

		ClientDto clientDto=ClientDto.fromEntity(clientDao.findByCodeClient(codeClient));
		if(clientDto.getCodeClient().trim()==null || clientDto.getCodeClient().trim().isEmpty()) {
			throw new EntityNotFoundException("le code n'existe pas ", ErreurCodes.CLIENT_NOT_VALID);
		}
		return clientDto;
	}

	@Override
	public ClientDto findByIdClientSer(int idClient) throws SQLException {
		
		ClientDto clientDto=ClientDto.fromEntity(clientDao.findByIdClient(idClient));
		
		if(clientDto.getCodeClient().trim()==null || clientDto.getCodeClient().trim().isEmpty()) {
			throw new EntityNotFoundException("le code n'existe pas ", ErreurCodes.CLIENT_NOT_VALID);
		}
		return clientDto;
	}

	@Override
	public List<client> findListClientALL() throws SQLException {
		return clientDao.findAllClient();
	}

	
}
