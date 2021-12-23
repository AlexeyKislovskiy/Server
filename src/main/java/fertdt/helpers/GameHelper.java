package fertdt.helpers;

import fertdt.ResponseMessage;
import fertdt.Server;
import fertdt.entities.Character;
import fertdt.entities.Field;
import fertdt.entities.Game;
import fertdt.exceptions.ServerException;

import java.util.List;

public class GameHelper {
    public static void gameOverCheck(Game game, Server server) throws ServerException {
        if (game.getFirstTurns() == Game.GAME_DURATION && game.getSecondTurns() == Game.GAME_DURATION) {
            game.setStatus(Game.FINISHED);
            ResponseMessage winMessage = ResponseMessage.createFinishMessage(ResponseMessage.YOU);
            ResponseMessage loseMessage = ResponseMessage.createFinishMessage(ResponseMessage.OPPONENT);
            ResponseMessage drawMessage = ResponseMessage.createFinishMessage(ResponseMessage.DRAW);
            if (game.getFirstPoints()[0] > game.getSecondPoints()[0]) {
                server.sendMessage(game.getFirstPlayer(), winMessage);
                server.sendMessage(game.getSecondPlayer(), loseMessage);
            } else if (game.getFirstPoints()[0] < game.getSecondPoints()[0]) {
                server.sendMessage(game.getFirstPlayer(), loseMessage);
                server.sendMessage(game.getSecondPlayer(), winMessage);
            } else {
                server.sendMessage(game.getFirstPlayer(), drawMessage);
                server.sendMessage(game.getSecondPlayer(), drawMessage);
            }
        }
    }

    public static void allCharactersMadeMoveCheck(Character[] allMyCharacters, Game game, Server server) throws ServerException {
        int num = 0;
        for (Character ch : allMyCharacters) {
            if (ch.isMadeMove()) num++;
        }
        if (num == Character.NUM_OF_CHARACTERS_FOR_EACH_PLAYER) {
            EffectHelper.recalculateSkills(game);
            ResponseMessage yourTurnMessage = ResponseMessage.createStartTurnMessage(ResponseMessage.YOU);
            ResponseMessage opponentsTurnMessage = ResponseMessage.createStartTurnMessage(ResponseMessage.OPPONENT);
            if (game.getCurrentTurn() == 2) {
                game.setCurrentTurn(1);
                game.setSecondTurns(game.getSecondTurns() + 1);
                gameOverCheck(game, server);
                for (Character el : game.getSecondCharacters()) {
                    el.setMadeMove(false);
                }
                server.sendMessage(game.getFirstPlayer(), yourTurnMessage);
                server.sendMessage(game.getSecondPlayer(), opponentsTurnMessage);
            } else {
                game.setCurrentTurn(2);
                game.setFirstTurns(game.getFirstTurns() + 1);
                gameOverCheck(game, server);
                for (Character el : game.getFirstCharacters()) {
                    el.setMadeMove(false);
                }
                server.sendMessage(game.getFirstPlayer(), opponentsTurnMessage);
                server.sendMessage(game.getSecondPlayer(), yourTurnMessage);
            }
            MessageSender.sendGameStateMessage(game, server);
        }
    }

    public static void doNormalMove(Character character, Field field, int[] x, int[] y, int[] points) {
        List<List<Integer>> blocks = field.getRawBlockState();
        for (List<Integer> block : blocks) {
            int num = block.get(0);
            for (int i = 0; i < x.length; i++) {
                if (field.getG()[x[i]][y[i]] == num) {
                    if (block.get(1) == 0) continue;
                    int damage = character.getBasicDamage();
                    block.set(1, block.get(1) - damage);
                    if (block.get(1) <= 0) {
                        block.set(1, 0);
                        while (block.size() > 2) {
                            block.remove(2);
                        }
                        int pointsForBlock = 0;
                        for (int j = 0; j < field.getX().length; j++) {
                            if (field.getX()[j][1] == num) pointsForBlock++;
                        }
                        points[0] += pointsForBlock;
                        points[1] += pointsForBlock;
                    }
                    break;
                }
            }
        }
        int broken = 0;
        for (List<Integer> block : blocks) {
            if (block.get(1) == 0) broken++;
        }
        if (broken == blocks.size()) field.regenerateField();
    }



}
