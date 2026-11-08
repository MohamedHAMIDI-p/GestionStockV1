package dao;

import java.sql.SQLException;
import java.util.List;

import dto.mvtStockDto;
import model.mvtStock;

public interface mvtStockDao {
	
	public boolean AjouterMvtStock(mvtStock mvtStock ) throws SQLException;
	public mvtStockDto consulterMvtStock(int CodeMvtArticle);
	List<mvtStockDto> findHistorique(String CodeMvtArticle) throws SQLException;
}
