package zelda.generator.view;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.PickableEdgePaintTransformer;
import edu.uci.ics.jung.visualization.decorators.PickableVertexPaintTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import zelda.generator.model.Game;
import zelda.generator.model.GameEnum;
import zelda.generator.model.ZeldaTree;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ZeldaGraphWindow {

    private Graph<String, Number> graph;

    private VisualizationViewer<String,Number> visualizer;

    public ZeldaGraphWindow(ZeldaTree tree) {
        List<ZeldaTree.Node> treeNodes = new ArrayList();
        if(tree.rootGame != null) {
            treeNodes.add(tree.rootGame);
            getChildren(treeNodes, tree.rootGame);
        }

        graph = new DirectedSparseGraph<>();
        String[] v = createVertices(treeNodes);
        createEdges(treeNodes);

        visualizer =  new VisualizationViewer<>(new FRLayout<>(graph));
        visualizer =  new VisualizationViewer<>(new FRLayout<>(graph));
        visualizer.getRenderContext().setVertexLabelTransformer(v1 -> v1);
        visualizer.getRenderContext().setVertexLabelRenderer(new DefaultVertexLabelRenderer(Color.cyan));
        visualizer.getRenderContext().setEdgeLabelRenderer(new DefaultEdgeLabelRenderer(Color.cyan));

        visualizer.getRenderContext().setVertexIconTransformer((String v12) -> new Icon() {

            public int getIconHeight() {
                return 20;
            }

            public int getIconWidth() {
                return 20;
            }

            public void paintIcon(Component c, Graphics g,
                                  int x, int y) {
                if(visualizer.getPickedVertexState().isPicked(v12)) {
                    g.setColor(Color.yellow);
                } else {
                    g.setColor(Color.red);
                }
                g.fillOval(x, y, 20, 20);
                if(visualizer.getPickedVertexState().isPicked(v12)) {
                    g.setColor(Color.black);
                } else {
                    g.setColor(Color.white);
                }
                g.drawString(""+ v12, x+8, y+15);

            }});

        visualizer.getRenderContext().setVertexFillPaintTransformer(new PickableVertexPaintTransformer<>(visualizer.getPickedVertexState(), Color.white,  Color.yellow));
        visualizer.getRenderContext().setEdgeDrawPaintTransformer(new PickableEdgePaintTransformer<>(visualizer.getPickedEdgeState(), Color.black, Color.lightGray));

        visualizer.setBackground(Color.white);

        visualizer.setVertexToolTipTransformer(new ToStringLabeller());

        final JFrame frame = new JFrame();
        Container content = frame.getContentPane();
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(visualizer);
        content.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final DefaultModalGraphMouse<String, Number> gm
                = new DefaultModalGraphMouse<String,Number>();
        visualizer.setGraphMouse(gm);

        final ScalingControl scaler = new CrossoverScalingControl();

        JButton plus = new JButton("+");
        plus.addActionListener(e -> scaler.scale(visualizer, 1.1f, visualizer.getCenter()));
        JButton minus = new JButton("-");
        minus.addActionListener(e -> scaler.scale(visualizer, 1/1.1f, visualizer.getCenter()));

        JPanel controls = new JPanel();
        controls.add(plus);
        controls.add(minus);
        controls.add(gm.getModeComboBox());
        content.add(controls, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public void getChildren(List<ZeldaTree.Node> nodeList, ZeldaTree.Node node) {
        for(Object child : node.children) {
            getChildren(nodeList, (ZeldaTree.Node)child);
            nodeList.add((ZeldaTree.Node)child);
        }
    }

    private String[] createVertices(List<ZeldaTree.Node> nodeList) {
        String[] v = new String[nodeList.size()];
        int i = 0;
        for (ZeldaTree.Node game : nodeList) {
            v[i] = ((GameEnum.getStringFromEnum(((Game)game.data).getGameTitle())));
            i++;
        }
        return v;
    }

    private void createEdges(List<ZeldaTree.Node> treeNodes) {
        for (ZeldaTree.Node game : treeNodes) {
            for (Object parent : game.parents) {
                graph.addEdge(Math.random(),
                        GameEnum.getStringFromEnum(((Game) ((ZeldaTree.Node) parent).data).getGameTitle()),
                        GameEnum.getStringFromEnum(((Game) game.data).getGameTitle()), EdgeType.DIRECTED);
            }
            for (Object child : game.children) {
                graph.addEdge(Math.random(),
                        GameEnum.getStringFromEnum(((Game) game.data).getGameTitle()),
                        GameEnum.getStringFromEnum(((Game) ((ZeldaTree.Node) child).data).getGameTitle()),
                        EdgeType.DIRECTED);
            }
        }
        // Will be replacing this graphing library shortly with Treant.js calls
        /*File file = new File("C:\\Users\\Kevin\\Desktop\\treant-js-master\\examples\\collapsable\\index.html");
        try {
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }*/
    }
}
