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

public class NormalSkillListener extends AbstractServerEventListener {
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
        if (connectionId == game.getFirstPlayer()) {
            allMyCharacters = game.getFirstCharacters();
            allOpponentsCharacters = game.getSecondCharacters();
            myField = game.getFirstField();
            opponentsField = game.getSecondField();
        } else {
            allMyCharacters = game.getSecondCharacters();
            allOpponentsCharacters = game.getFirstCharacters();
            myField = game.getSecondField();
            opponentsField = game.getFirstField();
        }
        Character character = allMyCharacters[characterNumber];
        if (character == null || character.getNormalSkill().getCurrentCooldown() != 0 || character.isMadeMove()) {
            MessageSender.sendIncorrectRequestMessage(connectionId, server);
            return;
        }
        MessageSender.sendOKMessage(connectionId, server);
        character.setMadeMove(true);
        character.getNormalSkill().updateCooldown();
        int[] myCharacters = message.getCharactersMy(), opponentsCharacters = message.getCharactersOpponent(),
                xMy = message.getXMy(), yMy = message.getYMy(),
                xOpponent = message.getXOpponent(), yOpponent = message.getYOpponent();

        EffectHelper.addEffectsToCharacters(true, myCharacters, opponentsCharacters, character, allMyCharacters, allOpponentsCharacters);
        EffectHelper.addEffectsToBlocks(true, xMy, yMy, xOpponent, yOpponent, character, myField, opponentsField);
        MessageSender.sendGameStateMessage(game, server);
    }

    @Override
    public int getType() {
        return RequestMessage.NORMAL_SKILL;
    }
}
