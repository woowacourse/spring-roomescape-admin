package roomescape;

import lombok.Getter;
import roomescape.dto.TimeData;

import java.time.LocalTime;

@Getter
public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    private ReservationTime(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime create(TimeData data) {
        return new ReservationTime(
                null,
                data.startAt()
        );
    }

    public static ReservationTime restore(final Long id, final LocalTime startAt) {
        return new ReservationTime(id, startAt);
    }
}
