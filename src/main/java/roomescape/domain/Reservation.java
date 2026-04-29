package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dto.ReservationRequestDto;

public class Reservation {
    private final Long id;

    private final String name;

    private final LocalDate date;

    private final LocalTime time;

    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(Long id, ReservationRequestDto dto) {
        return new Reservation(id,
                dto.name(),
                dto.date(),
                dto.time());
    }

    public static Reservation create(Long id, String name, LocalDate date, LocalTime time) {
        return new Reservation(id,
                name,
                date,
                time);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
