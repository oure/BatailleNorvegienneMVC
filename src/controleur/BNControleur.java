package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;

import jeu.Carte;
import jeu.Carte.Couleur;
import jeu.CartesEnMain;
import jeu.CartesFacesCachees;
import jeu.CartesfacesVisibles;
import jeu.Joueur;
import jeu.JoueurIA;
import jeu.PartieDeCartes;
import jeu.Pioche;
import jeu.Table;
import vue.BNVue;

public class BNControleur {
	public Object verrou = new String("Je suis un verrou");
	public Object verrou2 = new String("Je suis un deuxieme verrou");
	private BNVue vue;
	private PartieDeCartes pdc;
	private int valeur;
	private Couleur couleur;
	private int nombreDeCarteAjouer = 1;
	int tour = 1;

	public BNControleur(BNVue vue, PartieDeCartes pdc) {
		this.vue = vue;
		this.pdc = pdc;
		vue.addBNListener(new BouttonLancerListenner());
	}
	
	/**
	 * Cette methode permet  de ne plus effectuer une action des qu'on clique sur les cartes cartes 
	 */

	public void desalouageListener() {
		for (Iterator<JButton> iterator = vue.getbCartesEnMain().iterator(); iterator
				.hasNext();) {
			JButton bouton = iterator.next();
			bouton.removeActionListener(new BouttonCartesEnMainListener());
		}
		for (Iterator<JButton> iterator = vue.getbCartesVisibles().iterator(); iterator
				.hasNext();) {
			JButton bouton = iterator.next();
			bouton.removeActionListener(new BoutonCartesVisiblesListener());
		}
		for (Iterator<JButton> iterator = vue.getbCartesCachees().iterator(); iterator
				.hasNext();) {
			JButton bouton = iterator.next();
			bouton.removeActionListener(new BoutonCartesCacheesListener());
		}
	}
	
	/**
	 * Cette methode permet de distribuer  au joueur humain des cartes en main, visible et de face cachee 
	 * sur le plateau de jeu
	 */

	public void miseAJourEcouteBoutons() {
		System.out.println("MISE EN PLACE DES LISTENNER");
		if (!pdc.getListeDesJoueurs().getFirst().getCartesEnMain()
				.getCartemain().isEmpty()) {
			for (Iterator<JButton> iterator = vue.getbCartesEnMain().iterator(); iterator
					.hasNext();) {
				JButton bouton = iterator.next();
				vue.addBoutonCartes(bouton, new BouttonCartesEnMainListener());
			}
			return;
		}
		if (pdc.getListeDesJoueurs().getFirst().getCartesEnMain()
				.getCartemain().isEmpty()) {
			for (Iterator<JButton> iterator = vue.getbCartesVisibles()
					.iterator(); iterator.hasNext();) {
				JButton bouton = iterator.next();
				vue.addBoutonCartes(bouton, new BoutonCartesVisiblesListener());
				return;
			}
		}
		if (pdc.getListeDesJoueurs().getFirst().getCartesEnMain()
				.getCartemain().isEmpty()
				&& pdc.getListeDesJoueurs().getFirst().getCartefaceVisibles()
						.getCartesVisibles().isEmpty()) {
			for (Iterator<JButton> iterator = vue.getbCartesCachees()
					.iterator(); iterator.hasNext();) {
				JButton bouton = iterator.next();
				vue.addBoutonCartes(bouton, new BoutonCartesCacheesListener());
			}
			return;
		}

	}

	/**
	 * Cette classe permet de lancer un plateau de jeu , pret a debuter, des qu'on clique 
	 * sur le bouton lancer le jeu
	 *
	 */
	class BouttonLancerListenner implements ActionListener {
		@Override
		
		/**
		 * Cette methode permet de recuperer le nom et le nombre de joueur entres par le joueur humain
		 * de lancer une partie, distribuer les cartes sur le plateau de jeu
		 */
		public void actionPerformed(ActionEvent e) {
			boolean erreur = false;
			int a = vue.getNombreDeJoueur();
			if (a == -1 || a < 2 || a > 20) {
				vue.setTexteNbJoueur("il doit etre compris entre 2 et 20 !");
				erreur = true;
			}
			String name = vue.getNomDuJoueur();
			if (name == "" || name == null) {
				vue.setTexteNom("Votre nom ne doit pas être vide !");
				erreur = true;
			}
			if (erreur)
				return;
			System.out.println("tout est ok lancement de la partie");
			pdc = new PartieDeCartes();
			pdc.miseEnPlaceDeLaListeDesJoueurs(a, name);
			pdc.miseEnPlaceDesJeuxdeCartes();
			pdc.distribuer();
			pdc.decalerListedesJoueurs(); // Le distributeur joue en dernier
			System.out.println(pdc.getListeDesJoueurs());
			vue.MiseEnPlaceDuPlateau();
			
			miseAJourDeLaffichage();
			miseAJourEcouteBoutons();

			pdc.afficherListeDesJoueurs();
			vue.changerDePanel(1);
			synchronized (verrou) {
				verrou.notify();// je débloque
				System.out.println("UNLOCK DU GAME");
			}
		}
	}

	
	class BouttonCartesEnMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out
					.println("Vous avez cliquez sur une carte dans votre main !");
			String nomCarte = getButtonName((JButton) e.getSource());
			valeur = Integer.parseInt(nomCarte.replaceAll("[^0-9]", ""));
			String scouleur=nomCarte.replaceAll("[^a-zA-Z]", "");
			scouleur = Character.toString(scouleur.charAt(0)).toUpperCase()+scouleur.substring(1);
			couleur = Couleur.valueOf(scouleur);
			//System.out.println(valeur + couleur);
			synchronized (verrou) {
				verrou.notify();// je débloque
			}
			synchronized (verrou2) {
				verrou2.notify();// je débloque
			}
		}
	}

	class BoutonCartesVisiblesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out
					.println("Vous avez cliquez sur une de vos cartes visibles !");
			String nomCarte = getButtonName((JButton) e.getSource());
			valeur = Integer.parseInt(nomCarte.replaceAll("[^0-9]", ""));

		}
	}

	class BoutonCartesCacheesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String nomCarte = getButtonName((JButton) e.getSource());
			System.out.println(nomCarte);
			valeur = Integer.parseInt(nomCarte.replaceAll("[^0-9]", ""));
		}
	}
/**
 * Cette permet d'avoir le nom des cartes en main que possede le joueur humain
 * @return la liste des noms de ces cartes
 */
	public ArrayList<String> avoirLaListeDesFichierImagesCartesEnMain() {
		ArrayList<String> liste = new ArrayList<String>();
		HashSet<Carte> hc = pdc.getCartesEnMainJoueurHumain();
		for (Iterator<Carte> iterator = hc.iterator(); iterator.hasNext();) {
			Carte carte = (Carte) iterator.next();
			String valeur = Integer.toString(carte.getValeur());
			String couleur = carte.getCouleur().toString().toLowerCase();
			liste.add(valeur + "_" + couleur + ".png");
		}
		System.out.println(liste);
		return liste;
	}
	/**
	 * Cette permet d'avoir le nom des cartes face visible que possede le joueur humain
	 * @return la liste des noms de ces cartes
	 */
	public ArrayList<String> avoirLaListeDesFichierImagesCartesVisibles() {
		ArrayList<String> liste = new ArrayList<String>();
		HashSet<Carte> hc = pdc.getCartesVisiblesJoueurHumain();
		for (Iterator<Carte> iterator = hc.iterator(); iterator.hasNext();) {
			Carte carte = (Carte) iterator.next();
			String valeur = Integer.toString(carte.getValeur());
			String couleur = carte.getCouleur().toString().toLowerCase();
			liste.add(valeur + "_" + couleur + ".png");
		}
		return liste;
	}
	/**
	 * Cette permet d'avoir le nom des cartes de face cachee que possede le joueur humain
	 * @return la liste des noms de ces cartes
	 */

	public ArrayList<String> avoirLaListeDesFichierImagesCartesCachees() {
		ArrayList<String> liste = new ArrayList<String>();
		HashSet<Carte> hc = pdc.getCartesCacheesJoueurHumain();
		for (Iterator<Carte> iterator = hc.iterator(); iterator.hasNext();) {
			Carte carte = (Carte) iterator.next();
			String valeur = Integer.toString(carte.getValeur());
			String couleur = carte.getCouleur().toString().toLowerCase();
			liste.add(valeur + "_" + couleur + ".png");
		}
		return liste;
	}

	/**
	 *  Cette methode recupere la derniere carte posee sur la table
	 * @return la derniere carte posee sur la table
	 */
	public String recupererDerniereCarteDeLaTable() {
		if (pdc.getTable().isEmpty())
			return "";
		Carte carte = pdc.getTable().getDerniereCarteDuTas();
		String valeur = Integer.toString(carte.getValeur());
		String couleur = carte.getCouleur().toString().toLowerCase();
		return valeur + "_" + couleur + ".png";
	}
/**
 * Cette methode permet de mettre a jour l'affichage des cartes afin que le joueur humain ait fini de jouer
 */
	public void miseAJourDeLaffichage() {
		vue.AfficherCartesCachees(avoirLaListeDesFichierImagesCartesCachees());
		vue.AfficheCartesVisibles(avoirLaListeDesFichierImagesCartesVisibles());
		vue.AfficheCartesEnMain(avoirLaListeDesFichierImagesCartesEnMain());
		vue.afficherDerniereCarteDeLatable(recupererDerniereCarteDeLaTable(),
				tour);
	}
/**
 * Permet d'avoir le nom d'une carte
 * @param myButton: le bouton dont on recupere son nom
 * @return le nom de ce bouton
 */
	public String getButtonName(JButton myButton) {
		return myButton.getName().substring(0, myButton.getName().length() - 4);
	}
/**
 * Cette methode lance le jeu
 */
	public synchronized void lancementDeLaPartie() {
		deroulementDujeu();
	}

	/**
	 * methode qui deroule une partie sur le plateau graphique du jeu
	 */
	public void deroulementDujeu() {
		Joueur gagnant = null;
		boolean cond = true;
		int compteurPourPasserLesTours = 0; // compte le nombre de joueur qui
											// vont passer leur tour apres que
											// de 8 ont etes jouees
		boolean passerLeTour = false;
		int nombreDejoueurQuiPasseLeurTour = 0;
		tour = 1;
		vue.echangeCarte();
		//pdc.echangerLesCartes();
		HashSet<Carte> derniereCartesPosees = new HashSet<Carte>();
		while (cond) {
			for (Iterator<Joueur> iterator = pdc.getListeDesJoueurs()
					.iterator(); iterator.hasNext();) {
				Joueur joueur = (Joueur) iterator.next();
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
				if (!passerLeTour) {
					vue.setLabelTour(tour, joueur.getNom(),
							pdc.getListeDesJoueurs());
					// tour++;
					System.out.println("\nA vous de jouer " + joueur.getNom()
							+ " !");
					if (pdc.getListeDesJoueurs().get(0) == joueur && tour != 1) {
						pdc.getListeDesJoueurs().get(0).afficherLesCartes();
					}
					if (joueur.estCeQueLeJoueurPeutJouer(derniereCartesPosees,
							pdc.getTable())) {
						derniereCartesPosees.clear();
						derniereCartesPosees = jouerLibrement(joueur,
								pdc.getTable(), pdc.getPioche(),
								derniereCartesPosees, pdc.getListeDesJoueurs());
						miseAJourDeLaffichage();
						miseAJourEcouteBoutons();

						System.out.println("Les dernieres cartes posees sont "
								+ derniereCartesPosees);
						joueur.PoserUnDix(derniereCartesPosees, pdc.getTable());
						PoserUnAs(joueur, derniereCartesPosees, pdc.getTable(),
								pdc.getListeDesJoueurs());
						nombreDejoueurQuiPasseLeurTour = joueur.PoserUnHuit(
								derniereCartesPosees, pdc.getTable());
					} else {
						joueur.ajouterCartesEnMain(pdc.getTable()
								.ramasserLeTas());
						if (joueur == pdc.getListeDesJoueurs().getFirst())
							vue.popInformationRamassertas();
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
				miseAJourDeLaffichage();
				miseAJourEcouteBoutons();
			}
		}
		if (vue.popupRecommencerUnePartie(gagnant.getNom()))
			recommencerLaPartie();
		else
			System.exit(0);
	}
/**
 * Cette methode c'est pour redemarrer une partie
 * 
 */
	private void recommencerLaPartie() {
		vue.setTexteNom("");
		vue.setTexteNbJoueur("");
		vue.getPanelPlateauDuJeu().removeAll();
		vue.changerDePanel(0);
		vue.validate();
		vue.pack();
		vue.repaint();
	}

	/**
	 * Envoi de la table sur un joueur si un AS a ete joue
	 * 
	 * @param derniereCartesPosees
	 * @param table
	 * @param listeDesJoueurs
	 *            la liste des joueurs, ceci permet de choisir la victime en
	 *            fonction du type du joueur et de sa strategie, c est a dire
	 *            sur qui envoyer la table
	 */
	public void PoserUnAs(Joueur joueur, HashSet<Carte> derniereCartesPosees,
			Table table, LinkedList<Joueur> listeDesJoueurs) {
		CartesEnMain cartesEnMain = listeDesJoueurs.getFirst()
				.getCartesEnMain();
		CartesfacesVisibles cartefaceVisibles = listeDesJoueurs.getFirst()
				.getCartefaceVisibles();
		CartesFacesCachees carteFacesCachees = listeDesJoueurs.getFirst()
				.getCarteFacesCachees();
		Joueur j = null;
		for (Iterator<Carte> iterator = derniereCartesPosees.iterator(); iterator
				.hasNext();) {
			Carte carte = (Carte) iterator.next();
			if (carte.getValeur() == 1) {
				j = choixDuJoueurCibleePourEnvoyerLaTable(joueur,
						listeDesJoueurs);
				if (j.estCeQueJeSuisEnMesureDecontrerUnAs(j)) {
					System.out.println(j.getNom()
							+ " : \"Je suis en mesure de contrer l'as\"");
					ChoixMethodeseDefendreContreUnAs(j, joueur, table);
					return;
				} else {
					Carte c = null;
					if (cartesEnMain.isEmpty() && cartefaceVisibles.isEmpty()
							&& !carteFacesCachees.isEmpty()) {
						c = carteFacesCachees.prendreAuhasard();
						if (c.getValeur() == 2 || c.getValeur() == 1) {
							table.ajouterCarteALaTable(c);
							System.out
									.println("Vous avez contrer l'as en prennant au hasard une de vos cartes cachees !");
							return;
						} else {
							carteFacesCachees.getCartesCachees().add(c);
							System.out
									.println("Vous avez tirer au hasard une de vos cartes cachees mais vous n'avez pas ete capable de le contrer");
						}
					}
					System.out
							.println("Je suis suis pas en mesure de contrer l'as");
					joueur.envoyerTasSurJoueur(j, table);
					if (!(j instanceof JoueurIA)) {
						vue.popInformationRamassertasAs();
					}
					System.out.println(j);
				}
				return;
			}
		}
	}

	public void ChoixMethodeseDefendreContreUnAs(Joueur joueurCible,
			Joueur joueurAttaquant, Table table) {
		if ((joueurCible instanceof JoueurIA)) {
			System.out.println("JE SUIS IA ET JE VAIS ME DEFENDRE");
			((JoueurIA) joueurCible).seDefendreContreUnAs(table);
			return;
		}
		System.out.println("JE SUIS HUMAIN ET JE VAIS ME DEF ");
		seDefendreContreUnAs(joueurCible, table);
	}

	public void seDefendreContreUnAs(Joueur j, Table table) {
		System.out
				.print("Defender vous ! Un joueur veux vous envoyer le tas ! Quelles cartes voulez vous poser ?");
		vue.popupInformationContrerAs();
		synchronized (verrou2) {
			try {
				verrou2.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(j.estPossedeDansLamain(valeur, 1));
		if (j.estPossedeDansLamain(valeur, 1) && (valeur == 2 || valeur == 1))
			table.ajouterCartesTable(j.getCartesEnMain()
					.supCarteMain(valeur, couleur, 1,true));
		else {
			System.out
					.println("Vous ne possedez pas cette carte ou elle ne peut pas contrer un as !");
			seDefendreContreUnAs(j, table);
		}
	}

	/**
	 * Ceci permet au joueur qui a pose un As de choisir sur qui envoyer la
	 * table
	 * 
	 * @param joueur2
	 * 
	 * @param lj
	 * @return le joueur qui sera victime
	 */
	public Joueur choixDuJoueurCibleePourEnvoyerLaTable(Joueur joueur2,
			LinkedList<Joueur> lj) {
		if (joueur2 instanceof JoueurIA) {
			return ((JoueurIA) joueur2)
					.choixDuJoueurCibleePourEnvoyerLaTable(lj);
		}

		String nomDuJoueurCible = vue.choixListeJoueurLancerTas(pdc
				.getListeDesJoueurs());
		nomDuJoueurCible = nomDuJoueurCible.toLowerCase();
		System.out.println("Vous envoyez le tas sur :" + nomDuJoueurCible);
		for (Iterator<Joueur> iterator = lj.iterator(); iterator.hasNext();) {
			Joueur joueur = (Joueur) iterator.next();
			if (nomDuJoueurCible.equals(joueur.getNom().toLowerCase())) {
				return joueur;
			}
		}
		System.out.println("Vous avez specifier un nom incorrect monsieur !");
		return choixDuJoueurCibleePourEnvoyerLaTable(joueur2, lj);

	}

	public HashSet<Carte> jouerLibrement(Joueur j, Table table, Pioche pioche,
			HashSet<Carte> derniereCartesPosees, LinkedList<Joueur> lljoueur) {
		System.out.println();
		if (!(j instanceof JoueurIA)) {
			return jouerLibrementHumain(table, pioche, derniereCartesPosees,
					lljoueur);
		} else
			return ((JoueurIA) j).jouerLibrement(table, pioche,
					derniereCartesPosees, lljoueur);
	}

	public void updateDerniereCarteDeLaTable(Table table, HashSet<Carte> hc) {
		table.ajouterCartesTable(hc);
		vue.afficherDerniereCarteDeLatable(recupererDerniereCarteDeLaTable(),
				tour);
	}

	public HashSet<Carte> jouerLibrementHumain(Table table, Pioche pioche,
			HashSet<Carte> derniereCartesPosees, LinkedList<Joueur> lljoueur) {
		CartesEnMain cartesEnMain = lljoueur.getFirst().getCartesEnMain();
		CartesfacesVisibles cartefaceVisibles = lljoueur.getFirst()
				.getCartefaceVisibles();
		CartesFacesCachees carteFacesCachees = lljoueur.getFirst()
				.getCarteFacesCachees();
		Joueur j = lljoueur.getFirst();
		HashSet<Carte> hc = new HashSet<Carte>();
		if (!pioche.isEmpty() && cartesEnMain.isEmpty()) {
			j.piocher(pioche);
			return hc;
		}
		if (!cartesEnMain.getCartemain().isEmpty()
				|| !cartefaceVisibles.getCartesVisibles().isEmpty()) {
			synchronized (verrou) {
				try {
					System.out.println("J'attends !");
					verrou.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}// je bloque en attendant la réponse
			}
			int nombreDeCarteAjouer = 1;
			// try {
			// Thread.sleep(2000);
			// } catch (InterruptedException e1) {
			// e1.printStackTrace();
			// }
			System.out.println("vvvvvvvvvv" + valeur + "nnnb"
					+ nombreDeCarteAjouer);
			System.out.println("vvvvvvvvvv" + cartesEnMain);
			if (j.estPossedeDansLamain(valeur, nombreDeCarteAjouer)
					&& j.estCeQueLeJoueurPeutJouerDesCartes(valeur,
							nombreDeCarteAjouer, table)) {
				hc = cartesEnMain.supCarteMain(valeur,couleur, nombreDeCarteAjouer,true);
				updateDerniereCarteDeLaTable(table, hc);
				for (int i = 1; i <= hc.size(); i++) {
					j.piocher(pioche);
				}
				return hc;
			} else if (cartesEnMain.getCartemain().isEmpty()) {
				if (j.estPossedeDansDansLesCartesVisibles(valeur,
						nombreDeCarteAjouer)
						&& j.estCeQueLeJoueurPeutJouerDesCartes(valeur,
								nombreDeCarteAjouer, table)) {
					hc = cartefaceVisibles.supCarteVisible(valeur,
							nombreDeCarteAjouer);
					updateDerniereCarteDeLaTable(table, hc);
					for (int i = 1; i <= hc.size(); i++) {
						j.piocher(pioche);
					}
					return hc;
				} else {
					System.out
							.println("Impossible vous ne pouvez pas poser cette carte !");
					jouerLibrementHumain(table, pioche, derniereCartesPosees,
							lljoueur);
				}

			} else {
				System.out
						.println("impossible vous ne pouvez pas poser cette carte !");
				jouerLibrementHumain(table, pioche, derniereCartesPosees,
						lljoueur);
			}
		}
		if (cartesEnMain.getCartemain().isEmpty()
				&& cartefaceVisibles.getCartesVisibles().isEmpty()) {
			Carte carte;
			carte = carteFacesCachees.prendreAuhasard();
			hc.add(carte);
			if (j.estCeQueLeJoueurPeutJouerDesCartes(carte.getValeur(), 1,
					table)) {
				updateDerniereCarteDeLaTable(table, hc);
			} else {
				hc.clear();
				System.out.println("Je ne peux pas jouer cette carte");
				j.ajouterCartesEnMain(table.ramasserLeTas());
				System.out.println("Vous avez ramassez les cartes de la table "
						+ j.getNom());
			}
			return hc;
		}
		return hc;
	}
}
