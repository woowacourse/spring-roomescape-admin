package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(
        long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static Reservation of(long id, String name, String date, String time) {
        LocalDate parsedDate = LocalDate.parse(date);
        LocalTime parsedTime = LocalTime.parse(time);

        return new Reservation(id, name, parsedDate, parsedTime);
    }
}
