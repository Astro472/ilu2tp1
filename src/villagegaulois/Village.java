package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	private static class Marche {
		private Etal[] etals;
		
		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i=0;i<nbEtals;i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i=0;i<etals.length;i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtals = 0;
			for (int i=0;i<etals.length;i++) {
				if (etals[i].contientProduit(produit)) {
					nbEtals++;
				}
			}
			Etal[] etalsProduit = new Etal[nbEtals];
			int currentEtal = 0;
			for (int i=0;i<etals.length;i++) {
				if (etals[i].contientProduit(produit)) {
					etalsProduit[currentEtal] = etals[i];
					currentEtal++;
				}
			}
			return etalsProduit;
		}
		
		private Etal trouverVendeur(Gaulois vendeur) {
			for (int i=0;i<etals.length;i++) {
				if (etals[i].getVendeur() == vendeur) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			String retour = "Affichage du marché :\n\n";
			int nbVides = 0;
			for (int i=0;i<etals.length;i++) {
				if (etals[i].isEtalOccupe()) {
					retour += etals[i].afficherEtal();
				}
				else {
					nbVides++;
				}
			}
			retour += "Il reste " + nbVides + " étals non utilisés dans le marché.\n";
			return retour;
		}
	}

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder retour = new StringBuilder();
		retour.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int indiceEtal = marche.trouverEtalLibre();
		if (indiceEtal != -1) {
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			retour.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n° " + (indiceEtal+1) + ".\n");
		}
		else {
			retour .append("Il n'y a aucun étal libre pour " + vendeur.getNom() + ".\n");
		}
		return retour.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder retour = new StringBuilder();
		Etal[] etalsProduit = marche.trouverEtals(produit);
		if (etalsProduit.length == 0) {
			retour.append("Aucun vendeur ne propose de " + produit + " au marché.\n");
		}
		else if (etalsProduit.length == 1) {
			retour.append("Seul le vendeur " + etalsProduit[0].getVendeur().getNom() + " propose des " + produit + " au marché.\n");
		}
		else {
			retour.append("Les vendeurs qui vendent des " + produit + " sont :\n");
			for (int i=0;i<etalsProduit.length;i++) {
				retour.append(" - " + etalsProduit[i].getVendeur().getNom() + "\n");
			}
		}
		return retour.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalVendeur = rechercherEtal(vendeur);
		return etalVendeur.libererEtal();
	}
	
	public String afficherMarche() {
		return marche.afficherMarche();
	}
}