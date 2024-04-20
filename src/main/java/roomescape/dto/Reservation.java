package roomescape.dto;

import java.time.LocalDate;

public record Reservation(
        long id,
        String name,
        LocalDate date,
        ReservationTime time
) {
    public static Reservation of(long id, String name, String date, String time) {
        LocalDate parsedDate = LocalDate.parse(date);
        ReservationTime parsedTime = ReservationTime.parse(time);

        return new Reservation(id, name, parsedDate, parsedTime);
    }
}
