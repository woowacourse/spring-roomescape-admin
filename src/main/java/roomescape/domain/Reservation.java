package roomescape.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservation {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, String name, String date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = LocalDate.parse(date, DATE_FORMATTER);
        this.time = time;
    }

    public Reservation(String name, String date, ReservationTime time) {
        this(null, name, date, time);
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String date() {
        return date.format(DATE_FORMATTER);
    }

    public ReservationTime time() {
        return time;
    }

}
