package exception;
 
public enum MotifCorrection {
 
	VOL("Vol", false, true),
	CASSE("Casse", false, true),
	PEREMPTION("Péremption", false, true),
	RETROUVE("Article retrouvé", true, false),
	ERREUR_INVENTAIRE("Erreur d'inventaire", true, true),
	AUTRE("Autre", true, true);
 
	private final String libelle;
	private final boolean correctionPlus;
	private final boolean correctionMoins;
 
	private MotifCorrection(String libelle, boolean correctionPlus, boolean correctionMoins) {
		this.libelle = libelle;
		this.correctionPlus = correctionPlus;
		this.correctionMoins = correctionMoins;
	}
 
	public String getLibelle() {
		return libelle;
	}
 
	/** Ce motif peut-il justifier ce type de correction ? */
	public boolean estCompatible(TypeMvtStock type) {
		if (type == TypeMvtStock.CORRECTION_INVENTAIRE_PLUS) {
			return correctionPlus;
		}
		if (type == TypeMvtStock.CORRECTION_INVENTAIRE_MOINS) {
			return correctionMoins;
		}
		return false;
	}
}
 