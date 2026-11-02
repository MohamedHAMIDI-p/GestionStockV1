package util;

public final class pageLire {
	
	
	
	public static int lireEntier(String valeur,int parDefaut) {
		
		
		if (valeur==null) {
			return parDefaut;
		}
		
		try
		{
			return Integer.parseInt(valeur.trim());
		}catch(NumberFormatException e)
		{
			return parDefaut;
		}
	}

}
