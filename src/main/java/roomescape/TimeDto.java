package roomescape;

import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public class TimeDto { // TODO: change class name

    private final Long id;
    private final LocalTime startAt;

    public TimeDto(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static TimeDto from(ReservationTime time) {
        // TODO: entity에서 dto로 변환하는 게 나을까?
        return new TimeDto(time.getId(), time.getStartAt());
    }

    public ReservationTime toEntity() {
        return new ReservationTime(id, startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
