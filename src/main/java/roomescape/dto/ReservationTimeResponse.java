package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.model.ReservationTime;

public record ReservationTimeResponse(Long id, @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul") LocalTime startAt) {

    public static ReservationTimeResponse toDto(ReservationTime reservationTime){
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getTime());
    }
}
