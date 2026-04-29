package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.ReservationTime;

public record ReservationResponse(Long id, String name, LocalDate date, ReservationTimeResponse timeResponse) {
    public static ReservationResponse from(Long id, String name, LocalDate date, ReservationTime time) {
        return new ReservationResponse(id, name, date, ReservationTimeResponse.from(time.getId(), time.getStartAt()));
    }
}
