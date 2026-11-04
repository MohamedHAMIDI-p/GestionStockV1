package exception;

public enum TypeMvtStock {

	reception("ReceptionFournisseur",1),
	vente("vente",2),
	retourEntre("RetourClient",3),
	retourSortie("RetourFournisseur",4),
	CorrectionPlus("Correction en plus",5),
	CorrectionMoins("Correction en moins",6);
	
	private int CodeMvt;
	private String libelle;
	
	
	
	private TypeMvtStock(String libelle,int code) {
		this.CodeMvt=code;
		this.libelle=libelle;
	}



	public int getCodeMvt() {
		return CodeMvt;
	}



	public void setCodeMvt(int codeMvt) {
		CodeMvt = codeMvt;
	}



	public String getLibelle() {
		return libelle;
	}



	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
}
