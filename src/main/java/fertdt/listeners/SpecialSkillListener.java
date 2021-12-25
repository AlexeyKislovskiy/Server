package fertdt.listeners;

import fertdt.RequestMessage;
import fertdt.entities.Character;
import fertdt.entities.Field;
import fertdt.entities.Game;
import fertdt.exceptions.ServerEventListenerException;
import fertdt.exceptions.ServerException;
import fertdt.helpers.EffectHelper;
import fertdt.helpers.GameHelper;
import fertdt.helpers.GameStateHelper;
import fertdt.helpers.MessageSender;

public class SpecialSkillListener extends AbstractServerEventListener {
    @Override
    public void handle(int connectionId, RequestMessage message) throws ServerEventListenerException, ServerException {
        if (!GameHelper.basicMoveCorrectCheck(this.init, connectionId, server)) return;
        Game game = server.getGames().get(GameStateHelper.gameIndexByConnectionId(connectionId, server));
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
        if (character == null || character.getSpecialSkill().getCost() > myPoints[1] || character.isMadeMove()
                || EffectHelper.blockSkillUseCheck(character) || game.getUsedSkills() >= game.getFirstTurns() + game.getSecondTurns()) {
            MessageSender.sendIncorrectRequestMessage(connectionId, server);
            return;
        }
        MessageSender.sendOKMessage(connectionId, server);
        game.setUsedSkills(game.getUsedSkills() + 1);
        character.setMadeMove(true);
        myPoints[1] -= character.getSpecialSkill().getCost();
        int[] myCharacters = message.getCharactersMy(), opponentsCharacters = message.getCharactersOpponent(),
                xMy = message.getXMy(), yMy = message.getYMy(),
                xOpponent = message.getXOpponent(), yOpponent = message.getYOpponent();

        EffectHelper.addEffectsToCharacters(false, game.getCurrentTurn(), myCharacters, opponentsCharacters, character, allMyCharacters, allOpponentsCharacters);
        EffectHelper.addEffectsToBlocks(false, game.getCurrentTurn(), xMy, yMy, xOpponent, yOpponent, character, myField, opponentsField);
        EffectHelper.handleInstantEffects(game);
        MessageSender.sendGameStateMessage(game, server);
        GameHelper.allCharactersMadeMoveCheck(allMyCharacters, game, server);
    }

    @Override
    public int getType() {
        return RequestMessage.SPECIAL_SKILL;
    }
}
