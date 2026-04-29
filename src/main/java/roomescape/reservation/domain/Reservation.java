package roomescape.reservation.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import roomescape.reservationtime.domain.ReservationTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;
}
