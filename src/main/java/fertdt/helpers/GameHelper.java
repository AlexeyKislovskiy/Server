package fertdt.helpers;

import fertdt.ResponseMessage;
import fertdt.Server;
import fertdt.entities.Block;
import fertdt.entities.Character;
import fertdt.entities.Field;
import fertdt.entities.Game;
import fertdt.exceptions.ServerEventListenerException;
import fertdt.exceptions.ServerException;

public class GameHelper {
    public static boolean basicMoveCorrectCheck(boolean init, int connectionId, Server server) throws ServerEventListenerException, ServerException {
        if (!init) {
            throw new ServerEventListenerException("Listener has not been initiated yet");
        }
        if (!GameStateHelper.isGameStatus(connectionId, Game.IN_PROGRESS, server)) {
            MessageSender.sendIncorrectRequestMessage(connectionId, server);
            return false;
        }
        Game game = server.getGames().get(GameStateHelper.gameIndexByConnectionId(connectionId, server));
        if (connectionId == game.getFirstPlayer() && game.getCurrentTurn() == 2 ||
                connectionId == game.getSecondPlayer() && game.getCurrentTurn() == 1) {
            MessageSender.sendIncorrectRequestMessage(connectionId, server);
            return false;
        }
        return true;
    }

    public static boolean gameOverCheck(Game game, Server server) throws ServerException {
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
            return true;
        }
        return false;
    }

    public static void allCharactersMadeMoveCheck(Character[] allMyCharacters, Game game, Server server) throws ServerException {
        EffectHelper.blockMoveCheck(allMyCharacters);
        int num = 0;
        for (Character ch : allMyCharacters) {
            if (ch.isMadeMove()) num++;
        }
        if (num == Character.NUM_OF_CHARACTERS_FOR_EACH_PLAYER) {
            game.setUsedSkills(0);
            EffectHelper.recalculateEffects(game);
            recalculateCooldowns(allMyCharacters);
            ResponseMessage yourTurnMessage = ResponseMessage.createStartTurnMessage(ResponseMessage.YOU);
            ResponseMessage opponentsTurnMessage = ResponseMessage.createStartTurnMessage(ResponseMessage.OPPONENT);
            if (game.getCurrentTurn() == 2) {
                game.setCurrentTurn(1);
                game.setSecondTurns(game.getSecondTurns() + 1);
                if (gameOverCheck(game, server)) return;
                for (Character el : game.getSecondCharacters()) {
                    el.setMadeMove(false);
                }
                server.sendMessage(game.getFirstPlayer(), yourTurnMessage);
                server.sendMessage(game.getSecondPlayer(), opponentsTurnMessage);
            } else {
                game.setCurrentTurn(2);
                game.setFirstTurns(game.getFirstTurns() + 1);
                if (gameOverCheck(game, server)) return;
                for (Character el : game.getFirstCharacters()) {
                    el.setMadeMove(false);
                }
                server.sendMessage(game.getFirstPlayer(), opponentsTurnMessage);
                server.sendMessage(game.getSecondPlayer(), yourTurnMessage);
            }
            MessageSender.sendGameStateMessage(game, server);
            Character[] allOpponentsCharacters;
            if (game.getFirstCharacters() == allMyCharacters) allOpponentsCharacters = game.getSecondCharacters();
            else allOpponentsCharacters = game.getFirstCharacters();
            EffectHelper.blockMoveCheck(allOpponentsCharacters);
            allCharactersMadeMoveCheck(allOpponentsCharacters, game, server);
        }
    }

    private static void recalculateCooldowns(Character[] characters) {
        for (Character ch : characters) {
            int cd = ch.getNormalSkill().getCurrentCooldown();
            if (cd != 0) ch.getNormalSkill().setCurrentCooldown(cd - 1);
        }
    }

    public static void doNormalMove(Character character, Field field, int[] x, int[] y, int[] points) {
        Block[] blocks = field.getBlocks();
        for (Block block : blocks) {
            int num = block.getId();
            for (int i = 0; i < x.length; i++) {
                if (field.getG()[x[i]][y[i]] == num) {
                    if (block.getHp() == 0) continue;
                    int damage = EffectHelper.damageWithEffects(character.getBasicDamage(), character, block, field);
                    block.setHp(block.getHp() - damage);
                    if (block.getHp() <= 0) {
                        block.setHp(0);
                        while (block.getEffects().size() > 0) {
                            block.getEffects().remove(0);
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
        for (Block block : blocks) {
            if (block.getHp() == 0) broken++;
        }
        if (broken == blocks.length) field.regenerateField();
    }
}
