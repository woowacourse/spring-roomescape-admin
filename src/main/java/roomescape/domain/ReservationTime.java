package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private Long id;
    private LocalTime startAt;

    public ReservationTime(final Long id, final LocalTime startAt) {
        validateTime(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(final LocalTime startAt) {
        validateTime(startAt);
        this.startAt = startAt;
    }

    private void validateTime(final LocalTime startAt) {
        if (startAt.isBefore(LocalTime.now())) {
            throw new IllegalArgumentException("예약 시각은 현재보다 이전일 수 없습니다.");
        }
    }

    public boolean isBefore(final LocalTime comparedTime) {
        return startAt.isBefore(comparedTime);
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
