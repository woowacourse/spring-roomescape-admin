package roomescape.usecase.Reservation;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.usecase.ReservationTime.ReservationTimeOutput;

public record ReservationOutput(Long id, String name, LocalDate date, ReservationTimeOutput reservationTimeOutput) {
    //    public static ReservationOutput from(Reservation reservation, ReservationTime reservationTime) {
//        return new ReservationOutput(
//                reservation.getId(),
//                reservation.getName(),
//                reservation.getDate(),
//                new ReservationTimeOutput(reservationTime)
//        );
//    }
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
