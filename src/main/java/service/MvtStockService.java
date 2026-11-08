package service;

import java.sql.SQLException;
import java.util.List;

import dto.mvtStockDto;
import exception.MotifCorrection;
import exception.TypeMvtStock;
import model.mvtStock;

public interface MvtStockService {

	mvtStock enregistrerMvtStock(String codeArticle, TypeMvtStock type, int quantite, MotifCorrection motif,
			Integer idFournisseur, String reference, String commentaire) throws SQLException;
	List<mvtStockDto> historique(String codeArticle) throws SQLException;
}
