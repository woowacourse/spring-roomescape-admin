package roomescape.exception;

public class ReservationTimeInUseException extends RuntimeException {

    public ReservationTimeInUseException(Long id) {
        super("해당 시간에 예약이 존재하여 삭제할 수 없습니다: " + id);
    }
}