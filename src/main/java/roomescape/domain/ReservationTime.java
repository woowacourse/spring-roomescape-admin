package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {

    public static final String START_AT_IS_NOT_EMPTY = "시간은 비어 있을 수 없습니다.";

    private Long id;
    private LocalTime startAt;

    public ReservationTime() {
    }

    public ReservationTime(LocalTime startAt) {
        this(null, startAt);
    }

    public ReservationTime(Long id, LocalTime startAt) {
        validateTime(startAt);

        this.id = id;
        this.startAt = startAt;
    }

    private void validateTime(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException(START_AT_IS_NOT_EMPTY);
        }
    }

    public Long getId() {
        return id;
    }


    public LocalTime getStartAt() {
        return startAt;
    }

}
