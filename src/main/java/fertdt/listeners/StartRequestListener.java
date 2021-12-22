package fertdt.listeners;

import fertdt.RequestMessage;
import fertdt.ResponseMessage;
import fertdt.entities.Game;
import fertdt.exceptions.ServerEventListenerException;
import fertdt.exceptions.ServerException;
import fertdt.helpers.GameStateHelper;
import fertdt.helpers.MessageSender;

public class StartRequestListener extends AbstractServerEventListener {

    @Override
    public void handle(int connectionId, RequestMessage message) throws ServerEventListenerException, ServerException {
        if (!this.init) {
            throw new ServerEventListenerException("Listener has not been initiated yet");
        }
        int roomId = message.getRoomId();
        int gameIndex = GameStateHelper.gameIndexByRoomId(roomId, server);
        if (GameStateHelper.alreadyInGame(connectionId, server)) {
            MessageSender.sendIncorrectRequestMessage(connectionId,server);
            return;
        }
        if (gameIndex == -1) {
            Game newGame = new Game(roomId, connectionId, Game.AWAIT);
            server.getGames().add(newGame);
            MessageSender.sendOKMessage(connectionId,server);
        } else {
            Game game = server.getGames().get(gameIndex);
            game.setSecondPlayer(connectionId);
            game.setStatus(Game.CHARACTERS_SELECTING);
            MessageSender.sendOKMessage(connectionId,server);
            ResponseMessage startMessage = ResponseMessage.createStartMessage();
            server.sendMessage(connectionId, startMessage);
            server.sendMessage(game.getFirstPlayer(), startMessage);
        }
    }

    @Override
    public int getType() {
        return RequestMessage.START_REQUEST;
    }

}
