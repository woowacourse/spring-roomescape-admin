package roomescape.user.reservation.domain;

import java.time.LocalTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"id"})
public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(final Long id, final LocalTime startAt) {
        validateNotNull(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validateNotNull(final LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("Start time cannot be null");
        }
    }
}
