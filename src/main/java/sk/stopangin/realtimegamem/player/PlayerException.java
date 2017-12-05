package sk.stopangin.realtimegamem.player;

public class PlayerException extends RuntimeException {
    public PlayerException(String message) {
        super(message);
    }

    public PlayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
