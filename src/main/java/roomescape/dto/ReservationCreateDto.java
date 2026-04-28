package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationCreateDto(String name, LocalDate date, LocalTime time) {

    public Reservation toEntity() {
        return Reservation.builder()
                .name(name)
                .date(date)
                .time(time)
                .build();
    }
}