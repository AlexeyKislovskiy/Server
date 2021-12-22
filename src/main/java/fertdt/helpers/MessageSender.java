package fertdt.helpers;

import fertdt.ResponseMessage;
import fertdt.Server;
import fertdt.entities.Game;
import fertdt.exceptions.ServerException;

public class MessageSender {
    public static void sendIncorrectRequestMessage(int connectionId, Server server) throws ServerException {
        ResponseMessage responseMessage = ResponseMessage.createRequestStatusMessage(ResponseMessage.INCORRECT_REQUEST);
        server.sendMessage(connectionId, responseMessage);
    }

    public static void sendOKMessage(int connectionId, Server server) throws ServerException {
        ResponseMessage responseMessage = ResponseMessage.createRequestStatusMessage(ResponseMessage.OK);
        server.sendMessage(connectionId, responseMessage);
    }

    public static void sendGameStateMessage(Game game,Server server) throws ServerException {
        ResponseMessage responseMessage1 = ResponseMessage.createGameStateMessage(game.getFirstField().getBlockState(), game.getSecondField().getBlockState(),
                game.getFirstField().getX(), game.getFirstField().getY(), CharacterHelper.charactersArrayRepresentation(game.getFirstCharacters()),
                CharacterHelper.charactersArrayRepresentation(game.getSecondCharacters()), game.getFirstPoints(), game.getSecondPoints());
        ResponseMessage responseMessage2 = ResponseMessage.createGameStateMessage(game.getSecondField().getBlockState(), game.getFirstField().getBlockState(),
                game.getSecondField().getX(), game.getSecondField().getY(), CharacterHelper.charactersArrayRepresentation(game.getSecondCharacters()),
                CharacterHelper.charactersArrayRepresentation(game.getFirstCharacters()), game.getSecondPoints(), game.getFirstPoints());
        server.sendMessage(game.getFirstPlayer(), responseMessage1);
        server.sendMessage(game.getSecondPlayer(), responseMessage2);
    }
}
