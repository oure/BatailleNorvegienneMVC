package jeu;

/**
 * La classe Distributeur
 * le distributeur hérite de joueur
 * Il melange et distribue les cartes 
 *  cette classe prend un attribut de type linkedlist ce sont les cartes qui vont etre distribuees
 */

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Distributeur extends JoueurIA {
	/**
	 * constructeur de la classe Distributeur qui appelle le constructeur de sa
	 * classe JoueurIA
	 * 
	 * @param Il prend en parametre une strategie
	 */
	public Distributeur(Strategie strat) {
		super(strat);
	}

	private LinkedList<Carte> ListeDeCarte = new LinkedList<Carte>();

	/**
	 * Ceci est la methode pour la distribution des cartes
	 * 
	 * @param setJeuDeCartes
	 *            de type hashset
	 * @param lj
	 *            : de type linkedlist, c'est la liste des joueurs
	 * @return une pioche, ce sont les cartes qui reste apres la distribution
	 */
	public Pioche distribuer(HashSet<JeuDeCartes> setJeuDeCartes,
			LinkedList<Joueur> lj) {
		for (Iterator<JeuDeCartes> iterator = setJeuDeCartes.iterator(); iterator
				.hasNext();) {
			JeuDeCartes jdc = (JeuDeCartes) iterator.next();
			ListeDeCarte.addAll((LinkedList<Carte>) jdc.getLs());
		}
		Pioche pioche = new Pioche();
		distribuerCartesEnmMain(lj);
		distribuerCartesVisibles(lj);
		distribuerCartesCachees(lj);
		pioche.setListeDeCarte(ListeDeCarte);
		return pioche;
	}

	public LinkedList<Carte> getL() {
		return ListeDeCarte;
	}

	public void setL(LinkedList<Carte> l) {
		this.ListeDeCarte = l;
	}

	/**
	 * Cette methode distribue des cartes face visible a une liste de joueurs
	 * 
	 * @param lj la collection de joueur a qui l'on veut distribuer les cartes
	 */
	public void distribuerCartesVisibles(LinkedList<Joueur> lj) {
		for (int i = 1; i <= 3; i++) {
			for (Iterator<Joueur> it = lj.iterator(); it.hasNext();) {
				Joueur joueur = (Joueur) it.next();
				joueur.ajouterCartefacsVisible((ListeDeCarte.pop()));
			}
		}
	}

	/**
	 * * Methode pour distribuer des cartes face cachee a une liste de
	 * joueurs
	 * @param lj la collection de joueur a qui l'on veut distribuer les cartes
	 */
	public void distribuerCartesCachees(LinkedList<Joueur> lj) {
		for (int i = 1; i <= 3; i++) {
			for (Iterator<Joueur> it = lj.iterator(); it.hasNext();) {
				Joueur joueur = (Joueur) it.next();
				joueur.ajouterCarteFaceCachee(ListeDeCarte.pop());
			}
		}
	}

	/**
	 * Methode pour distribuer des cartes en main a une liste de joueurs
	 * @param lj la collection de joueur a qui l'on veut distribuer les cartes
	 */
	public void distribuerCartesEnmMain(LinkedList<Joueur> lj) {
		for (int i = 1; i <= 3; i++) {
			for (Iterator<Joueur> it = lj.iterator(); it.hasNext();) {
				Joueur joueur = (Joueur) it.next();
				joueur.ajouterCarteEnMain(ListeDeCarte.pop());
			}
		}
	}
}
