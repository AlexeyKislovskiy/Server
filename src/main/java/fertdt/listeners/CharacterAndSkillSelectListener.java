package fertdt.listeners;

import com.google.gson.Gson;
import fertdt.RequestMessage;
import fertdt.ResponseMessage;
import fertdt.entities.Character;
import fertdt.entities.Field;
import fertdt.entities.Game;
import fertdt.entities.PassiveSkill;
import fertdt.exceptions.ServerEventListenerException;
import fertdt.exceptions.ServerException;
import fertdt.helpers.GameStateHelper;
import fertdt.helpers.MessageSender;

import java.util.ArrayList;
import java.util.List;

public class CharacterAndSkillSelectListener extends AbstractServerEventListener {

    @Override
    public void handle(int connectionId, RequestMessage message) throws ServerEventListenerException, ServerException {
        if (!this.init) {
            throw new ServerEventListenerException("Listener has not been initiated yet");
        }
        int[] characters = message.getCharactersMy(), skills = message.getSkills();
        int gameIndex = GameStateHelper.gameIndexByConnectionId(connectionId, server);
        if (!GameStateHelper.isGameStatus(connectionId, Game.CHARACTERS_SELECTING, server) || characters.length != 3 || skills.length != 3) {
            MessageSender.sendIncorrectRequestMessage(connectionId, server);
            return;
        }
        Game game = server.getGames().get(gameIndex);
        Character[] usedCharacters;
        if (game.getFirstPlayer() == connectionId) usedCharacters = game.getFirstCharacters();
        else usedCharacters = game.getSecondCharacters();
        if (usedCharacters != null) {
            MessageSender.sendIncorrectRequestMessage(connectionId, server);
            return;
        }
        List<Character> setCharacters = new ArrayList<>();
        List<PassiveSkill> setSkills = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (characters[i] > Character.NUM_OF_CHARACTERS || skills[i] > PassiveSkill.NUM_OF_PASSIVE_SKILLS ||
                    setCharacters.contains(new Character(characters[i])) || setSkills.contains(new PassiveSkill(skills[i]))) {
                MessageSender.sendIncorrectRequestMessage(connectionId, server);
                return;
            }
            setCharacters.add(new Character(characters[i]));
            setSkills.add(new PassiveSkill(skills[i]));
        }
        if (game.getFirstPlayer() == connectionId) {
            game.setFirstCharacters(setCharacters.toArray(new Character[0]));
            game.setFirstSkills(setSkills.toArray(new PassiveSkill[0]));
        } else {
            game.setSecondCharacters(setCharacters.toArray(new Character[0]));
            game.setSecondSkills(setSkills.toArray(new PassiveSkill[0]));
        }
        MessageSender.sendOKMessage(connectionId, server);
        if (game.getFirstCharacters() != null && game.getSecondCharacters() != null) {
            int currentTurn = (int) (Math.random() * 2 + 1);
            game.setCurrentTurn(currentTurn);
            Field field = Field.generateField(10, 10);
            game.setFirstField(field);
            Gson gson = new Gson();
            game.setSecondField(gson.fromJson(gson.toJson(field), Field.class));
            MessageSender.sendGameStateMessage(game, server);
            game.setStatus(Game.IN_PROGRESS);
            ResponseMessage yourTurnMessage = ResponseMessage.createStartTurnMessage(ResponseMessage.YOU);
            ResponseMessage opponentsTurnMessage = ResponseMessage.createStartTurnMessage(ResponseMessage.OPPONENT);
            if (currentTurn == 1) {
                server.sendMessage(game.getFirstPlayer(), yourTurnMessage);
                server.sendMessage(game.getSecondPlayer(), opponentsTurnMessage);
            } else {
                server.sendMessage(game.getFirstPlayer(), opponentsTurnMessage);
                server.sendMessage(game.getSecondPlayer(), yourTurnMessage);
            }
        }
    }

    @Override
    public int getType() {
        return RequestMessage.CHARACTERS_AND_SKILLS_SELECT;
    }
}
