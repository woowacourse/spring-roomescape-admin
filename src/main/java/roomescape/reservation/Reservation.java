package roomescape.reservation;

import java.time.LocalDate;
import lombok.Getter;
import roomescape.reservationTime.ReservationTime;

@Getter
public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}