package roomescape.reservation.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.time.domain.ReservationTime;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;
}
