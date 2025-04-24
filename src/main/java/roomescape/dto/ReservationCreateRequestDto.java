package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;

public record ReservationCreateRequestDto(String name, @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                          Long timeId) {
    public Reservation toEntity() {
        ReservationTime reservationTime = new ReservationTime(timeId, null);
        return new Reservation(null, name, date, reservationTime);
    }
}
