package vue;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import jeu.Joueur;
import mVCBatailleNorvegienne.MVCBN;
/**
 * La classe vue 
 * Donc c'est toute la vue de l'interface graphique
 * Elle implemente la Classe JFrame afin de pouvoir creer une grande fenetre qui contiendra d'autre composants
 */
public class BNVue extends JFrame {
	private static final long serialVersionUID = 1L;
	private CardLayout cl = new CardLayout();
	private JPanel panel = new JPanel();
	private JPanel plateauDuJeu;
	private JPanel panelLabelTour;
	private JLabel labelTour = new JLabel("", SwingConstants.CENTER);
	private JPanel panelCartesCachees;
	private JPanel panelCartesVisibles;
	private JPanel panelCartesEnMain;
	private JPanel panelTas;
	private HashSet<JButton> bCartesEnMain = new HashSet<JButton>();
	private HashSet<JButton> bCartesVisibles = new HashSet<JButton>();
	private HashSet<JButton> bCartesCachees = new HashSet<JButton>();
	private String titre = "Jouer à la bataille norvégienne !";
	private String[] listContent = { titre + " Entrez votre nom :", titre,
			"Vous avez vaincu !" };
	JTextField texteNom = new JTextField(10);
	JTextField texteNbJoueur = new JTextField(2);
	JButton butLauch = new JButton("Lancer la partie !");
	int indice = 0;
	Color vertTapis = new Color(42, 114, 32);

	public BNVue() {
		this.add(panel);
		this.setTitle("Jouer à la bataille norvégienne !");
		JLabel labelNom = new JLabel("Entrez votre nom :");
		JLabel labelNbJoueur = new JLabel("Nombre de joueurs (entre 2 et 20) :");
		JPanel miseEnPlaceDesJoueurs = new JPanel();
		miseEnPlaceDesJoueurs.setLayout(new GridLayout(3, 2, 5, 5));
		miseEnPlaceDesJoueurs.setBorder(new EmptyBorder(new Insets(10, 10, 10,
				10)));

		panel.add(miseEnPlaceDesJoueurs);
		miseEnPlaceDesJoueurs.add(labelNom);
		miseEnPlaceDesJoueurs.add(texteNom);
		miseEnPlaceDesJoueurs.add(labelNbJoueur);
		miseEnPlaceDesJoueurs.add(texteNbJoueur);
		miseEnPlaceDesJoueurs.add(new JLabel(""));
		miseEnPlaceDesJoueurs.add(butLauch);

		JPanel boutonPane = new JPanel();

		panel.setLayout(cl);
		panel.add(miseEnPlaceDesJoueurs, listContent[0]);
		this.getContentPane().add(boutonPane, BorderLayout.NORTH);
		this.getContentPane().add(panel, BorderLayout.CENTER);

		this.setVisible(true);
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		JMenuBar bar = new JMenuBar();
		JMenu parti= new JMenu("Partie");
		JMenuItem exit = new JMenuItem("Close");
		exit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	
		JMenuItem save = new JMenuItem("Sauvegarder");
		save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				MVCBN.saveJeu("fichier");
				System.exit(0);			}
		});
		JMenuItem load = new JMenuItem("Reprendre");	
		load.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					MVCBN.chargerJeu("fichier");
				} catch (IOException e1) {
					e1.printStackTrace();
				}}
		});
		
		parti.add(save);
		parti.add(load);
		parti.add(exit);
		bar.add(parti);
		
		//ajout de l'onglet
		JMenu fileMenu = new JMenu("?");
		bar.add(fileMenu);
		JMenuItem newMenuItem = new JMenuItem("Afficher de l'aide",
				KeyEvent.VK_N);
		fileMenu.add(newMenuItem);
		newMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Aide rf = new Aide();
			}
		});

		JMenuItem newMenuItem1 = new JMenuItem(
				"A propos de Bataille Norvegienne", KeyEvent.VK_N);
		fileMenu.add(newMenuItem1);

		setJMenuBar(bar);
	}
	/**
	 * La methode qui gere la vue graphique du plateau de jeu
	 * En ajoutant des conteneurs qui vont representer le tas, les cartes visibles, les cartes a face cachees et celle a face visible
	 *Elle colorie le fond de l'image en vert et borde les conteners 
	 */
	public void MiseEnPlaceDuPlateau() {
		plateauDuJeu = new JPanel();
		panelLabelTour = new JPanel();
		panelTas = new JPanel();
		panelCartesCachees = new JPanel();
		panelCartesVisibles = new JPanel();
		panelCartesEnMain = new JPanel();
		panelLabelTour.setBackground(vertTapis);
		panelTas.setBackground(vertTapis);
		panelCartesCachees.setBackground(vertTapis);
		plateauDuJeu.setBackground(vertTapis);
		panelCartesVisibles.setBackground(vertTapis);
		panelCartesEnMain.setBackground(vertTapis);
		panelTas.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panelCartesVisibles.setBorder(BorderFactory.createEmptyBorder(5, 5, 5,
				5));
		plateauDuJeu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panelCartesCachees.setBorder(BorderFactory
				.createEmptyBorder(5, 5, 5, 5));
		panelCartesEnMain
				.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panelLabelTour
				.setLayout(new BoxLayout(panelLabelTour, BoxLayout.X_AXIS));
		panelCartesEnMain.setLayout(new BoxLayout(panelCartesEnMain,
				BoxLayout.X_AXIS));
		panelCartesVisibles.setLayout(new BoxLayout(panelCartesVisibles,
				BoxLayout.X_AXIS));
		panelCartesCachees.setLayout(new BoxLayout(panelCartesCachees,
				BoxLayout.X_AXIS));
		plateauDuJeu.setLayout(new BoxLayout(plateauDuJeu, BoxLayout.Y_AXIS));
		plateauDuJeu.add(panelTas);
		plateauDuJeu.add(panelCartesCachees);
		plateauDuJeu.add(panelCartesVisibles);
		plateauDuJeu.add(panelCartesEnMain);
		plateauDuJeu.add(panelLabelTour, BorderLayout.CENTER);

		panelLabelTour.add(labelTour);

		panel.add(plateauDuJeu, listContent[1]);

	}
	/**
	 * Cette methode au bouton  nomme "lancer la partie" d'effectuer une action des qu'on clique dessus
	 * @param listenForLancerButton
	 */
	public void addBNListener(ActionListener listenForLancerButton) {
		butLauch.addActionListener(listenForLancerButton);
	}
	/**
	 * La methode permet au bouton decouter une action
	 * @param b :le bouton qui attend une action 
	 * @param listenerPourBoutonCarte l'action a effectuer sur le buton b
	 * 
	 */
	public void addBoutonCartes(JButton b,
			ActionListener listenerPourBoutonCarte) {
		b.addActionListener(listenerPourBoutonCarte);
	}
	/**
	 * 
	 * @param numPanel:le numero du panel a afficher
	 */
	public void changerDePanel(int numPanel) {
		cl.show(panel, listContent[numPanel]);
	}
	/**
	 * Elle premet de recuperer le nombre de joueurs 
	 * @param a est une chaine de caractere , c'est le nombre entre par le joueur
	 */
	public void setTexteNbJoueur(String a) {
		texteNbJoueur.setText(a);
	}
	/**
	 *   Elle premet de recuperer le nom du joueur humain 
	 * @param b est une chaine de caractere , c'est le nombre entre par le joueur
	 */
	public void setTexteNom(String b) {
		texteNom.setText(b);
	}
	/**
	 * Elle convertit la chaine de caractere entree par le joueur en entier
	 * @returnle nombre de joueur entre par le joueur humain
	 */
	public int getNombreDeJoueur() {
		if (texteNbJoueur.getText().length() != 0) {
			try {
				@SuppressWarnings("unused")
				int i = Integer.parseInt(texteNbJoueur.getText());
				System.out.println("Valeur OK");
			} catch (Exception e) {
				System.out.println("Ce n'est pas un entier");
				return -1;
			}
			return Integer.parseInt(texteNbJoueur.getText());
		}
		return -1;
	}
	/**
	 * Elle recupere le texte entre comme etant le nom du joueur
	 * @return le texte entre par le joueur
	 */
	public String getNomDuJoueur() {
		if (!texteNom.getText().equals(""))
			return texteNom.getText();
		else
			return "";
	}
	/**
	 * Cette methode affiche une boite de dialogue 
	 * @param g  est la chaine de caractere affichee dans la boite de dialogue
	 */
	public void creerUneFenetreDinformation(String g) {
		JOptionPane.showMessageDialog(this, g);
	}
	/**
	 * La methode parcourt la liste des joueurs , recupere leur nom et l' ecrit dans un tableau de chaine de caractere
	 * ces noms sont ecrits dans la boite de dialogue
	 * et le joueur humain pourra choisir le joueur sur qui envoyer le tas
	 * @param list la liste de tous les joueurs
	 * @return le nom du joueur dont le joueur humain veut envoyer le tas
	 */
	public String choixListeJoueurLancerTas(LinkedList<Joueur> list) {
		String[] tableauDeJoueur = new String[list.size() - 1];
		int i = 0;
		for (Iterator<Joueur> iterator = list.iterator(); iterator.hasNext();) {
			Joueur joueur = (Joueur) iterator.next();
			if (joueur != list.getFirst()) {
				tableauDeJoueur[i] = joueur.getNom();
				i++;
			}
		}
		DialogChoixJoueurEnvoieTas a = new DialogChoixJoueurEnvoieTas(
				tableauDeJoueur);
		return a.getnomChoisi();
	}
	/**
	 * Cette methode permet de donner des noms aux cartes de face visible
	 * @param NomFichiers: c'est une liste noms
	 */
	public void AfficheCartesVisibles(ArrayList<String> NomFichiers) {
		panelCartesVisibles.removeAll();
		for (Iterator<String> iterator = NomFichiers.iterator(); iterator
				.hasNext();) {
			String string = (String) iterator.next();
			JButton b = getCarteButton(string);
			b.setName(string);
			bCartesVisibles.add(b);
			panelCartesVisibles.add(panelCartesEnMain.add(b));
		}
		this.pack();
	}
	/**
	 *   Cette methode permet de donner des noms aux cartes en main
	 *
	 * ces cartes seront seront affichees dans l'interface  graphique et seront a face decouvert 
	 * @param NomFichiers:  c'est une liste noms
	 */
	public void AfficheCartesEnMain(ArrayList<String> NomFichiers) {
		panelCartesEnMain.removeAll();
		for (Iterator<String> iterator = NomFichiers.iterator(); iterator
				.hasNext();) {
			String string = (String) iterator.next();
			JButton b = getCarteButton(string);
			b.setName(string);
			bCartesEnMain.add(b);
			panelCartesEnMain.add(b);
		}
		this.pack();
	}
	/**
	 * Cette methode permet de donner des noms aux cartes de face cachee
	 * et elles seront visibles sur l'interface  et seront a face decouvert
	 * @param NomFichiers:  c'est une liste noms
	 */
	public void AfficherCartesCachees(ArrayList<String> NomFichiers) {
		panelCartesCachees.removeAll();
		for (Iterator<String> iterator = NomFichiers.iterator(); iterator
				.hasNext();) {
			String string = (String) iterator.next();
			JButton b = getCarteButton("hidden.png");
			b.setName(string);
			bCartesCachees.add(b);
			panelCartesCachees.add(b);
		}
	}
	/**
	 * Elle affiche sur l'interface graphique la dernier que carte que vient de jouer chaque joueur
	 * @param nomDeLaCarte que vient de jouer chaque carte
	 * @param tour : le tour auquel cette carte a ete posee
	 */
	public void afficherDerniereCarteDeLatable(String nomDeLaCarte, int tour) {
		panelTas.removeAll();
		if (nomDeLaCarte.equals("")) {
			String s;
			if (tour <= 1)
				s = "<html><font color=\"white\"><center>Pas<br>encore<br>de tas</center></color></html>";
			else
				s = "<html><font color=\"white\"><center>Tas<br>Vide</center></color></html>";
			panelTas.add(new JLabel(s), BorderLayout.CENTER);
			// this.pack();
			return;
		}
		panelTas.removeAll();
		panelTas.add(getCarteButton(nomDeLaCarte), BorderLayout.CENTER);
		panel.revalidate();
		panel.repaint();
		pack();
	}
	/**
	 * Cette methode permet de creer une carte, lui affecte une valeur et un nom
	 *la carte est affichee sur l'interface graphique
	 * @param fileName est le nom de la carte 
	 * @return
	 */
	public JButton getCarteButton(String fileName) {
		JButton button = new JButton();
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setIcon(new ImageIcon(
				"images/Cartes/"
						+ fileName));
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		return button;
	}
	public BufferedImage getBufferedImageFromImagesFolder(String fileName) {
		BufferedImage myPicture = null;
		try {

			myPicture = ImageIO.read(new File(
					"images/Cartes/"
							+ fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myPicture;
	}
	/**
	 * Permet d'afficher les cartes en main
	 * @return cette collection de cartes
	 */
	public HashSet<JButton> getbCartesEnMain() {
		return bCartesEnMain;
	}
	/**
	 * Permet d'afficher les cartes de face visible
	 * @return cette collection de cartes
	 */
	public HashSet<JButton> getbCartesVisibles() {
		return bCartesVisibles;
	}
	/**
	 *  Permet d'afficher les cartes de face cachee
	 * @return cette collection de cartes
	 */
	public HashSet<JButton> getbCartesCachees() {
		return bCartesCachees;
	}

	public void popupInformationContrerAs() {
		JOptionPane
				.showMessageDialog(
						this,
						"Un joueur veut vous envoyer le tas a la figure\nChoisissez une carte pour vous defendre",
						"Vous etes attaque !", JOptionPane.INFORMATION_MESSAGE);
	}

	public void popupInformationImpossible() {
		JOptionPane.showMessageDialog(this,
				"Vous ne pouvez pas poser cette carte dans le contexte !",
				"Impossible", JOptionPane.INFORMATION_MESSAGE);
	}

	public void popInformationRamassertasAs() {
		JOptionPane
				.showMessageDialog(
						this,
						"Vos cartes ne vous permettent pas de contrer un As,\nvous ramasser donc le tas.",
						"Incapacite de se defendre",
						JOptionPane.INFORMATION_MESSAGE);
	}

	public void popInformationRamassertas() {
		JOptionPane
				.showMessageDialog(
						this,
						"Vos cartes ne vous permettent pas de jouer sur le tas,\nvous ramasser donc le tas.",
						"Incapacite de jouer", JOptionPane.INFORMATION_MESSAGE);
	}

	public void popupInformationPasserLeTour() {
		JOptionPane
				.showMessageDialog(
						this,
						"Un ou plusieurs 8 ont ete poses, vous devez passer votre tour.",
						"Passer un tour", JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean popupRecommencerUnePartie(String nomGagnant) {
		Object[] options = { "Oui, super !", "Non merci" };
		int n = JOptionPane.showOptionDialog(this,
				"Felicitation : "+nomGagnant+" vous avez gagne la partie !\n\n"+
				"Voulez vous refaire une partie ? " + "\nLaissez vous tenter ...",
				"Victoire", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		return (n==0);
	}

	public void setLabelTour(int nbTour, String nom,
			LinkedList<Joueur> listeDesJoueurs) {
		String texte;
		if (nom.equals(listeDesJoueurs.getFirst().getNom()))
			texte = " : C'est a vous de jouer !!";
		else
			texte = " : C'est a " + nom + " de jouer !!";
		String s = "<html><font color='white'>Tour n° " + nbTour + texte
				+ " </font></html>";			
		labelTour.setText(s);
		validate();
		repaint();
		pack();
	}
	public JPanel getPanelPlateauDuJeu(){
		return plateauDuJeu;
	}
}
