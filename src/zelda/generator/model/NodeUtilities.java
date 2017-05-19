package zelda.generator.model;

import java.util.ArrayList;
import java.util.List;

public class NodeUtilities {
    public static List<Game> FindLeafGames(List<Game> gameList) {
        List<Game> initialList = new ArrayList<>(gameList);
        for(Game outerGame : gameList) {
            for(Game innerGame : gameList) {
                for(Connection connection : innerGame.getConnections()) {
                    if((connection.getSourceGameTitle() == outerGame.getGameTitle() &&
                            (connection.getOrder() == ExclusionOrder.CantBeAfter ||
                             connection.getOrder() == ExclusionOrder.MustBeBefore)) ||
                            connection.getTargetGameTitle() == outerGame.getGameTitle() &&
                                    (connection.getOrder() == ExclusionOrder.CantBeBefore ||
                                    connection.getOrder() == ExclusionOrder.MustBeAfter)) {
                        initialList.remove(outerGame);
                    }
                }
            }
        }
        return initialList;
    }
}
