package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(
        long id,
        String name,
        LocalDate date,
        ReservationTime time
) {
    public static Reservation of(long id, String name, String date, long timeId, String time) {
        LocalDate parsedDate = LocalDate.parse(date);
        LocalTime parsedTime = LocalTime.parse(time);

        return new Reservation(id, name, parsedDate, new ReservationTime(timeId, parsedTime));
    }
}
