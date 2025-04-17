package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.entity.Reservation;

public record ReservationRequestDto(
        String name,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime time
) {

    public Reservation toEntity(Long id) {
        return new Reservation(id, this.name(), this.date(), this.time());
    }
}
