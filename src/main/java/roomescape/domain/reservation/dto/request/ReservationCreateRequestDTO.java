package roomescape.domain.reservation.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequestDTO(String name, LocalDate date, LocalTime time, Long timeId) {

    public ReservationCreateRequestDTO(String name, LocalDate date, LocalTime time) {
        this(name, date, time, null);
    }
}
