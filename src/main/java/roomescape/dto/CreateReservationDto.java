package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.entity.Reservation;

public record CreateReservationDto(String name, String date, String time) {

    public Reservation toEntity() {
        return new Reservation(
                null,
                name,
                LocalDate.parse(date),
                LocalTime.parse(time)
        );
    }
}
