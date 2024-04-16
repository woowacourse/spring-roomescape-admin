package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(Long id, String name, LocalDate localDate, LocalTime localTime) {

    public static ReservationDto fromEntity(Reservation reservation) {
        return new ReservationDto(reservation.getId(), reservation.getName(), reservation.getLocalDate().toLocalDate(), reservation.getLocalDate().toLocalTime());
    }
}
