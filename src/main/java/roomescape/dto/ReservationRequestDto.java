package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.entity.Reservation;

public record ReservationRequestDto(
        String name,
        LocalDate date,
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {

    public Reservation toEntity(Long id) {
        return new Reservation(id, this.name(), this.date(), this.time());
    }
}
