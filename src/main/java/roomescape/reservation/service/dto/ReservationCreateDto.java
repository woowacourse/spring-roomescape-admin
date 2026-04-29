package roomescape.reservation.service.dto;

import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.ReservationTime;

public record ReservationCreateDto(String name, LocalDate date, Long timeId) {

    public Reservation toEntity(ReservationTime time) {
        return Reservation.builder()
                .name(name)
                .date(date)
                .time(time)
                .build();
    }
}
