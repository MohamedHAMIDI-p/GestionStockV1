package exception;

public enum TypeMvtStock {

	//entre stock
	reception("Reception Fournisseur",1,false,true),
	retourEntre("Retour Client",1,false,false),
	
	//sortie Stockt
	vente("vente",-1,false,false),
	retourSortie("Retour Fournisseur",-1,false,true),


	//correction 
	CORRECTION_VOL("VOL",-1,true,false),
	CORRECTION_CASSE("CASSE",-1,true,false),
	CORRECTION_PEREMPTION("peremption",-1,true,false),
	CORRECTION_INVENTAIRE_PLUS("Inventaire +",1,true,false),
	CORRECTION_INVENTAIRE_MOINS("Inventaire -",-1,true,false);
	
	private final int CodeMvt;
	private final String libelle;
	private final boolean commentareOblgatoire;
	private final boolean fournisseurConcerne;
	
	
	private TypeMvtStock(String libelle,int code,boolean commentaireObl,boolean fournisseurConcerne) {
		this.CodeMvt=code;
		this.libelle=libelle;
		this.commentareOblgatoire=commentaireObl;
		this.fournisseurConcerne=fournisseurConcerne;
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
		return commentareOblgatoire;
	}

	public boolean isFournisseurConcerne() {
		return fournisseurConcerne;
	}
}