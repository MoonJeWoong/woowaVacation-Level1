package exception;

public class UnsupportedOperationAtPlayerException extends RuntimeException {

    public static final String MESSAGE = "[ERROR] Player에서는 지원하지 않는 기능입니다.";

    public UnsupportedOperationAtPlayerException() {
        super(MESSAGE);
    }
}
