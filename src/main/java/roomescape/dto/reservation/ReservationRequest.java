package roomescape.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.model.Reservation;
import roomescape.model.Time;

public record ReservationRequest(String name,
                                 @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") LocalDate date,
                                 Long timeId) {

    public Reservation toEntity(Time time) {
        return new Reservation(name, date, time);
    }
}

