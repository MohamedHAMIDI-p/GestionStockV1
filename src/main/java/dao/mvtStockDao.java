package dao;

import java.sql.SQLException;
import java.util.List;

import dto.mvtStockDto;
import exception.TypeMvtStock;
import model.mvtStock;

public interface mvtStockDao {
	
	public boolean AjouterMvtStock(mvtStock mvtStock ) throws SQLException;
	public mvtStockDto consulterMvtStock(int CodeMvtArticle);
	List<mvtStockDto> findHistorique(String CodeMvtArticle) throws SQLException;
	List<mvtStockDto> findAllMouvement() throws SQLException;
	List<mvtStockDto> rechercherMouvements(
	        String codeArticle,
	        Integer idMouvement,
	        String reference,
	        String cnuf,
	        TypeMvtStock typeMouvement,
	        java.sql.Date dateMouvement
	) throws SQLException;
}
