package exception;

public enum TvaArticle {
	

	TVA_7(7),
	TVA_14(14),
	TVA_20(20);
	
	private final int taux;
	
	private TvaArticle(int taux) {
		this.taux=taux;
	}

	public int getTaux() {
		return taux;
	}
	
	@Override
	public String toString() {
		return taux + " %";
	}
	
	public static TvaArticle fromTaux(int taux) {
		for(TvaArticle t : values()) {
			if(t.taux==taux) {
				return t;
			}
		}
		throw new IllegalArgumentException("Taux TVA non autorisé "+taux+"%");
	}
}
