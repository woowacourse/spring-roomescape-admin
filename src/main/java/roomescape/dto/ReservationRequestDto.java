package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.model.Id;
import roomescape.model.Reservation;

public record ReservationRequestDto(String name, LocalDate date, LocalTime time) {

    public Reservation toEntity(Id id) {
        return new Reservation(
                id, name, date, time
        );
    }
}
