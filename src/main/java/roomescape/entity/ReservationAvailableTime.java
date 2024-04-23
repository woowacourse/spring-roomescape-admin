package roomescape.entity;

import java.time.LocalTime;

public class ReservationAvailableTime {
    private final Long id;
    private final LocalTime time;

    public ReservationAvailableTime(Long id, LocalTime time) {
        validate(time);
        this.id = id;
        this.time = time;
    }

    public ReservationAvailableTime(LocalTime time) {
        this(null, time);
    }

    private void validate(LocalTime time) {
        validateNonNull(time);
        validateHourlyUnit(time);
    }

    private void validateNonNull(LocalTime time) {
        if (time == null) {
            throw new NullPointerException("예약 가능한 시간은 null일 수 없습니다");
        }
    }

    private void validateHourlyUnit(LocalTime time) {
        if (time.getMinute() != 0) {
            throw new IllegalStateException("예약 가능 시각은 정각 단위여야 합니다: " + time);
        }
    }
}
