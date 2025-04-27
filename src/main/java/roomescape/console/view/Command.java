package roomescape.console.view;

public enum Command {

    ADD_RESERVATION("예약"),
    RESERVATIONS("예약 목록"),
    CANCEL_RESERVATION("예약 취소"),
    ADD_RESERVATION_TIME("예약 시간"),
    RESERVATION_TIMES("예약 시간 목록"),
    CANCEL_RESERVATION_TIME("예약 시간 취소"),
    EXIT("종료");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command from(int index) {
        return switch (index) {
            case 1 -> ADD_RESERVATION;
            case 2 -> RESERVATIONS;
            case 3 -> CANCEL_RESERVATION;
            case 4 -> ADD_RESERVATION_TIME;
            case 5 -> RESERVATION_TIMES;
            case 6 -> CANCEL_RESERVATION_TIME;
            default -> EXIT;
        };
    }
}
