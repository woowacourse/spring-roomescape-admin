package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequestDto(
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        Long timeId
) {
    public Reservation toEntity(ReservationTime time) {
        return new Reservation(this.name, this.date, time);
    }
}
