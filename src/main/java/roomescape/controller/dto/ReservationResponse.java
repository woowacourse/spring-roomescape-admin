package roomescape.controller.dto;

import java.time.LocalDate;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.TimeSlot;

public record ReservationResponse(Long id, String name, LocalDate date, TimeSlotCreationResponse timeSlot) {

    public static ReservationResponse from(Reservation reservation) {
        Name name = reservation.getName();
        ReservationDate reservationDate = reservation.getReservationDate();
        TimeSlot timeSlot = reservation.getReservationTime();
        return new ReservationResponse(
                reservation.getId(),
                name.asText(),
                reservationDate.getDate(),
                TimeSlotCreationResponse.from(timeSlot)
        );
    }
}
