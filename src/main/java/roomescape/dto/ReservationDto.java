package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationDto(Long id, String name, String date, String time) {

    public static ReservationDto fromEntity(Reservation reservation) {
        return new ReservationDto(reservation.getId(), reservation.getName(), reservation.getDate().format(
                DateTimeFormatter.ISO_DATE), reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
