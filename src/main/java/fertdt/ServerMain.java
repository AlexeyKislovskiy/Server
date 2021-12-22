package fertdt;

import fertdt.exceptions.ServerException;
import fertdt.listeners.*;

public class ServerMain {
    public static void main(String[] args) {
        Server server = new SocketServer(10034);
        try {
            server.registerListener(new StartRequestListener());
            server.registerListener(new StopWaitingStartRequest());
            server.registerListener(new CharacterAndSkillSelectListener());
            server.registerListener(new NormalMoveEventListener());
            server.registerListener(new NormalSkillListener());
            server.registerListener(new SpecialSkillListener());
            server.registerListener(new ExitRequestEventListener());
            server.start();
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

}

