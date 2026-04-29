package roomescape.reservation.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import roomescape.reservationtime.domain.ReservationTime;

@Getter
@Builder
@AllArgsConstructor
public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Long getTimeId() {
        return time.getId();
    }
}
