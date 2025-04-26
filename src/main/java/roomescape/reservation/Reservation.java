package roomescape.reservation;

import java.time.LocalDate;
import java.util.Objects;
import roomescape.time.ReservationTime;

public record Reservation(
        Long id, String name, LocalDate date, ReservationTime reservationTime
) {

    public Long id(){
        Objects.requireNonNull(id);
        return id;
    }

    public ReservationTime reservationTime(){
        Objects.requireNonNull(reservationTime);
        return reservationTime;
    }
}
