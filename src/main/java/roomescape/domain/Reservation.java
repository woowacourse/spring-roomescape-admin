package roomescape.domain;

import roomescape.domain.vo.Name;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final Name name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = new Name(name);
        this.date = translateDate(date);
        this.time = translateTime(time);
    }

    private LocalDate translateDate(String date) {
        // TODO : 검증 로직

        return LocalDate.parse(date);
    }

    private LocalTime translateTime(String time) {
        // TODO : 검증 로직

        return LocalTime.parse(time);
    }

    public boolean isEqualId(Long other) {
        return other == this.id;
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
