package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private static final String TIME_FORMAT = "HH:mm";
    private static final long NO_ID = 0;

    private final long id;
    private final Name name;
    private final LocalDateTime dateTime;

    public Reservation(long id, Name name, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public Reservation(long id, Reservation reservation) {
        this(id, reservation.name, reservation.dateTime);
    }

    public Reservation(String name, String date, String time) {
        this(NO_ID, new Name(name), LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time)));
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getDate() {
        return dateTime.format(DateTimeFormatter.ISO_DATE);
    }

    public String getTime() {
        return dateTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }
}
