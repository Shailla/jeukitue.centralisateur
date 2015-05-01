package jkt.centralisateur.applet;

import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;

@SuppressWarnings("serial")
public class JktApplet extends JApplet implements ImageObserver {
    
    @Override
    public void init() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Menu Fichiers
        JMenu menuFichiers = new JMenu("Fichiers");
        menuBar.add(menuFichiers);
        menuFichiers.add(new JMenuItem("Ouvrir"));
        
        JGraph graphe = new JGraph();
        JScrollPane scrollpane = new JScrollPane(graphe);
        
        JButton button = new JButton("Bouton");
        add(button);
        button.setIcon(createImageIcon("images/Calimero-5.gif"));
        
        add(scrollpane);
        
        setSize(500, 500);
        
        this.invalidate();
    }
    
    private ImageIcon createImageIcon(final String path) {
        Image image = getImage(getCodeBase(), path);

        if (image != null) {
            return new ImageIcon(image);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}
