package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationRegisterDto(
        LocalDate date,
        String name,
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
    public Reservation toEntity() {
        return new Reservation(name, date, time);
    }
}
