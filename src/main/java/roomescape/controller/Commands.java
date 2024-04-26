package roomescape.controller;

public enum Commands {
    GET_RESERVATION(1),
    CREATE_RESERVATION(2),
    DELETE_RESERVATION(3);

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 6;

    Commands(final int commandNumber) {
        validate(commandNumber);
    }

    private void validate(final int commandNumber) {
        if (commandNumber < MIN_VALUE || commandNumber > MAX_VALUE) {
            throw new IllegalArgumentException("지원하지 않는 커맨드입니다");
        }
    }
}
