package jeu;

/**
 * La classe Carte Elle definit une carte avec toutes ses caracteristiques
 */
public class Carte {
	public enum Couleur {
		Pique, Coeur, Carreau, Treffle
	};

	private int valeur;
	private Couleur couleur;

	private boolean estVisible;

	/**
	 * le constructeur de la classe
	 * 
	 * @param v
	 *            : une valeur de type entier
	 * @param c
	 *            : une couleur de type enum Couleur
	 */

	public Carte(int v, Couleur c) {
		if (v >= 1 && v <= 13) {
			valeur = v;
		} else {
			System.out.println("Erreur: problème de valeurs !");
			System.exit(0);
		}
		if (c == Couleur.Pique || c == Couleur.Coeur || c == Couleur.Carreau
				|| c == Couleur.Treffle) {
			couleur = c;
		} 
	}

	public int getValeur() {
		return valeur;
	}

	public Couleur getCouleur() {
		return couleur;
	}

	public boolean isEstVisible() {
		return estVisible;
	}

	public void setEstVisible(boolean estVisible) {
		this.estVisible = estVisible;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}

	@Override
	public String toString() {
		return valeur + "" + couleur.toString().toLowerCase().substring(0, 2);
	}
}
