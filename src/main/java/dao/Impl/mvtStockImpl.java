package dao.Impl;

import java.sql.SQLException;
import java.util.List;

import dao.mvtStock;
import dto.mvtStockDto;
import model.*;

public class mvtStockImpl implements dao.mvtStock {



	@Override
	public void AjouterMvtStock(mvtStock mvtStock) throws SQLException {

		String sql1="update article set stockActuel=stockActuel + ?"
				+" where id";
		String sql2="";
	}
	@Override
	public mvtStockDto consulterMvtStock(int CodeMvtArticle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<mvtStockDto> mvtStockDtos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
