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

    public static ReservationResponse reservationToDto(Reservation reservation) {
        LocalDateTime dateTime = reservation.getReservationTime();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                dateTime.toLocalDate().format(dateFormatter),
                dateTime.toLocalTime().format(timeFormatter));
    }

    public static List<ReservationResponse> reservationsToDtos(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::reservationToDto)
                .toList();
    }
}
