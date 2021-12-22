package fertdt.exceptions;

public class ServerEventListenerException extends Exception{
    public ServerEventListenerException() {
        super();
    }

    public ServerEventListenerException(String message) {
        super(message);
    }

    public ServerEventListenerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerEventListenerException(Throwable cause) {
        super(cause);
    }
}
