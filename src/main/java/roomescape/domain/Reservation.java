package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDateTime dateTime;

    public Reservation(Long id, String name, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public String formatDateTime(DateTimeFormatter formatter) {
        return dateTime.format(formatter);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
