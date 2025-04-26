package roomescape.usecase.reservation;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.usecase.reservationTime.ReservationTimeOutput;

public record ReservationOutput(Long id, String name, LocalDate date, ReservationTimeOutput reservationTimeOutput) {

    public static ReservationOutput from(Reservation reservation) {
        return new ReservationOutput(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                new ReservationTimeOutput(
                        reservation.getReservationTime().getId(),
                        reservation.getReservationTime().getStart_at()
                )
        );
    }
}
