package roomescape;

import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ReservationTime {
    private final long id;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime startAt;

    public ReservationTime(long id, LocalTime startAt) {
        validate(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime generateWithPrimaryKey(ReservationTime reservationTime, long newPrimaryKey) {
        return new ReservationTime(newPrimaryKey, reservationTime.startAt);
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    private void validate(LocalTime startAt) {
        if (startAt == null) {
            throw new NullPointerException("startAt(시작 시간)은 비어있을 수 없습니다.");
        }
    }
}
