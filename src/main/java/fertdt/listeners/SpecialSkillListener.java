package fertdt.listeners;

import fertdt.RequestMessage;
import fertdt.entities.Character;
import fertdt.entities.Field;
import fertdt.entities.Game;
import fertdt.exceptions.ServerEventListenerException;
import fertdt.exceptions.ServerException;
import fertdt.helpers.EffectHelper;
import fertdt.helpers.GameStateHelper;
import fertdt.helpers.MessageSender;

public class SpecialSkillListener extends AbstractServerEventListener {
    @Override
    public void handle(int connectionId, RequestMessage message) throws ServerEventListenerException, ServerException {
        if (!this.init) {
            throw new ServerEventListenerException("Listener has not been initiated yet");
        }
        if (!GameStateHelper.isGameStatus(connectionId, Game.IN_PROGRESS, server)) {
            MessageSender.sendIncorrectRequestMessage(connectionId, server);
            return;
        }
        Game game = server.getGames().get(GameStateHelper.gameIndexByConnectionId(connectionId, server));
        if (connectionId == game.getFirstPlayer() && game.getCurrentTurn() == 2 ||
                connectionId == game.getSecondPlayer() && game.getCurrentTurn() == 1) {
            MessageSender.sendIncorrectRequestMessage(connectionId, server);
            return;
        }
        int characterNumber = message.getCharacter();
        Character[] allMyCharacters, allOpponentsCharacters;
        Field myField, opponentsField;
        int[] myPoints;
        if (connectionId == game.getFirstPlayer()) {
            allMyCharacters = game.getFirstCharacters();
            allOpponentsCharacters = game.getSecondCharacters();
            myField = game.getFirstField();
            opponentsField = game.getSecondField();
            myPoints = game.getFirstPoints();
        } else {
            allMyCharacters = game.getSecondCharacters();
            allOpponentsCharacters = game.getFirstCharacters();
            myField = game.getSecondField();
            opponentsField = game.getFirstField();
            myPoints = game.getSecondPoints();
        }
        Character character = allMyCharacters[characterNumber];
        if (character == null || character.getSpecialSkill().getCost() > myPoints[1] || character.isMadeMove()) {
            MessageSender.sendIncorrectRequestMessage(connectionId, server);
            return;
        }
        MessageSender.sendOKMessage(connectionId, server);
        character.setMadeMove(true);
        myPoints[1] -= character.getSpecialSkill().getCost();
        int[] myCharacters = message.getCharactersMy(), opponentsCharacters = message.getCharactersOpponent(),
                xMy = message.getXMy(), yMy = message.getYMy(),
                xOpponent = message.getXOpponent(), yOpponent = message.getYOpponent();

        EffectHelper.addEffectsToCharacters(false, myCharacters, opponentsCharacters, character, allMyCharacters, allOpponentsCharacters);
        EffectHelper.addEffectsToBlocks(false, xMy, yMy, xOpponent, yOpponent, character, myField, opponentsField);
        MessageSender.sendGameStateMessage(game, server);
    }

    @Override
    public int getType() {
        return RequestMessage.SPECIAL_SKILL;
    }
}
