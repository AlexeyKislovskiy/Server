package fertdt.listeners;

import fertdt.RequestMessage;
import fertdt.Server;
import fertdt.exceptions.ServerEventListenerException;
import fertdt.exceptions.ServerException;

public interface ServerEventListener {
    void init(Server server);

    void handle(int connectionId, RequestMessage message) throws ServerEventListenerException, ServerException;

    int getType();
}
