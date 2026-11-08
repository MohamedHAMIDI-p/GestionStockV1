package exception;

public enum TypeMvtStock {

	//entre stock
	reception("ReceptionFournisseur",1,false),
	retourEntre("RetourClient",1,false),
	
	//sortie Stockt
	vente("vente",-1,false),
	retourSortie("RetourFournisseur",-1,false),


	//correction 
	CORRECTION_VOL("VOL",-1,true),
	CORRECTION_CASSE("CASSE",-1,true),
	CORRECTION_PEREMPTION("peremption",-1,true),
	CORRECTION_INVENTAIRE_PLUS("Inventaire +",1,true),
	CORRECTION_INVENTAIRE_MOINS("Inventaire -",-1,true);
	
	private final int CodeMvt;
	private final String libelle;
	private final boolean commentareOblgatoire;
	
	
	
	private TypeMvtStock(String libelle,int code,boolean commentaireObl) {
		this.CodeMvt=code;
		this.libelle=libelle;
		this.commentareOblgatoire=commentaireObl;
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	public int getSens() {
		return CodeMvt;
	}
	
	public boolean isCommentaireObligatoire() {
		return commentareOblgatoire;
	}
	
	public boolean isEntree() {
		return CodeMvt>0;
	}

	public boolean isCorrection() {
		// TODO Auto-genesrated method stub
		return false;
	}

	public boolean isFournisseurConcerne() {
		// TODO Auto-generated method stub
		return false;
	}
}