package roomescape.reservation.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.Time;

public record ReservationRequest(String name,
                                 @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") LocalDate date,
                                 Long timeId) {

    public Reservation toEntity(Time time) {
        return new Reservation(name, date, time);
    }
}

