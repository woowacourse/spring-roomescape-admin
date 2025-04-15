package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record ReservationDto(Long id, String name, LocalDate date, LocalTime time) {

    public static List<ReservationDto> from(final List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationDto::from)
                .toList();
    }

    private static ReservationDto from(final Reservation reservation) {
        LocalDateTime dateTime = reservation.getDateTime();
        return new ReservationDto(reservation.getId(), reservation.getName(), dateTime.toLocalDate(), dateTime.toLocalTime());
    }
}
