package roomescape.reservation.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import roomescape.reservationtime.domain.ReservationTime;

@Getter
@Builder
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;
}
