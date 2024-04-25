package roomescape.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.dao.Reservation;
import roomescape.dao.ReservationTime;

public record ReservationSaveRequest(
        String name,
        LocalDate date,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        Long timeId) {

    public Reservation toEntity(ReservationTime reservationTime) {
        return new Reservation(null, name, date, reservationTime);
    }
}
