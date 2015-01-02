package jeu;

import java.util.HashSet;
import java.util.Iterator;
/**
 * La classe Strategie offensive
 * Elle herite de Strategie et represente la tactique offensive des joueurs virtuels 
 */
import java.util.LinkedList;

public class StrategieOffensive implements Strategie {

	public int nombreDeHuitQueIaDoitPoser(JoueurIA ia, LinkedList<Joueur> lj) {
		int nbDeHuit = ia.nombreDeHuitQueLeJoueurPossede();
		int positionJoueurIA = ia.getNumeroduJoueurDansLaListeDeJoueur(lj, ia);
		positionJoueurIA++;
		for (int i = 1; i < lj.size(); i++) {
			if ((positionJoueurIA + i) % lj.size() == 1){
				if (nbDeHuit>=i);
				return i;
			}
		}
		return -1;
	}
	@Override
	/**
	 * Cette methode permet au joueur IA d'enoyer la table sur le joueur qui a le moins de carte
	 * @return  un joueur sur qui la table sera envoye
	 */
	public Joueur choixDuJoueurCibleePourEnvoyerLetas(JoueurIA j,
			LinkedList<Joueur> lj) {
		return j.quiAleMoinsDeCarte(lj);
	}

	@Override
	public int contrerUnAs(JoueurIA ia) {
		if (!ia.getCartesEnMain().isEmpty())
			if (ia.estPossedeDansLamain(2, 1))
				return 2;
			else
				return 1;
		if (!ia.getCartefaceVisibles().isEmpty()
				&& ia.getCartesEnMain().isEmpty())
			if (ia.estPossedeDansDansLesCartesVisibles(2, 1))
				return 2;
			else
				return 1;
		if (!ia.getCartefaceVisibles().isEmpty()
				&& !ia.getCartesEnMain().isEmpty()
				&& ia.getCarteFacesCachees().isEmpty()) {
			Carte c = ia.getCarteFacesCachees().prendreAuhasard();
			if (c.getValeur() == 2)
				return c.getValeur();
			else if (c.getValeur() == 1)
				return c.getValeur();
			else
				ia.ajouterCarteFaceCachee(c);
		}
		return -1;
	}

	@Override
	public void EchangerLesCartes(JoueurIA ia,HashSet<Carte> sMain,
			HashSet<Carte> nonSVisible) {
		Carte tmp1 = null;
		Carte tmp2 = null;
		if (sMain.isEmpty() || nonSVisible.isEmpty())
			return;
		for (Iterator<Carte> iterator = sMain.iterator(); iterator.hasNext();) {
			Carte c1 = (Carte) iterator.next();
			tmp1 = null;
			int[] tab = new int[2];
			tab[0] = c1.getValeur();
			if (!nonSVisible.isEmpty()) {
				for (Iterator<Carte> iterator2 = nonSVisible.iterator(); iterator2
						.hasNext();) {
					Carte c2 = (Carte) iterator2.next();
					tmp2 = c2;
					tab[1] = c2.getValeur();
					break;
				}
			}
			ia.echangerCartes(tab);
			nonSVisible.remove(tmp2);
			sMain.remove(tmp1);
			if (sMain.isEmpty() || nonSVisible.isEmpty())
				return;
		}

	}

}
