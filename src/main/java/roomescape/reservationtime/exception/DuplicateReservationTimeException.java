package roomescape.reservationtime.exception;

public class DuplicateReservationTimeException extends RuntimeException {
    private final String time;

    public DuplicateReservationTimeException(String time) {
        super(String.format("이미 존재하는 예약 시간입니다: %s", time));
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}

