package roomescape.view;

public enum ReservationCommand {
    RESERVATION_INQUIRY(1, "예약 조회"),
    RESERVATION_CREATE(2, "예약 생성"),
    RESERVATION_DELETE(3, "예약 삭제"),
    RESERVATION_TIME_INQUIRY(4, "예약 시간 조회"),
    RESERVATION_TIME_CREATE(5, "예약 시간 생성"),
    RESERVATION_TIME_DELETE(6, "예약 시간 삭제"),
    CONSOLE_END(-1, "콘솔창 종료");

    private final int value;
    private final String message;

    ReservationCommand(final int value, final String message) {
        this.value = value;
        this.message = message;
    }

    public static ReservationCommand from(final int value) {
        for (final ReservationCommand command : ReservationCommand.values()) {
            if (command.value == value) {
                return command;
            }
        }
        throw new IllegalArgumentException(String.format("%d 는 없는 숫자입니다", value));
    }

    public static String formatMessage() {
        StringBuilder sb = new StringBuilder();
        for (final ReservationCommand command : ReservationCommand.values()) {
            sb.append(command.value)
              .append(".")
              .append(command.message)
              .append("\t");
        }
        return sb.toString();
    }

    public String getMessage() {
        return message;
    }

    public boolean isEnd() {
        return this == ReservationCommand.CONSOLE_END;
    }
}
