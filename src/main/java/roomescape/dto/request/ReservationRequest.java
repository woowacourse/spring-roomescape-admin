package roomescape.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.model.Reservation;

public record ReservationRequest(
        String date,
        String name,
        String time
) {
    public Reservation dtoToReservationWithoutId(){
        LocalDate date = LocalDate.parse(date());
        LocalTime time = LocalTime.parse(time());

        LocalDateTime reservationTime = LocalDateTime.of(date, time);
        return Reservation.createReservationWithoutID(name(), reservationTime);
    }
}
