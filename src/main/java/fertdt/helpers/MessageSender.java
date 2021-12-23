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

    public static void sendGameStateMessage(Game game, Server server) throws ServerException {
        ResponseMessage responseMessage1 = ResponseMessage.createGameStateMessage(ArrayRepresentationHelper.blocksArrayRepresentation
                        (game.getFirstField().getBlocks()), ArrayRepresentationHelper.blocksArrayRepresentation(game.getSecondField().getBlocks()),
                game.getFirstField().getX(), game.getFirstField().getY(), ArrayRepresentationHelper.charactersArrayRepresentation(game.getFirstCharacters()),
                ArrayRepresentationHelper.charactersArrayRepresentation(game.getSecondCharacters()), game.getFirstPoints(), game.getSecondPoints());
        ResponseMessage responseMessage2 = ResponseMessage.createGameStateMessage(ArrayRepresentationHelper.blocksArrayRepresentation
                        (game.getSecondField().getBlocks()), ArrayRepresentationHelper.blocksArrayRepresentation(game.getFirstField().getBlocks()),
                game.getSecondField().getX(), game.getSecondField().getY(), ArrayRepresentationHelper.charactersArrayRepresentation(game.getSecondCharacters()),
                ArrayRepresentationHelper.charactersArrayRepresentation(game.getFirstCharacters()), game.getSecondPoints(), game.getFirstPoints());
        server.sendMessage(game.getFirstPlayer(), responseMessage1);
        server.sendMessage(game.getSecondPlayer(), responseMessage2);
    }
}
