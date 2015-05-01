package jkt.centralisateur.applet;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SiteIdDialog extends JDialog implements ActionListener {
    private JButton buttonOk= new JButton("Ok");
    private JButton buttonCancel= new JButton("Annuler");
    private JTextField field = new JTextField();
    
    private Integer siteId;

    public SiteIdDialog(final int x, final int y) {
        setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        
        // Label du texte à saisir
        JLabel label = new JLabel("Identifiant du site");
        constraints.gridx = 0;
        constraints.gridy = 0;
        
        add(label, constraints);
        
        // Champ texte � saisir
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(field, constraints);
        
        // Bouton valider
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(buttonOk, constraints);
        buttonOk.addActionListener(this);
        
        // Bouton annuler
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(buttonCancel, constraints);
        buttonCancel.addActionListener(this);
        
        
        setModal(true);
        pack();
        setLocation(x - (getWidth() / 2), y - (getHeight() / 2));
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonOk) {
            String text = field.getText();
            
            try {
                siteId = Integer.parseInt(text);
                setVisible(false);
            }
            catch(final Throwable throwable) {
                JOptionPane.showMessageDialog(this, "Entrer un nombre valide", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == buttonCancel) {
            siteId = null;
            setVisible(false);
        }
    }
    
    public Integer getSiteId() {
        return siteId;
    }
}
