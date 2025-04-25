package roomescape.usecase.Reservation;

import java.time.LocalDate;
import roomescape.enttity.ReservationTime.ReservationTime;
import roomescape.usecase.ReservationTime.ReservationTimeOutput;

public record ReservationOutput(Long id, String name, LocalDate date, ReservationTimeOutput reservationTimeOutput) {
    public static ReservationOutput from(Reservation reservation, ReservationTime reservationTime) {
        return new ReservationOutput(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                new ReservationTimeOutput(reservationTime)
        );
    }
}
