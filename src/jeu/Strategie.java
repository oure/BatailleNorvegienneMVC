package jeu;
import java.util.HashSet;
/**
 * La classe Strategie
 *
 */
import java.util.LinkedList;

public interface Strategie {
	public Joueur choixDuJoueurCibleePourEnvoyerLetas(JoueurIA j, LinkedList<Joueur> lj);
	public int nombreDeHuitQueIaDoitPoser(JoueurIA ia, LinkedList<Joueur> lj);
	public int contrerUnAs(JoueurIA ia);
	public void EchangerLesCartes(JoueurIA ia,
			HashSet<Carte> sMain,
			HashSet<Carte> nonSVisible);
}
