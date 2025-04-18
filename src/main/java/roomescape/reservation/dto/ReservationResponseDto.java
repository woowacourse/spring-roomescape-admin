package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.reservation.entity.Reservation;

public record ReservationResponseDto(Long id, String name, LocalDate date, LocalTime time) {

    public static ReservationResponseDto toDto(Reservation reservation) {
        LocalDateTime dateTime = reservation.getDateTime();
        LocalDate localDate = dateTime.toLocalDate();
        LocalTime localTime = dateTime.toLocalTime();

        return new ReservationResponseDto(reservation.getId(), reservation.getName(), localDate, localTime);
    }
}
