package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationSaveRequest(
        long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public ReservationSaveRequest(String name, LocalDate date, LocalTime time) {
        this(0L, name, date, time);
    }
}
