package jkt.centralisateur.applet;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.undo.UndoableEdit;

import org.jgraph.JGraph;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.Edge;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.ParentMap;
import org.jgraph.graph.Port;

public class Essai extends JFrame {
    JGraph myGraph;
    GraphModel myModel;
    Map myAttribute;

    Essai() {
        myModel = new DefaultGraphModel();
        myGraph = new JGraph(myModel);
        myAttribute = new Hashtable();
    }

    DefaultGraphCell setVertix(String name, Rectangle bound, Color bgColor,
            boolean isOpaque, Border border, Color borderColor) {
        DefaultGraphCell myCell = new DefaultGraphCell(name);
//        Map cellAttribute = GraphConstants.createMap();
        Map cellAttribute = new HashMap();
        myAttribute.put(myCell, cellAttribute);
        GraphConstants.setBounds(cellAttribute, bound);
        if (bgColor != null) {
            GraphConstants.setBackground(cellAttribute, bgColor);
        }
        if (isOpaque) {
            GraphConstants.setOpaque(cellAttribute, isOpaque);
        }
        if (border != null) {
            GraphConstants.setBorder(cellAttribute, border);
        }
        if (borderColor != null) {
            GraphConstants.setBorderColor(cellAttribute, borderColor);
        }

        return myCell;
    }

    DefaultEdge setEdge(String name, int lineEnd, boolean isEndFill) {
        DefaultEdge myEdge = new DefaultEdge(name);
//        Map edgeAttribute = GraphConstants.createMap();
        Map edgeAttribute = new HashMap();
        myAttribute.put(myEdge, edgeAttribute);
        GraphConstants.setLineEnd(edgeAttribute, lineEnd);
        GraphConstants.setEndFill(edgeAttribute, isEndFill);
        return myEdge;
    }

    void insertModel(Edge edge, Port source, Port target, Object srcCell,
                     Object tgtCell, ParentMap pm, UndoableEdit[] e) {
        ConnectionSet cs = new ConnectionSet(edge, source, target);
        Object[] cells = new Object[] { edge, srcCell, tgtCell };
        myModel.insert(cells, myAttribute, cs, pm, e);
    }

    void showFrame() {
        this.getContentPane().add(new JScrollPane(myGraph));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] arg) {
        Essai graph = new Essai();
        
        // First vertix
        DefaultGraphCell cell1 = graph.setVertix("First Vertix", new Rectangle(
                20, 20, 40, 20), null, false, null, Color.black);
        DefaultPort port1 = new DefaultPort();
        cell1.add(port1);
        
        // Second vertix
        DefaultGraphCell cell2 = graph.setVertix("Second Vertix",
                new Rectangle(140, 140, 40, 20), Color.orange, true,
                BorderFactory.createRaisedBevelBorder(), null);
        DefaultPort port2 = new DefaultPort();
        cell2.add(port2);
        
        // Edge
        DefaultEdge edge1 = graph.setEdge("My Edge",
                GraphConstants.ARROW_CLASSIC, true);
        graph.insertModel(edge1, port1, port2, cell1, cell2, null, null);
        graph.showFrame();
    }
}
