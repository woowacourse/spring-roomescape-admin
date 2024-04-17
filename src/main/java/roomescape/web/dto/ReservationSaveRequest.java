package roomescape.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationSaveRequest(
        LocalDate date,
        String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime time) {
    public Reservation toDomain() {
        return new Reservation(this.name(), this.date(), this.time());
    }
}
