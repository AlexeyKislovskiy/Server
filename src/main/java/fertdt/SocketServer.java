package fertdt;

import fertdt.entities.Game;
import fertdt.exceptions.ServerEventListenerException;
import fertdt.exceptions.ServerException;
import fertdt.helpers.GameStateHelper;
import fertdt.listeners.ServerEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer implements Server {
    protected List<ServerEventListener> listeners;
    protected int port;
    protected ServerSocket server;
    protected boolean started;
    protected List<Socket> sockets;
    protected List<Game> games;

    public SocketServer(int port) {
        this.listeners = new ArrayList<>();
        this.port = port;
        this.sockets = new ArrayList<>();
        this.started = false;
        this.games = new ArrayList<>();
    }

    @Override
    public void registerListener(ServerEventListener listener) throws ServerException {
        if (started) {
            throw new ServerException("Server has been started already");
        }
        listener.init(this);
        this.listeners.add(listener);
    }

    @Override
    public void start() throws ServerException {
        try {
            server = new ServerSocket(this.port);
            started = true;

            while (true) {
                Socket s = server.accept();
                handleConnection(s);
            }
        } catch (IOException ex) {
            throw new ServerException("Problem with server starting", ex);
        }
    }

    protected void handleConnection(Socket socket) {
        sockets.add(socket);
        int connectionId = sockets.lastIndexOf(socket);

        Runnable runnable = () -> {
            try {
                InputStream is = socket.getInputStream();
                List<Byte> list = new ArrayList<>();
                while (true) {
                    int n = is.read();
                    if (n != -1) {
                        list.add((byte) n);
                        try {
                            RequestMessage requestMessage = RequestMessage.readMessage(list);
                            System.out.println(requestMessage);
                            for (ServerEventListener listener : listeners) {
                                if (requestMessage.getMessageType() == listener.getType()) {
                                    listener.handle(connectionId, requestMessage);
                                }
                            }
                            list.clear();
                        } catch (MessageReadingException ignored) {
                        }
                    }
                }
            } catch (IOException | ServerEventListenerException | ServerException e) {
                handleDisconnection(connectionId);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    protected void handleDisconnection(int connectionId) {
        int gameId = GameStateHelper.gameIndexByConnectionId(connectionId, this);
        if (gameId != -1) {
            Game game = games.get(gameId);
            if (game.getStatus() != Game.AWAIT) {
                int anotherPlayer;
                if (game.getFirstPlayer() == connectionId) anotherPlayer = game.getSecondPlayer();
                else anotherPlayer = game.getFirstPlayer();
                ResponseMessage winMessage = ResponseMessage.createFinishMessage(ResponseMessage.YOU);
                try {
                    sendMessage(anotherPlayer, winMessage);
                } catch (ServerException ignored) {
                }
            }
            game.setStatus(Game.FINISHED);
        }
    }

    @Override
    public void sendMessage(int connectionId, ResponseMessage message) throws ServerException {
        if (!started) {
            throw new ServerException("Server hasn't been started yet");
        }
        if (connectionId >= sockets.size()) {
            throw new ServerException("Incorrect connectionId value");
        }
        try {
            Socket socket = sockets.get(connectionId);
            socket.getOutputStream().write(message.getData());
            socket.getOutputStream().flush();
        } catch (IOException ex) {
            throw new ServerException("Can't send message", ex);
        }
    }

    @Override
    public List<ServerEventListener> getListeners() {
        return listeners;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public ServerSocket getServer() {
        return server;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public List<Socket> getSockets() {
        return sockets;
    }

    @Override
    public List<Game> getGames() {
        return games;
    }
}
