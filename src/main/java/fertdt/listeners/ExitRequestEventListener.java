package fertdt.listeners;

import fertdt.RequestMessage;
import fertdt.ResponseMessage;
import fertdt.entities.Game;
import fertdt.exceptions.ServerEventListenerException;
import fertdt.exceptions.ServerException;
import fertdt.helpers.GameStateHelper;
import fertdt.helpers.MessageSender;

public class ExitRequestEventListener extends AbstractServerEventListener {
    @Override
    public void handle(int connectionId, RequestMessage message) throws ServerEventListenerException, ServerException {
        if (!this.init) {
            throw new ServerEventListenerException("Listener has not been initiated yet");
        }
        if (!(GameStateHelper.isGameStatus(connectionId, Game.CHARACTERS_SELECTING, server) ||
                GameStateHelper.isGameStatus(connectionId, Game.IN_PROGRESS, server))) {
            MessageSender.sendIncorrectRequestMessage(connectionId, server);
            return;
        }
        Game game = server.getGames().get(GameStateHelper.gameIndexByConnectionId(connectionId, server));
        MessageSender.sendOKMessage(connectionId, server);
        game.setStatus(Game.FINISHED);
        ResponseMessage winMessage = ResponseMessage.createFinishMessage(ResponseMessage.YOU);
        ResponseMessage loseMessage = ResponseMessage.createFinishMessage(ResponseMessage.OPPONENT);
        server.sendMessage(connectionId, loseMessage);
        if (connectionId == game.getFirstPlayer()) {
            server.sendMessage(game.getSecondPlayer(), winMessage);
        } else server.sendMessage(game.getFirstPlayer(), winMessage);
    }

    @Override
    public int getType() {
        return RequestMessage.EXIT_REQUEST;
    }
}
