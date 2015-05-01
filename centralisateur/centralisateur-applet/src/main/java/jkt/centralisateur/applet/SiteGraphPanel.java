package jkt.centralisateur.applet;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkt.centralisateur.remote.data.Chiffreur;
import jkt.centralisateur.remote.data.Site;
import jkt.centralisateur.remote.data.VpnInstance;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.ConnectionSet.Connection;

@SuppressWarnings("serial")
public class SiteGraphPanel extends JGraph {
    private GraphModel graphModel;
    private Map<Object, Object> graphAttributes = new HashMap<Object, Object>();
    
    private DefaultGraphCell siteCell;
    private Set<DefaultGraphCell> instanceCells;
    private Map<DefaultGraphCell, Set<DefaultGraphCell>> chiffreurCells;

    private DefaultGraphCell createSiteCell(final Site site, final List<Object> cellules, Map<Object, Object> attributes) {
        siteCell = new DefaultGraphCell(site);
        cellules.add(siteCell);

        // Attributs de la cellule du site
        Map<Object, Object> siteCellAttributes = new HashMap<Object, Object>();
        attributes.put(siteCell, siteCellAttributes);

        int x = site.getPosition().x;
        int y = site.getPosition().y;
        Rectangle2D rect = new Rectangle(x, y, 100, 40);
        GraphConstants.setBounds(siteCellAttributes, rect);    // Dimensions
        GraphConstants.setOpaque(siteCellAttributes, true);                             // Opaque
        GraphConstants.setBackground(siteCellAttributes, Color.blue);                   // Couleur du fond
        GraphConstants.setBorderColor(siteCellAttributes, Color.black);                 // Couleur des bords
        
        return siteCell;
    }

    private DefaultGraphCell createInstanceCell(final VpnInstance instance, final List<Object> cellules, Map<Object, Object> attributes) {
        DefaultGraphCell instanceCell = new DefaultGraphCell(instance);
        cellules.add(instanceCell);

        // Attributs de la cellule du site
        Map<Object, Object> instanceCellAttributes = new HashMap<Object, Object>();
        attributes.put(instanceCell, instanceCellAttributes);

        int x = instance.getPosition().x;
        int y = instance.getPosition().y;
        Rectangle2D rect = new Rectangle(x, y, 100, 40);
        GraphConstants.setBounds(instanceCellAttributes, rect);    // Dimensions
        GraphConstants.setOpaque(instanceCellAttributes, true);                             // Opaque
        GraphConstants.setBackground(instanceCellAttributes, Color.GREEN);                  // Couleur du fond
        GraphConstants.setBorderColor(instanceCellAttributes, Color.black);                 // Couleur des bords

        return instanceCell;
    }

    static private DefaultGraphCell createChiffreurCell(final Chiffreur chiffreur, final List<Object> cellules, Map<Object, Object> attributes) {
        DefaultGraphCell instanceCell = new DefaultGraphCell(chiffreur);
        cellules.add(instanceCell);

        // Attributs de la cellule du site
        Map<Object, Object> instanceCellAttributes = new HashMap<Object, Object>();
        attributes.put(instanceCell, instanceCellAttributes);

        int x = chiffreur.getPosition().x;
        int y = chiffreur.getPosition().y;
        Rectangle2D rect = new Rectangle(x, y, 100, 40);
        GraphConstants.setBounds(instanceCellAttributes, rect);    // Dimensions
        GraphConstants.setOpaque(instanceCellAttributes, true);                             // Opaque
        GraphConstants.setBackground(instanceCellAttributes, Color.PINK);                  // Couleur du fond
        GraphConstants.setBorderColor(instanceCellAttributes, Color.black);                 // Couleur des bords

        
        return instanceCell;
    }

    private static DefaultEdge createInstanceEdge(final List<Object> objects, Map<Object, Object> attributes) {
        DefaultEdge edge = new DefaultEdge("instance");
        Map<Object, Object> edgeAttribute = new HashMap<Object, Object>();
        attributes.put(edge, edgeAttribute);
        GraphConstants.setLineEnd(edgeAttribute, GraphConstants.ARROW_CLASSIC);
        GraphConstants.setEndFill(edgeAttribute, true);
        objects.add(edge);

        return edge;
    }

    private static DefaultEdge createChiffreurEdge(final List<Object> objects, Map<Object, Object> attributes) {
        DefaultEdge edge = new DefaultEdge("chiffreur");
        Map<Object, Object> edgeAttribute = new HashMap<Object, Object>();
        attributes.put(edge, edgeAttribute);
        GraphConstants.setLineEnd(edgeAttribute, GraphConstants.ARROW_SIMPLE);
        GraphConstants.setEndFill(edgeAttribute, true);
        objects.add(edge);

        return edge;
    }    
    
    /**
     * Initialise un graphe avec un site.
     * 
     * @param site site d'initialisation du graphe
     */
    public void init(final Site site) {
        // R�initialisation des �l�ments d'affichage
        graphModel = new DefaultGraphModel();
        setModel(graphModel);
        graphAttributes.clear();
        
        siteCell = null;
        instanceCells = new HashSet<DefaultGraphCell>();
        chiffreurCells = new HashMap<DefaultGraphCell, Set<DefaultGraphCell>>();
        
        List<Object> objects = new ArrayList<Object>(); 
        Set<DefaultEdge> edges = new HashSet<DefaultEdge>();

        if(site != null) {            
            /* **********************************
             *  Cellule du site
             * *********************************/

            siteCell = createSiteCell(site, objects, graphAttributes);
            Set<Object> siteConnections = new HashSet<Object>();
            Object sitePort = siteCell.addPort();

            
            /* **********************************
             *  Instances VPN du site
             * *********************************/

            for(final VpnInstance instance : site.getVpnInstances()) {
                DefaultGraphCell instanceCell = createInstanceCell(instance, objects, graphAttributes);
                
                instanceCells.add(instanceCell);
                
                // Fl�che Site -> Instance VPN
                DefaultEdge instanceEdge = createInstanceEdge(objects, graphAttributes);

                Object instancePort = instanceCell.addPort();
                siteConnections.add(new Connection(instanceEdge, instancePort, false));
                siteConnections.add(new Connection(instanceEdge, sitePort, true));

                for(final Chiffreur chiffreur : instance.getChiffreurs()) {
                    DefaultGraphCell chiffreurCell = createChiffreurCell(chiffreur, objects, graphAttributes);
                    
                    Set<DefaultGraphCell> chiffreursFromInstance = chiffreurCells.get(instanceCell);
                    if(chiffreursFromInstance == null) {
                        chiffreurCells.put(instanceCell, new HashSet<DefaultGraphCell>());
                    }
                    
                    chiffreurCells.get(instanceCell).add(chiffreurCell);    
                    
                    
                    // Fl�che Site -> Instance VPN
                    DefaultEdge chiffreurEdge = createChiffreurEdge(objects, graphAttributes);

                    Object chiffreurPort = chiffreurCell.addPort();
                    siteConnections.add(new Connection(chiffreurEdge, chiffreurPort, false));
                    siteConnections.add(new Connection(chiffreurEdge, instancePort, true));
                }
            }

            // Agr�gation des objets JGraph
            Object[] objectArray = objects.toArray();
            ConnectionSet connectionSet = new ConnectionSet(siteConnections);
            connectionSet.setEdges(edges);

            // Insertion des cellules dans le graphe
            graphModel.insert(objectArray, graphAttributes, connectionSet, null, null);
        }
    }

    public Site getSite() {
        Site site = null;
        
        if(siteCell != null) {
            // Mise � jour de la position du site sur le graphe
            site = (Site) siteCell.getUserObject();
            site.setPosition(getPoint(siteCell.getAttributes()));
            
            
            // Mise � jour des positions des instances du site
            for(final DefaultGraphCell instanceCell : instanceCells) {
                VpnInstance vpnInstance = (VpnInstance) instanceCell.getUserObject();
                vpnInstance.setPosition(getPoint(instanceCell.getAttributes()));
                
                // Mise � jour des positions des chiffreurs des instances
                Set<DefaultGraphCell> chiffreursCells = chiffreurCells.get(instanceCell);
                
                if(chiffreursCells != null) {
                    for(final DefaultGraphCell chiffreurCell : chiffreursCells) {
                        Chiffreur chiffreur = (Chiffreur) chiffreurCell.getUserObject();
                        chiffreur.setPosition(getPoint(chiffreurCell.getAttributes()));
                    }
                }
            }
        }
        
        return site;
    }
    
    private Point getPoint(final AttributeMap cellAttributes) {
        Rectangle2D rect = GraphConstants.getBounds(cellAttributes);
        Point position = new Point((int) rect.getX()+10, (int) rect.getY());
        
        return position;
    }
}
