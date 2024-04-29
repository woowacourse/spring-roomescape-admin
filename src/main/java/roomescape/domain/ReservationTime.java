package roomescape.domain;

import org.springframework.http.HttpStatus;
import roomescape.exception.ReservationTimeException;

import java.time.LocalTime;

public class ReservationTime {

    private long id;
    private LocalTime startAt;

    public ReservationTime() {
    }

    public ReservationTime(long id) {
        this(id, LocalTime.MIN);
    }

    public ReservationTime(long id, LocalTime startAt) {
        validateNotNull(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(LocalTime startAt) {
        this(-1, startAt);
    }

    private void validateNotNull(LocalTime startAt) {
        if (startAt == null) {
            throw new ReservationTimeException(HttpStatus.BAD_REQUEST, "시작 시간은 비어있을 수 없습니다");
        }
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
