package roomescape.domain;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class Reservation {
    private final Long id;
    private final String reservationName;
    private final LocalDate reservationDate;
    private final ReservationTime reservationTime;

    public Reservation(Long id,
                       String reservationName,
                       LocalDate reservationDate,
                       ReservationTime reservationTime) {
        this.id = id;
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public Reservation(String reservationName,
                       LocalDate reservationDate,
                       ReservationTime reservationTime) {
        this(null, reservationName, reservationDate, reservationTime);
    }
}
