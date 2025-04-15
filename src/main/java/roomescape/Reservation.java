package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {

    public Reservation withId(Long newId) {
        return new Reservation(newId, name, date, time);
    }
}
