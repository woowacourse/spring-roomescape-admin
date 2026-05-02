package roomescape.exception;

public class ReservationTimeNotFoundException extends RuntimeException {

    public ReservationTimeNotFoundException(Long id) {
        super("존재하지 않는 시간 ID입니다: " + id);
    }
}
