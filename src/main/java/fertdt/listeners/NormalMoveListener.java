package fertdt.listeners;

import fertdt.RequestMessage;
import fertdt.entities.Character;
import fertdt.entities.Field;
import fertdt.entities.Game;
import fertdt.exceptions.ServerEventListenerException;
import fertdt.exceptions.ServerException;
import fertdt.helpers.GameHelper;
import fertdt.helpers.GameStateHelper;
import fertdt.helpers.MessageSender;

public class NormalMoveListener extends AbstractServerEventListener {
    @Override
    public void handle(int connectionId, RequestMessage message) throws ServerEventListenerException, ServerException {
        if (!GameHelper.basicMoveCorrectCheck(this.init, connectionId, server)) return;
        Game game = server.getGames().get(GameStateHelper.gameIndexByConnectionId(connectionId, server));
        int characterNumber = message.getCharacter();
        Character[] allMyCharacters;
        Field field;
        int[] points;
        if (connectionId == game.getFirstPlayer()) {
            allMyCharacters = game.getFirstCharacters();
            field = game.getFirstField();
            points = game.getFirstPoints();
        } else {
            allMyCharacters = game.getSecondCharacters();
            field = game.getSecondField();
            points = game.getSecondPoints();
        }
        Character character = allMyCharacters[characterNumber];
        if (character == null || character.isMadeMove()) {
            MessageSender.sendIncorrectRequestMessage(connectionId, server);
            return;
        }
        MessageSender.sendOKMessage(connectionId, server);
        character.setMadeMove(true);
        int[] x = message.getXMy(), y = message.getYMy();
        GameHelper.doNormalMove(character, field, x, y, points);
        MessageSender.sendGameStateMessage(game, server);
        GameHelper.allCharactersMadeMoveCheck(allMyCharacters, game, server);
    }

    @Override
    public int getType() {
        return RequestMessage.NORMAL_MOVE;
    }
}
