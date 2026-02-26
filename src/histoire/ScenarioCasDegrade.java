package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois ouix = new Gaulois("ouix", 1);
		Gaulois nonx = new Gaulois("nonx", 1);
		try {
			etal.acheterProduit(2, nonx);
		}
		catch (IllegalArgumentException | IllegalStateException e) {
			e.printStackTrace();
		}
		
		System.out.println("Fin du test");
	}
}
