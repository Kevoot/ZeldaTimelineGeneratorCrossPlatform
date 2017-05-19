package zelda.generator.model;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

import static org.simple.coollection.Coollection.*;

public class ZeldaTree<ZeldaTreeNode>
{
    public ZeldaTree.Node rootGame;
    private List<Node<Game>> leafGames;
    private List<Node<Game>> middleGames;


    public ZeldaTree(Game rootGame , List<Game> leafGames, List<Game> middleGames) {
        this.rootGame = new Node<Game>(rootGame);
        this.rootGame.data = rootGame;
        this.rootGame.children = new ArrayList<Node<Game>>();
        this.leafGames = new ArrayList<Node<Game>>();
        this.middleGames = new ArrayList<Node<Game>>();
        for(Game game : leafGames) {
            this.leafGames.add(new ZeldaTree.Node(game));
        }
        for(Game game : middleGames) {
            this.middleGames.add(new ZeldaTree.Node(game));
        }
        AssignRootChildren();
    }

    public class Node<Game> {
        public Game data;
        public String gameTitle;
        public List<Node<Game>> parents;
        public List<Node<Game>> children;
        public Boolean hasBeenVisited = false;

        public Node(Game game) {
            this.data = game;
            this.gameTitle = GameEnum.getStringFromEnum(((zelda.generator.model.Game)data).getGameTitle());
            this.parents = new ArrayList();
            this.children = new ArrayList();
        }
    }

    public void AssignRootChildren() {
        for(Connection connection : ((Game)rootGame.data).getConnections()) {
            if(connection.getTargetGameTitle() != ((Game) rootGame.data).getGameTitle()) {
                Node temp = from(middleGames)
                        .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getTargetGameTitle()))).first();
                if(temp == null) {
                    temp = from(leafGames)
                            .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getTargetGameTitle()))).first();
                }
                rootGame.children.add(temp);
            }
            else {
                Node temp = from(middleGames)
                        .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getSourceGameTitle()))).first();
                if(temp == null) {
                    temp = from(leafGames)
                            .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getSourceGameTitle()))).first();
                }
                rootGame.children.add(temp);
            }
        }
        for(Node node : middleGames) {
            for(Connection connection : ((Game) node.data).getConnections()) {
                if((connection.getSourceGameTitle() == ((Game)rootGame.data).getGameTitle() &&
                        (connection.getOrder() == ExclusionOrder.MustBeBefore ||
                        connection.getOrder() == ExclusionOrder.CantBeAfter)) ||
                        (connection.getTargetGameTitle() == ((Game)rootGame.data).getGameTitle() &&
                        (connection.getOrder() == ExclusionOrder.CantBeBefore ||
                        connection.getOrder() == ExclusionOrder.MustBeAfter))) {
                    rootGame.children.add(node);
                }
            }
        }
        for(Object childNode : rootGame.children) {
            ((Node)childNode).parents.add(rootGame);
        }
        // Once all other paths are complete, add in straggler leaf nodes
        for(Node node : leafGames) {
            for(Connection connection : ((Game) node.data).getConnections()) {
                if((connection.getSourceGameTitle() == ((Game)rootGame.data).getGameTitle() &&
                        (connection.getOrder() == ExclusionOrder.MustBeBefore ||
                                connection.getOrder() == ExclusionOrder.CantBeAfter)) ||
                        (connection.getTargetGameTitle() == ((Game)rootGame.data).getGameTitle() &&
                                (connection.getOrder() == ExclusionOrder.CantBeBefore ||
                                        connection.getOrder() == ExclusionOrder.MustBeAfter))) {
                    rootGame.children.add(node);
                    for(Object childNode : rootGame.children) {
                        ((Node<Game>)childNode).parents.add(rootGame);
                    }
                }
            }
        }
        AssignChildren();
    }

    public void AssignChildren() {
        for(Node node : this.middleGames) {
            for(Connection connection : ((Game)node.data).getConnections()) {
                if(connection.getSourceGameTitle() == ((Game) node.data).getGameTitle()) {
                    Node temp = from(middleGames)
                            .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getTargetGameTitle()))).first();
                    if(temp == null) {
                        temp = from(leafGames)
                                .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getTargetGameTitle()))).first();
                    }
                    if(temp != null) {
                        if(connection.getOrder() == ExclusionOrder.MustBeBefore || connection.getOrder() == ExclusionOrder.CantBeAfter) {
                            node.children.add(temp);
                            temp.parents.add(node);
                        }
                        else {
                            temp.children.add(node);
                            node.parents.add(temp);
                        }
                    }
                }
                else {
                    Node temp = from(middleGames)
                            .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getSourceGameTitle()))).first();
                    if(temp == null) {
                        temp = from(leafGames)
                                .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getSourceGameTitle()))).first();
                    }
                    if(temp != null) {
                        if(connection.getOrder() == ExclusionOrder.MustBeBefore || connection.getOrder() == ExclusionOrder.CantBeAfter) {
                            temp.children.add(node);
                            node.parents.add(temp);
                        }
                        else {
                            node.children.add(temp);
                            temp.parents.add(node);
                        }
                    }
                }
            }
        }
        for(Node node : this.leafGames) {
            for(Connection connection : ((Game)node.data).getConnections()) {
                if(connection.getSourceGameTitle() == ((Game) node.data).getGameTitle()) {
                    Node temp = from(middleGames)
                            .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getTargetGameTitle()))).first();
                    if(temp == null) {
                        temp = from(leafGames)
                                .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getTargetGameTitle()))).first();
                    }
                    if(temp != null) {
                        if(connection.getOrder() == ExclusionOrder.MustBeBefore || connection.getOrder() == ExclusionOrder.CantBeAfter) {
                            node.children.add(temp);
                            temp.parents.add(node);
                        }
                        else {
                            temp.children.add(node);
                            node.parents.add(temp);
                        }
                    }
                }
                else {
                    Node temp = from(middleGames)
                            .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getSourceGameTitle()))).first();
                    if(temp == null) {
                        temp = from(leafGames)
                                .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getSourceGameTitle()))).first();
                    }
                    if(temp != null) {
                        if(connection.getOrder() == ExclusionOrder.MustBeBefore || connection.getOrder() == ExclusionOrder.CantBeAfter) {
                            temp.children.add(node);
                            node.parents.add(temp);
                        }
                        else {
                            node.children.add(temp);
                            temp.parents.add(node);
                        }
                    }
                }
            }
        }
        /*if(targetNode.hasBeenVisited == false) {
            targetNode.hasBeenVisited = true;

            for(Connection connection : ((Game)targetNode.data).getConnections()) {
                if(connection.getTargetGameTitle() != ((Game) targetNode.data).getGameTitle()) {
                    Node temp = from(middleGames)
                            .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getTargetGameTitle()))).first();
                    if(temp == null) {
                        temp = from(leafGames)
                                .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getTargetGameTitle()))).first();
                    }
                    if(!targetNode.children.contains(temp)) {
                        targetNode.children.add(temp);
                    }
                }
                else {
                    Node temp = from(middleGames)
                            .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getSourceGameTitle()))).first();
                    if(temp == null) {
                        temp = from(leafGames)
                                .where("gameTitle", eq(GameEnum.getStringFromEnum(connection.getSourceGameTitle()))).first();
                    }
                    if(!targetNode.children.contains(temp)) {
                        targetNode.children.add(temp);
                    }
                }
            }
            for(Object childNode : targetNode.children) {
                ((Node)childNode).parents.add(targetNode);
                AssignChildren((Node)childNode);
            }
            for(Node node : middleGames) {
                for(Connection connection : ((Game) node.data).getConnections()) {
                    if((connection.getSourceGameTitle() == ((Game)targetNode.data).getGameTitle() &&
                            (connection.getOrder() == ExclusionOrder.MustBeBefore ||
                                    connection.getOrder() == ExclusionOrder.CantBeAfter)) ||
                            (connection.getTargetGameTitle() == ((Game)targetNode.data).getGameTitle() &&
                                    (connection.getOrder() == ExclusionOrder.CantBeBefore ||
                                            connection.getOrder() == ExclusionOrder.MustBeAfter))) {
                        targetNode.children.add(node);
                        for(Object childNode : targetNode.children) {
                            if(!targetNode.children.contains(node)) {
                                ((Node) childNode).parents.add(targetNode);
                                AssignChildren((Node) childNode);
                            }
                        }
                    }
                }
            }
            for(Node node : leafGames) {
                for(Connection connection : ((Game) node.data).getConnections()) {
                    if((connection.getSourceGameTitle() == ((Game)targetNode.data).getGameTitle() &&
                            (connection.getOrder() == ExclusionOrder.MustBeBefore ||
                                    connection.getOrder() == ExclusionOrder.CantBeAfter)) ||
                            (connection.getTargetGameTitle() == ((Game)targetNode.data).getGameTitle() &&
                                    (connection.getOrder() == ExclusionOrder.CantBeBefore ||
                                            connection.getOrder() == ExclusionOrder.MustBeAfter))) {
                        targetNode.children.add(node);
                        for(Object childNode : targetNode.children) {
                            if(!targetNode.children.contains(node)) {
                                ((Node) childNode).parents.add(targetNode);
                            }
                        }
                    }
                }
            }
        }
        else {
            return;
        }*/
    }

	public ZeldaTree(ObservableList<Game> games)
	{
		/*try
		{
			GenerateCollectionFromGames(games);
			GenerateGraphWindow();
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}*/
	}
}