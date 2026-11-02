package service.Impl;

import java.sql.SQLException;

import dao.FournisseurDao;
import exception.EntityNotFoundException;
import exception.ErreurCodes;
import exception.InvalidEntityException;
import model.Fournisseur;
import service.FournisseurService;

public class FournisseurServiceImpl implements FournisseurService {
	
	private final FournisseurDao fournisseurDao;

	
	public FournisseurServiceImpl(FournisseurDao fournisseurDao) {
		super();
		this.fournisseurDao = fournisseurDao;
	}

	@Override
	public void ajouterFournisseur(Fournisseur f) throws SQLException {

		valide(f);

			Fournisseur fou=fournisseurDao.findFournisseurByCnuf(f.getCnuf());
			if(fou!=null) {
				throw new EntityNotFoundException("Fournisseur "+f.getCnuf()+" déja existe !!", null);
			}
			fournisseurDao.saveFournisseur(f);
	}
	
	private void valide(Fournisseur f)throws SQLException {
		
		if(f==null) {
			throw new EntityNotFoundException("Fournisseur manquant", ErreurCodes.FOURNISSEUR_NOT_FOUND);
		}
		
		if(f.getCnuf()==null || f.getCnuf().trim().isEmpty()) {
			throw new EntityNotFoundException("Cnuf est obligatoire ", ErreurCodes.FOURNISSEUR_NOT_VALID);
		}
		
		if (f.getRIB()==null || f.getRIB().trim().isEmpty()) {
			throw new EntityNotFoundException("RIB est obligatoire ", ErreurCodes.FOURNISSEUR_NOT_VALID);
		}
		if (f.getTelephoneFournisseur()==null || f.getTelephoneFournisseur().trim().isEmpty()) {
			throw new InvalidEntityException("Telephone est obligatoire", ErreurCodes.FOURNISSEUR_NOT_VALID);
		}
		f.setTelephoneFournisseur(f.getTelephoneFournisseur().trim());
		f.setRIB(f.getRIB().trim());
	}

	@Override
	public Fournisseur trouverFournisseur(String cnuf) throws SQLException {

		Fournisseur f=fournisseurDao.findFournisseurByCnuf(cnuf);
		if(f==null) {
			throw new EntityNotFoundException("Fournisseur Introuvable ", ErreurCodes.FOURNISSEUR_NOT_FOUND);
		}
		return f;
	}

	
	
}
