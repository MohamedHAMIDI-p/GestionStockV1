package dao;

import java.sql.SQLException;
import java.util.List;

import dto.mvtStockDto;

public interface mvtStock {
	
	public void AjouterMvtStock(mvtStock mvtStock ) throws SQLException;
	public mvtStockDto consulterMvtStock(int CodeMvtArticle);
	public List<mvtStockDto> mvtStockDtos() throws SQLException;
}
