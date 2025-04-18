package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.model.Reservation;

public record ReservationRes(Long id, String name, LocalDate date, LocalTime time) {

    public static ReservationRes from(final Long id, final Reservation reservation) {
        return new ReservationRes(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
