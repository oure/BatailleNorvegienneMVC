package jeu;

/**
 * La classe partie de carte
 *Elle represente une partie de jeu avec des joueurs, une pioche,un table et un paquet de carte 
 */
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import jeu.Carte.Couleur;

public class PartieDeCartes {
	// attributs de la carte
	public static Scanner reader;
	private Pioche pioche = new Pioche();
	private HashSet<JeuDeCartes> setJeuDeCartes = new HashSet<JeuDeCartes>();
	private Table table = new Table();
	private LinkedList<Joueur> llJoueur = new LinkedList<Joueur>();

	/**
	 * Methode qui choisit aleatoirement le type de strategie d'un joueurIA
	 * 
	 * @param r
	 * @return une strategie
	 */
	public Strategie choixDuneStrategie(Random r) {
		if (r.nextBoolean())
			return new StrategieAleatoire();
		else
			return new StrategieOffensive();
	}

	public void setPioche(Pioche pioche) {
		this.pioche = pioche;
	}

	/**
	 * Seul l'utilisateur va entrer son nom les Joueurs IA sont generes
	 * automatiquement
	 */
	private void affichageBienvenu() {
		System.out
				.println("### Bienvenue dans dans le jeu de la Bataille Norvegienne ####");
	}

	private Object[] demandeNomEtNbJoueur() {
		Object[] a = new Object[2];
		System.out.print("-Saisissez le nombre de joueur :");
		reader = new Scanner(System.in);
		int nbJoueur = reader.nextInt();
		reader.nextLine();
		String name = null;
		System.out.print("-Entrer votre nom :");
		name = reader.nextLine();
		a[0] = name;
		a[1] = nbJoueur;
		return a;
	}

	public void miseEnPlaceDeLaListeDesJoueurs(int nbJoueur, String name) {
		for (int i = 1; i <= nbJoueur; i++) {
			Random r = new Random();
			if (i == 1)
				llJoueur.add(new Distributeur(choixDuneStrategie(r)));
			else if (i == 2)
				llJoueur.add(new Joueur(name));
			else {
				llJoueur.add(new JoueurIA(choixDuneStrategie(r)));
			}
		}
	}

	/**
	 * Cette methode permet au joueur qui est a gauche du distributeur de
	 * commencer
	 */

	public void decalerListedesJoueurs() {
		Joueur j = llJoueur.getFirst();
		llJoueur.remove();
		llJoueur.addLast(j);
	}

	/**
	 * Creation d'un ou plusieurs jeux de carte en fonction du nombre de joueur.
	 */
	public void miseEnPlaceDesJeuxdeCartes() {
		if (llJoueur.size() <= 5 && llJoueur.size() > 1) {
			setJeuDeCartes.add(new JeuDeCartes());
		} else if (llJoueur.size() > 5 && llJoueur.size() <= 11) {
			setJeuDeCartes.add(new JeuDeCartes());
			setJeuDeCartes.add(new JeuDeCartes());
		} else if (llJoueur.size() > 11 && llJoueur.size() <= 20) {
			setJeuDeCartes.add(new JeuDeCartes());
			setJeuDeCartes.add(new JeuDeCartes());
			setJeuDeCartes.add(new JeuDeCartes());
		} else
			System.out
					.println("Veuillez renseigner le bon nombre d'utilisateur ! ");
	}

	/**
	 * Methode qui renvoie la postion d'un joueur dans la LinkedList de Joueur
	 * 
	 * @param joueur
	 * @return le numero du joueur pris en parametre
	 */

	public int positionJoueurDansLaListe(Joueur joueur) {
		return (llJoueur.indexOf(joueur));
	}

	private void deroulementDujeu() {
		Joueur gagnant = null;
		boolean cond = true;
		int compteurPourPasserLesTours = 0; // compte le nombre de joueur qui
											// vont passer leur tour apres que
											// de 8 ont etes jouees
		boolean passerLeTour = false;
		int nombreDejoueurQuiPasseLeurTour = 0;
		int tour = 1;
		decalerListedesJoueurs(); // Le distributeur joue en dernier
		afficherListeDesJoueurs();
		echangerLesCartes();
		HashSet<Carte> derniereCartesPosees = new HashSet<Carte>();
		while (cond) {
			for (Iterator<Joueur> iterator = llJoueur.iterator(); iterator
					.hasNext();) {
				Joueur joueur = (Joueur) iterator.next();
				if (!passerLeTour) {
					System.out.println("\nA vous de jouer " + joueur.getNom()
							+ " !");
					if (llJoueur.get(0) == joueur && tour != 1) {
						llJoueur.get(0).afficherLesCartes();
					}
					if (joueur.estCeQueLeJoueurPeutJouer(derniereCartesPosees,
							table)) {
						derniereCartesPosees.clear();
						derniereCartesPosees = null; // .joueur.jouerLibrement(table,
						// pioche, derniereCartesPosees,llJoueur);
						System.out.println("Les dernieres cartes posees sont "
								+ derniereCartesPosees);
						joueur.PoserUnDix(derniereCartesPosees, table);
						// joueur.PoserUnAs(derniereCartesPosees, table,
						// llJoueur);
						nombreDejoueurQuiPasseLeurTour = joueur.PoserUnHuit(
								derniereCartesPosees, table);
					} else {
						joueur.ajouterCartesEnMain(table.ramasserLeTas());
						System.out
								.println("Vous avez ramassez le contenu de la table "
										+ joueur.getNom());
					}
					if (nombreDejoueurQuiPasseLeurTour != 0) {
						passerLeTour = true;
					}
					if (joueur.avoirAucuneCarte()) {
						gagnant = joueur;
						cond = false;
						break;
					}
				} else {
					System.out.println("Je passe mon tour " + joueur.getNom());
					if (compteurPourPasserLesTours + 1 >= nombreDejoueurQuiPasseLeurTour) {
						passerLeTour = false;
						nombreDejoueurQuiPasseLeurTour = 0;
					} else
						compteurPourPasserLesTours++;
				}
				tour++;
			}
		}
		System.out.println("Felicitation " + gagnant.getNom()
				+ " vous avez vaincu !\n-Voulez vous jouer a nouveau ? O/n");
		String s = reader.nextLine();
		if ((s.length() == 1 && s.charAt(0) == 'O') || s.length() == 0) {
			demarrer();
		}

	}

	private void melanger() {
		for (Iterator<JeuDeCartes> it = setJeuDeCartes.iterator(); it.hasNext();) {
			JeuDeCartes jdc = (JeuDeCartes) it.next();
			jdc.melanger();
		}
	}

	public void afficherListeDesJoueurs() {
		System.out.println("La liste des joueurs est :");
		for (Iterator<Joueur> iterator = llJoueur.iterator(); iterator
				.hasNext();) {
			Joueur joueur = (Joueur) iterator.next();
			System.out.println("\t-" + joueur.getNom());
		}
	}

	private void test() {
		decalerListedesJoueurs();
		System.out.println(new Joueur().getNumeroduJoueurDansLaListeDeJoueur(
				llJoueur, llJoueur.get(0)));
		System.out.println(llJoueur);
		HashSet<Carte> derniereCartesPosees = new HashSet<Carte>();
		table.getListe().clear();
		table.ajouterCarteALaTable(new Carte(3, Couleur.Pique));
		table.ajouterCarteALaTable(new Carte(1, Couleur.Pique));
		table.ajouterCarteALaTable(new Carte(3, Couleur.Pique));
		table.ajouterCarteALaTable(new Carte(2, Couleur.Pique));
		System.out.println(llJoueur.get(1).getNom());
		llJoueur.get(1).getCartesEnMain().supprimerToutesLesCartesEnmain();
		llJoueur.get(1).ajouterCarteEnMain(new Carte(8, Couleur.Coeur));
		llJoueur.get(1).ajouterCarteEnMain(new Carte(8, Couleur.Treffle));
		llJoueur.get(1).ajouterCarteEnMain(new Carte(8, Couleur.Carreau));
		llJoueur.get(2).ajouterCarteEnMain(new Carte(8, Couleur.Pique));
		System.out.println(llJoueur.get(2).getNom()
				+ " zrrerraaze"
				+ ((JoueurIA) llJoueur.get(2))
						.nombreDeHuitQueIaDoitPoser(llJoueur));
	}

	/**
	 * Methode qui permet au joueur humain d echanger ses cartes
	 */
	public boolean controleDesBornes(int nb, int b1, int b2) {
		return (nb >= b1 && nb <= b2);
	}

	public void echangerLesCartes() {
		for (int i = 1; i < llJoueur.size(); i++) {
			((JoueurIA) llJoueur.get(i)).echangerLesCartes();
		}
		llJoueur.get(0).afficherLesCartes();
		System.out.println("Voulez vous echanger des cartes ? N/o");
		reader = new Scanner(System.in);
		String s = reader.nextLine();
		if (s.length() == 1 && s.charAt(0) == 'o') {
			System.out.println("Nombre d'echange que vous voulez faire :");
			int nbEchange = reader.nextInt();
			if (!controleDesBornes(nbEchange, 1, 3)) {
				System.out.println("Nombre doit etre entre 1 et 3");
				echangerLesCartes();
			}
			for (int i = 0; i < nbEchange; i++) {
				if (!llJoueur.get(0).echangerCartes(
						llJoueur.get(0).choixDesCartesAEchanger()))
					i--;
			}
		}
	}

	/**
	 * choix du distributeur parmi les joueurs IA
	 */
	public void distribuer() {
		melanger();
		for (Iterator<Joueur> it = llJoueur.iterator(); it.hasNext();) {
			Joueur joueur = (Joueur) it.next();
			if (joueur instanceof Distributeur) {
				pioche = ((Distributeur) joueur).distribuer(setJeuDeCartes,
						llJoueur);
				System.out.println("je suis " + joueur.getNom()
						+ " et je vais distribuer ! ");
				break;
			}
		}
	}

	/**
	 * Methode qui demarre le jeu
	 */
	private void demarrer() {
		affichageBienvenu();
		// miseEnPlaceDeLaListeDesJoueurs();
		miseEnPlaceDesJeuxdeCartes();
		distribuer();
		deroulementDujeu();
		test();
	}

	public static void main(String[] args) {
		PartieDeCartes pdc = new PartieDeCartes();
		pdc.demarrer();
	}

	public HashSet<Carte> getCartesEnMainJoueurHumain() {
		return llJoueur.getFirst().getCartesEnMain().getCartemain();
	}

	public HashSet<Carte> getCartesVisiblesJoueurHumain() {
		return llJoueur.getFirst().getCartefaceVisibles().getCartesVisibles();
	}

	public HashSet<Carte> getCartesCacheesJoueurHumain() {
		return llJoueur.getFirst().getCarteFacesCachees().getCartesCachees();
	}

	public Pioche getPioche() {
		return pioche;
	}

	public Table getTable() {
		return table;
	}

	public LinkedList<Joueur> getListeDesJoueurs() {
		return llJoueur;
	}

}
