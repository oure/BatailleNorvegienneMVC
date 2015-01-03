package mVCBatailleNorvegienne;
/**
 * La classe Model Vue Controleur
 * Dans cette classe se fait la sauvegarde et la restauration d'une partie de jeu
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import controleur.BNControleur;
import jeu.PartieDeCartes;
import vue.BNVue;

public class MVCBN implements Serializable {
	private static BNVue theView;
	//methode pour sauvegarder le jeu
	
	public static void saveJeu(String nomfichier) {
		try {
			FileOutputStream fos = new FileOutputStream(nomfichier);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(theView);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//methode pour recharger le jeu
	
	public static BNVue chargerJeu(String nomfichier) throws IOException {
		BNVue theView= new BNVue();
		
			FileInputStream fis = new FileInputStream(nomfichier);
			ObjectInputStream ois = new ObjectInputStream(fis);
			try {
				 theView=(BNVue)	ois.readObject();}
		 catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
	fis.close();

	return theView;
	}
	
	
	public static void main(String[] args) {
		BNVue theView = new BNVue();
		PartieDeCartes theModel = new PartieDeCartes();
		BNControleur theContoleur = new BNControleur(theView, theModel);
		theView.setVisible(true);
		synchronized (theContoleur.verrou) {
			try {
				theContoleur.verrou.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// je bloque en attendant la réponse
		}
		theContoleur.deroulementDujeu();

	}
}