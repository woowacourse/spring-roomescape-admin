package roomescape.dto;

import java.time.LocalDate;

public record ReservationSaveRequest(
        long id,
        String name,
        LocalDate date,
        long timeId
) {
    public ReservationSaveRequest(String name, LocalDate date, long timeId) {
        this(0L, name, date, timeId);
    }
}
