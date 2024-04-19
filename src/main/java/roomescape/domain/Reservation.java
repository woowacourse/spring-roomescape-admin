package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private static final String TIME_FORMAT = "HH:mm";
    private static final long NO_ID = 0;

    private final long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(long id, Reservation reservation) {
        this(id, reservation.name, reservation.date, reservation.time);
    }

    public Reservation(String name, String date, String time) {
        this(NO_ID, name, LocalDate.parse(date), LocalTime.parse(time));
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ISO_DATE);
    }

    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }
}
