package roomescape.reservation.ui.dto;

import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservationResponseDto(long id,
                                     String name,
                                     LocalDate date,
                                     LocalTime time) {

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDatetime().toLocalDate(),
                reservation.getDatetime().toLocalTime());
    }

    public static List<ReservationResponseDto> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }
}
