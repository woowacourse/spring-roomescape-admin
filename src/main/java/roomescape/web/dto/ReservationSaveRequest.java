package roomescape.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.dao.Reservation;
import roomescape.dao.ReservationTime;

public record ReservationSaveRequest(
        String name,
        LocalDate date,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime time) {

    public Reservation toEntity() {
        return new Reservation(null, name, date, new ReservationTime(null, time));
    }
}
