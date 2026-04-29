package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(Long id, String name, LocalDate date, LocalTime time) {
        validate(id, name);
        return new Reservation(id, name, date, time);
    }

    private static void validate(Long id, String name) {
        if (id == null) {
            throw new IllegalArgumentException("예약 ID가 없습니다.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("예약자명이 없습니다.");
        }
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

    public LocalTime getTime() {
        return time;
    }
}
