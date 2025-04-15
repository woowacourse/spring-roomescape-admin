package roomescape.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import roomescape.model.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {

    public static ReservationResponse entityToDto(Reservation reservation) {
        LocalDateTime dateTime = reservation.getReservationTime();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new ReservationResponse(reservation.getId(), reservation.getName(), dateTime.toLocalDate().format(dateFormatter), dateTime.toLocalTime().format(timeFormatter));
    }
}
