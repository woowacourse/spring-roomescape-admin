package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(LocalTime startAt) {
        this(null, startAt);
    }

    public ReservationTime(Long id, LocalTime startAt) {
        validateStartAt(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime withId(Long id) {
        validateId(id);

        if (this.id != null) {
            throw new IllegalStateException("이미 id가 존재하는 예약 시간입니다.");
        }

        return new ReservationTime(id, startAt);
    }

    private void validateStartAt(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("예약 시간은 비어 있을 수 없습니다.");
        }
    }

    private void validateId(Long id){
        if (id == null) {
            throw new IllegalArgumentException("예약 시간 id는 비어 있을 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
