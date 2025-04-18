package roomescape.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import roomescape.model.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {

    private static final DateTimeFormatter TIME_FORMATTER = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ReservationResponse reservationToDto(Reservation reservation) {
        LocalDateTime dateTime = reservation.getReservationTime();
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                dateTime.toLocalDate().format(DATE_FORMATTER),
                dateTime.toLocalTime().format(TIME_FORMATTER));
    }

    public static List<ReservationResponse> reservationsToDtos(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::reservationToDto)
                .toList();
    }
}
