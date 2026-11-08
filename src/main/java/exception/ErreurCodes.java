package exception;

public enum ErreurCodes {
	
		ARTICLE_NOT_FOUND(100),
		ARTICLE_NOT_VALID(101),
		ARTICLE_ALREADY_IN_USE(102),
		
		FOURNISSEUR_NOT_FOUND(200),
		FOURNISSEUR_NOT_VALID(201),
		FOURNISSEUR_ALREADY_IN_USE(202),
		
		CATEGORY_NOT_FOUND(300),
		CATEGORY_NOT_VALID(301),
		CATEGORY_ALREADY_IN_USE(302),
		
		MOUVEMENT_NOT_VALID(401),
		STOCK_INSUFFISANT(402);

	  private final int code;

	  private ErreurCodes(int code) {
		this.code = code;
	}
	  
	  public int getcodes() {
		  return code;
	  }
}
