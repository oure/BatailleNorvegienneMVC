package jeu;

/**
 *La classe pioche
 *C'est la pile de cartes qui reste apres la distribution
 */
import java.util.*;

public class Pioche {

	private static LinkedList<Carte> listeDeCarte = null;

	public static LinkedList<Carte> getListeDeCarte() {
		return listeDeCarte;
	}

	public void setListeDeCarte(LinkedList<Carte> listeDeCarte) {
		Pioche.listeDeCarte = listeDeCarte;
	}
	
	/** Point d'acces pour l'instance unique du singleton */

	public static LinkedList<Carte> getPioche() {
		if (listeDeCarte == null) 
			listeDeCarte = new LinkedList<Carte>();
		return listeDeCarte;
	}

	public String toString() {
		String s = "";
		for (Iterator<Carte> iterator = listeDeCarte.iterator(); iterator
				.hasNext();) {
			Carte carte = (Carte) iterator.next();
			s += " " + carte;
		}
		return "Pioche : " + s;
	}

	/**
	 * Cette methode supprime tous les elements de cette liste .
	 */
	public void viderPioche() {
		listeDeCarte.clear();
	}

	/**
	 * recuperer et supprimer le dernier element de la liste
	 * 
	 * @return
	 */
	public Carte prendreCarte() {
		return listeDeCarte.pollLast();
	}

	/**
	 * Retourne vrai si la liste est vide
	 */
	public boolean isEmpty() {
		return listeDeCarte.isEmpty();

	}
} 
