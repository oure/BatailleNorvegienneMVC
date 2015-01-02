package jeu;

/**
 * La classe Carte en main 
 * c'est l'ensemble des cartes qu'un joueur dispose dans sa main 
 * cette classe prend un seul attribut de type hashset
 */
import java.util.HashSet;
import java.util.Iterator;

import jeu.Carte.Couleur;

public class CartesEnMain {
	private HashSet<Carte> cartemain = new HashSet<Carte>();

	/**
	 * cette methode permet d'ajouter des cartes sur les cartes en main
	 * 
	 * @param table
	 *            : instance de la classe Table
	 */
	public void addAll(Table table) {
		for (Iterator<Carte> it = table.getListe().iterator(); it.hasNext();) {
			Carte carte = (Carte) it.next();
			ajouterCarteMain(carte);

		}
	}

	/**
	 * cette methode permet d'ajouter une seule carte sur les cartes en main
	 * 
	 * @param c
	 *            : une carte a ajouter
	 */

	public void ajouterCarteMain(Carte car) {
		cartemain.add(car);
	}

	/**
	 * la methode qui permet de supprimer des cartes en main
	 * 
	 * @param valeur
	 *            : la valeur de la carte a supprimer
	 * @param couleur
	 * @param estHumain
	 * @param nombreOccurence
	 *            : le nombre de cartes a supprimer
	 * 
	 * @return une collection de cartes de type Hashset. ce sont les cartes que
	 *         l'on vient de supprimer de la main
	 */

	public HashSet<Carte> supCarteMain(int valeur, Couleur couleur,
			int nombreOccurence, boolean estHumain) {
		int i = 0;
		HashSet<Carte> hc = new HashSet<Carte>();
		for (Iterator<Carte> iterator = cartemain.iterator(); iterator
				.hasNext();) {
			Carte carte = (Carte) iterator.next();
			if (i >= nombreOccurence)
				break;
			if (estHumain) {
				if (carte.getValeur() == valeur
						&& carte.getCouleur() == couleur) {
					i++;
					hc.add(carte);
				}
			} else if (carte.getValeur() == valeur) {
				i++;
				hc.add(carte);
			}
		}
		cartemain.removeAll(hc);
		return hc;
	}

	/**
	 * la methode qui permet de supprimer toutes les cartes en main
	 * 
	 * @see HashSet#clear()
	 */

	public void supprimerToutesLesCartesEnmain() {
		cartemain.clear();
	}

	public HashSet<Carte> getCartemain() {
		return cartemain;
	}

	public void setCartemain(HashSet<Carte> cartemain) {
		this.cartemain = cartemain;
	}

	@Override
	public String toString() {
		return cartemain.toString();
	}

	/**
	 * Cette methode renvoie vrai si la main d'un joueur est vide et faux, sinon
	 * 
	 * @return un booleen
	 */
	public boolean isEmpty() {
		return cartemain.isEmpty();
	}

}
