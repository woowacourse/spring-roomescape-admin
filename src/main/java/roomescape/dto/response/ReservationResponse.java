package roomescape.dto.response;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {

    public static List<ReservationResponse> from(final List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public static ReservationResponse from(final Reservation reservation) {
        LocalDateTime dateTime = reservation.getDateTime();
        return new ReservationResponse(reservation.getId(), reservation.getName(), dateTime.toLocalDate(), dateTime.toLocalTime());
    }
}
