package mVCBatailleNorvegienne;

import controleur.BNControleur;
import jeu.PartieDeCartes;
import vue.BNVue;

public class MVCBN {
	public static void main(String[] args) {
		BNVue theView = new BNVue();
		PartieDeCartes theModel = new PartieDeCartes();
		BNControleur theContoleur = new BNControleur(theView, theModel);
		theView.setVisible(true);
		while (true) {
			synchronized (theContoleur.verrou) {
				try {
					// je bloque en attendant la réponse
					theContoleur.verrou.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("vdrggd");
			theContoleur.deroulementDujeu();
		}
	}
}