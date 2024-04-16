package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.format.DateTimeFormatter;

public record ReservationDto(
        Long id,
        String name,
        String date,
        String time
) {

    public static ReservationDto from(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                reservation.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
