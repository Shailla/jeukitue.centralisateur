package jkt.centralisateur.applet;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jkt.centralisateur.applet.remoting.RemoteVpnService;
import jkt.centralisateur.remote.data.Site;

import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("serial")
public class VpnApplet extends JApplet implements ActionListener { 
    private RemoteVpnService remoteAppletService;

    private JButton buttonVpnInstance, buttonEquipment, buttonChiffreur;
    private JMenuItem menuItemNew, menuItemLoad, menuItemSave;
    private SiteGraphPanel siteGraphPanel;
    private JLabel labelId = new JLabel();
    
    @Override
    public void init() {
        super.init();
        
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-applet.xml");
        remoteAppletService = (RemoteVpnService) context.getBean("remoteVpnService");
        
        // Initialisation de la barre de menus
        JMenuBar menubar = createMenu(); 
        setJMenuBar(menubar);

        // Disposition de la fen�tre
        setSize(500, 500);
        
        Container mainPanel = getContentPane();
        
        GridBagLayout layout = new GridBagLayout();
        mainPanel.setLayout(layout);
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.weighty = 100;
        constraints.fill = GridBagConstraints.BOTH;
        
        // Initialisation de la toolbox
        JComponent toolbox = createToolbox();
        constraints.gridx = 0;
        constraints.weightx = 30;
        mainPanel.add(toolbox, constraints);
        
        // Initialisation du graphe
        siteGraphPanel = new SiteGraphPanel();
        siteGraphPanel.init(null);

        JScrollPane scrollPane = new JScrollPane(siteGraphPanel);
        constraints.gridx = 1;
        constraints.weightx = 70;
        mainPanel.add(scrollPane, constraints);
        
        // Param�tre de la fen�tre
        invalidate();
        
        
    }
    
    private JPanel createToolbox() {
        JPanel toolbox = new JPanel();
        int y = 0;
        
        GridBagLayout layout = new GridBagLayout();
        toolbox.setLayout(layout);
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.weighty = 10;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        
        // Label de l'ID
        constraints.gridy = y++;
        toolbox.add(labelId, constraints);
        
        // Instance VPN drag'n droppable
        buttonVpnInstance = new JButton("Instance VPN");
        buttonVpnInstance.addActionListener(this);
        constraints.gridy = y++;
        toolbox.add(buttonVpnInstance, constraints);

        // Equipement drag'n droppable
        buttonEquipment = new JButton("Equipement");
        buttonEquipment.addActionListener(this);
        constraints.gridy = y++;
        toolbox.add(buttonEquipment, constraints);  

        // Chiffreur drag'n droppable
        buttonChiffreur = new JButton("Chiffreur");
        buttonChiffreur.addActionListener(this);
        constraints.gridy = y++;
        toolbox.add(buttonChiffreur, constraints);   
        
        return toolbox;
    }
    
    /**
     * Cr�ation de la barre de menus
     * 
     * @return barre de menus
     */
    private JMenuBar createMenu() {
        JMenuBar menubar = new JMenuBar();
        
        // Menu fichiers
        JMenu menuFichiers = new JMenu("Fichiers");
        menubar.add(menuFichiers);
        
        // Nouveau site
        menuItemNew = new JMenuItem("Nouveau site");
        menuItemNew.addActionListener(this);
        menuFichiers.add(menuItemNew);
        
        // S�paration
        menuFichiers.addSeparator();

        // Lire un site
        menuItemLoad = new JMenuItem("Lire un site");
        menuItemLoad.addActionListener(this);
        menuFichiers.add(menuItemLoad);
        
        // Sauvegarder le site courrant
        menuItemSave = new JMenuItem("Sauver le site");
        menuItemSave.addActionListener(this);
        menuFichiers.add(menuItemSave);
        
        // Menu about
        JMenu menuHelp = new JMenu("Aide");
        menubar.add(menuHelp);
        menuHelp.add(new JMenuItem("A propos"));
        
        return menubar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        // Cr�ation d'un nouveau site
        if(source == menuItemNew) {
            Site site = remoteAppletService.createNewSite();
            siteGraphPanel.init(site);
            labelId.setText("Identifiant : " + site.getId());
        }
        
        // Lecture d'un site dont l'identifiant est demand� � l'utilsateur
        else if(source == menuItemLoad) {
            SiteIdDialog dialog = new SiteIdDialog(getWidth() / 2, getHeight() / 2);
            Integer siteId = dialog.getSiteId();
            labelId.setText("Identifiant : " + siteId);
            
            if(siteId != null) {
                Site site = remoteAppletService.loadSiteById(siteId);
                siteGraphPanel.init(site);
            }
        }
        
        // Sauvegarde du site
        else if(source == menuItemSave) {
            Site site = siteGraphPanel.getSite();
            remoteAppletService.saveSite(site);
        }
    }
}
