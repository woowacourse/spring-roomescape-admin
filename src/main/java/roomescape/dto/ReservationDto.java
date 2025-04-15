package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.model.Reservation;

public record ReservationDto(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {

    public static ReservationDto EntityToDto(Reservation reservation) {
        LocalDateTime dateTime = reservation.getReservationTime();
        return new ReservationDto(reservation.getId(), reservation.getName(), dateTime.toLocalDate(), dateTime.toLocalTime());
    }
}
