package fertdt;

import fertdt.entities.Game;
import fertdt.exceptions.ServerException;
import fertdt.listeners.ServerEventListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public interface Server {
    void registerListener(ServerEventListener listener) throws ServerException;

    void sendMessage(int connectionId, ResponseMessage message) throws ServerException;

    void start() throws ServerException;

    List<ServerEventListener> getListeners();

    int getPort();

    ServerSocket getServer();

    boolean isStarted();

    List<Socket> getSockets();

    List<Game> getGames();
}
