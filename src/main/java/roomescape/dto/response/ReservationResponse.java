package roomescape.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import roomescape.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static ReservationResponse reservationToDto(Reservation reservationWithId) {
        return new ReservationResponse(reservationWithId.getId(), reservationWithId.getName(), reservationWithId.getDateTime().toLocalDate(), reservationWithId.getDateTime().toLocalTime().truncatedTo(ChronoUnit.MINUTES));
    }
}
