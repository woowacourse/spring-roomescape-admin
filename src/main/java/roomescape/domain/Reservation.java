package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    private Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(String name, LocalDate date, ReservationTime time) {
        validate(name);
        return new Reservation(null, name, date, time);
    }

    public static Reservation withId(Long id, String name, LocalDate date, ReservationTime time) {
        validate(name);
        return new Reservation(id, name, date, time);
    }

    private static void validate(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("예약자명이 없습니다.");
        }
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

    public ReservationTime getTime() {
        return time;
    }
}
