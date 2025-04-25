package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.model.ReservationWithTimeId;

public record ReservationRequest(
                                 String name,
                                 @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") LocalDate date,
                                 Long timeId) {

    public ReservationWithTimeId toReservationWithId() {
        return new ReservationWithTimeId(null, name, date, timeId);
    }
}
