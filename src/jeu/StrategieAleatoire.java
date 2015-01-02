package jeu;

/**
 * La classe Strategie aleatoire
 * Elle herite de Strategie 
 */
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class StrategieAleatoire implements Strategie {
	public int nombreDeHuitQueIaDoitPoser(JoueurIA ia, LinkedList<Joueur> lj) {
		Random rand = new Random();
		int nbDeHuit = ia.nombreDeHuitQueLeJoueurPossede();
		if (nbDeHuit >= 2)
			return rand.nextInt((nbDeHuit - 1) + 1) + 1;
		else
			return 1;
	}

	public Joueur choixDuJoueurCibleePourEnvoyerLetas(JoueurIA j,
			LinkedList<Joueur> lj) {
		int num = j.getNumeroduJoueurDansLaListeDeJoueur(lj, j);
		int numeroDuJoueurDesigne = JoueurIA.randInt(0, lj.size() - 1);
		System.out.println("numeroDuJoueurDesigne " + numeroDuJoueurDesigne);
		System.out.println("num " + num);
		// Il serait idiot qu'un joueurIA se lance le tas à lui même.
		if (numeroDuJoueurDesigne != num) {
			System.out.println(j.getNom()+" envoie les cartes de la table a"
					+ lj.get(numeroDuJoueurDesigne).getNom());
			return lj.get(numeroDuJoueurDesigne);
		} else
			return j.choixDuJoueurCibleePourEnvoyerLaTable(lj);
	}

	public int contrerUnAs(JoueurIA ia) {
		int vCarte= JoueurIA.randInt(1, 2);
		int vCarte2;
		if (vCarte==1)
			vCarte2=2;
		else
			vCarte2=1;
		if (!ia.getCartesEnMain().isEmpty())
			if (ia.estPossedeDansLamain(vCarte, 1))
				return vCarte;
			else
				return vCarte2;
		if (!ia.getCartefaceVisibles().isEmpty()
				&& ia.getCartesEnMain().isEmpty())
			if (ia.estPossedeDansDansLesCartesVisibles(vCarte, 1))
				return vCarte;
			else
				return vCarte2;
		if(!ia.getCartefaceVisibles().isEmpty()
				&& !ia.getCartesEnMain().isEmpty() 
				&& ia.getCarteFacesCachees().isEmpty()){
			Carte c=ia.getCarteFacesCachees().prendreAuhasard();
			if(c.getValeur() == 1 || c.getValeur()==2)
				return c.getValeur();
			else
				ia.ajouterCarteFaceCachee(c);
		}
		return -1;
	}

	@Override
	public void EchangerLesCartes(JoueurIA ia,
			HashSet<Carte> sMain,
			HashSet<Carte> nonSVisible) {
	}
}
