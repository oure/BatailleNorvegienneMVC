package jeu;

/**
 * La classe table
 *C'est la table constitue par les cartes jouees pas les joueurs 
 */
import java.util.*;

public class Table {
	/**
	 * attributs de la classe table
	 */
	private LinkedList<Carte> liste = new LinkedList<Carte>();

	/**
	 * Vider la table de toute ses cartes
	 */
	public void viderTable() {
		liste.clear();
	}

	public HashSet<Carte> ramasserLeTas() {
		HashSet<Carte> h = new HashSet<Carte>();
		for (Iterator<Carte> iterator = liste.iterator(); iterator.hasNext();) {
			Carte carte = (Carte) iterator.next();
			h.add(carte);
		}
		viderTable();
		return h;
	}

	/**
	 * affichage de la derniere carte jouee sur la table
	 * 
	 * @return une carte
	 */
	public Carte getDerniereCarteDuTas() {
		return liste.getLast();
	}

	/**
	 * Un joueur peut jouer sur la table grace a cette methode
	 * 
	 * @param ca
	 */
	public void ajouterCarteALaTable(Carte ca) {
		System.out.println("J'ajoute un carte à la table " + ca.getValeur());
		liste.add(ca);
	}

	public LinkedList<Carte> getListe() {
		return liste;
	}

	/**
	 * cette methode servira au joueur qui veut jouer plus d une carte
	 * 
	 * @param hashSetCartes
	 */
	public void ajouterCartesTable(HashSet<Carte> hashSetCartes) {
		for (Iterator<Carte> iterator = hashSetCartes.iterator(); iterator
				.hasNext();) {
			Carte carte = (Carte) iterator.next();
			liste.add(carte);
		}
	}

	public boolean laDerniereCarteDuJeuEstUnSept() {
		if (!this.isEmpty()){
		if (getDerniereValeurCarteDuTas() == 7)
			return true;
		else
			return false;
		}
		else return false;
	}

	public void setListe(LinkedList<Carte> liste) {
		this.liste = liste;
	}

	public boolean isEmpty() {
		return this.liste.isEmpty();
	}

	@Override
	public String toString() {
		return "Table [liste=" + liste + "]";
	}

	public int getDerniereValeurCarteDuTas() {
		return getDerniereCarteDuTas().getValeur();
	}
}
