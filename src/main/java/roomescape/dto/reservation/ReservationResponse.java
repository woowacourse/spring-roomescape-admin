package roomescape.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.dto.time.TimeResponse;
import roomescape.model.Reservation;

public record ReservationResponse(Long id,
                                  String name,
                                  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") LocalDate date,
                                  TimeResponse time) {
    public static ReservationResponse toDto(Long id, Reservation reservation) {
        return new ReservationResponse(id, reservation.getName(), reservation.getDate(),
                TimeResponse.from(reservation.getTime()));
    }
}
