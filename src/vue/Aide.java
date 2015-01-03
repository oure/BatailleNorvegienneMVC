package vue;

import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Aide extends JFrame implements Serializable {
	JTextArea text;

	public Aide() {
		this.setTitle("Aide");
		text = new JTextArea(
				"\n Au début du jeu, vous pouvez faire des échanges entre vos cartes en main et celles de faces visibles sur la table \n\n Jouez un As pour envoyer le tas sur un joueur  \n\n Vous pouvez toujours jouer un 2 \n\n Un 8 posé, permet au joueur suivant de passer son tourSi le 7 est la dernière carte posée, vous devez jouez une carte de valeur inférieure \n\n Jouez le 10 pour supprimer du jeu les cartes du tas \n ");
		this.add(text);
		text.setEditable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
}