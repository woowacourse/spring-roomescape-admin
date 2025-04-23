package roomescape.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        @JsonFormat(pattern = "HH:mm") LocalTime time
) {
    public static ReservationResponse toDto(Reservation reservation) {
        LocalDate date = reservation.getDateTime().toLocalDate();
        LocalTime time = reservation.getDateTime().toLocalTime();
        return new ReservationResponse(reservation.getId(), reservation.getName(), date, time);
    }
}
