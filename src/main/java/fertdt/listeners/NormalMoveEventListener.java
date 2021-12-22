package fertdt.listeners;

import fertdt.RequestMessage;
import fertdt.exceptions.ServerEventListenerException;
import fertdt.exceptions.ServerException;

public class NormalMoveEventListener extends AbstractServerEventListener {
    @Override
    public void handle(int connectionId, RequestMessage message) throws ServerEventListenerException, ServerException {

    }

    @Override
    public int getType() {
        return RequestMessage.NORMAL_MOVE;
    }
}
