package roomescape.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.dao.Reservation;

public record ReservationSaveRequest(
        String name,
        LocalDate date,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime time) {

    public Reservation toEntity() {
        return new Reservation(null, this.name(), this.date(), this.time());
    }
}
