package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(
        Long id,
        String name,
        LocalDate date,
        ReservationTime time
) {
    public static Reservation of(long id, String name, String date, long timeId, String time) {
        LocalDate parsedDate = LocalDate.parse(date);
        LocalTime parsedTime = LocalTime.parse(time);

        return new Reservation(id, name, parsedDate, new ReservationTime(timeId, parsedTime));
    }

    public static Reservation of(String name, LocalDate date, long timeId) {
        return new Reservation(null, name, date, ReservationTime.from(timeId));
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public long getTimeId() {
        return time.id();
    }
}
