package roomescape.reservation.dto;

import java.time.format.DateTimeFormatter;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.dto.ReservationTimeDto;

public record ReservationDto(Long id, String name, String date, ReservationTimeDto time) {

    public static ReservationDto from(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                ReservationTimeDto.from(reservation.getTime())
        );
    }
}
