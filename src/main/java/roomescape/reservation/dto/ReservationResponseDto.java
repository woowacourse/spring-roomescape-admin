package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.entity.Reservation;

public record ReservationResponseDto(Long id, String name, LocalDate localDate, LocalTime localTime) {

    public static ReservationResponseDto toDto(Reservation reservation) {
        LocalDate localDate = reservation.getDateTime().toLocalDate();
        LocalTime localTime = reservation.getDateTime().toLocalTime();

        return new ReservationResponseDto(reservation.getId(), reservation.getName(), localDate, localTime);
    }
}
