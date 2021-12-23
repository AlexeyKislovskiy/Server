package fertdt.listeners;

import fertdt.RequestMessage;
import fertdt.entities.Game;
import fertdt.exceptions.ServerEventListenerException;
import fertdt.exceptions.ServerException;
import fertdt.helpers.GameStateHelper;
import fertdt.helpers.MessageSender;

public class StopWaitingStartRequest extends AbstractServerEventListener {
    @Override
    public void handle(int connectionId, RequestMessage message) throws ServerEventListenerException, ServerException {
        if (!this.init) {
            throw new ServerEventListenerException("Listener has not been initiated yet");
        }
        if (!GameStateHelper.isGameStatus(connectionId, Game.AWAIT, server)) {
            MessageSender.sendIncorrectRequestMessage(connectionId, server);
            return;
        }
        Game game = server.getGames().get(GameStateHelper.gameIndexByConnectionId(connectionId, server));
        game.setStatus(Game.FINISHED);
        MessageSender.sendOKMessage(connectionId, server);
    }

    @Override
    public int getType() {
        return RequestMessage.STOP_WAITING_START_REQUEST;
    }
}
