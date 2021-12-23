package fertdt.helpers;

import fertdt.Server;
import fertdt.entities.Game;

import java.util.List;

public class GameStateHelper {
    public static int gameIndexByRoomId(int roomId, Server server) {
        List<Game> games = server.getGames();
        for (Game el : games) {
            if (el.getRoomId() == roomId && el.getSecondPlayer() == null && el.getStatus() != Game.FINISHED)
                return games.indexOf(el);
        }
        return -1;
    }

    public static int gameIndexByConnectionId(int connectionId, Server server) {
        List<Game> games = server.getGames();
        for (Game el : games) {
            if ((el.getFirstPlayer() == connectionId || el.getSecondPlayer() != null &&
                    el.getSecondPlayer() == connectionId) && el.getStatus() != Game.FINISHED)
                return games.indexOf(el);
        }
        return -1;
    }

    public static boolean alreadyInGame(int connectionId, Server server) {
        int gameIndex = gameIndexByConnectionId(connectionId, server);
        if (gameIndex == -1) return false;
        Game game = server.getGames().get(gameIndex);
        return game.getStatus() != Game.FINISHED;
    }

    public static boolean isGameStatus(int connectionId, int gameStatus, Server server) {
        int gameIndex = gameIndexByConnectionId(connectionId, server);
        if (gameIndex == -1) return false;
        Game game = server.getGames().get(gameIndex);
        return game.getStatus() == gameStatus;
    }

}
