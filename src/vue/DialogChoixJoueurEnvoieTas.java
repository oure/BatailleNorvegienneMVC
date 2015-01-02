package vue;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class DialogChoixJoueurEnvoieTas {
	private String nomChoisi;	

	public DialogChoixJoueurEnvoieTas(String[] tableauDeJoueur) {
		nomChoisi = tableauDeJoueur[0];
		final JTextPane textPane = new JTextPane();
		JComboBox<String> combo = new JComboBox<String>(tableauDeJoueur);
		combo.setSelectedIndex(0);
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<?> cb = (JComboBox<?>) e.getSource();
				nomChoisi = (String) cb.getSelectedItem();
			}
		});

		final JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setAlignmentX(Component.RIGHT_ALIGNMENT);

		final JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		final JDialog dialog = new JOptionPane(content,
				JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION)
				.createDialog(null, "Vous avez pose un As !!");

		JLabel message = new JLabel("Choisissez votre victime : ");
		message.setBorder(new EmptyBorder(10, 10, 10, 10)); 
		//Dimension labelSize = message.getPreferredSize();
		//labelSize.setSize(300, labelSize.height);
		//message.setPreferredSize(labelSize);
		content.add(message);
		content.add(combo);

		dialog.setResizable(false);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);
	}

	public String getnomChoisi() {
		return nomChoisi;
	}
}
