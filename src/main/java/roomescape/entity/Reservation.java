package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    public static final LocalTime START_RESERVATION_TIME = LocalTime.of(10, 0);
    public static final LocalTime LAST_RESERVATION_TIME = LocalTime.of(23, 0);

    private long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(final long id, final String name, final LocalDate date, final LocalTime time) {
        validateTime(time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final String name, final LocalDate date, final LocalTime time) {
        validateTime(time);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateTime(final LocalTime time) {
        if(time.isBefore(START_RESERVATION_TIME) || time.isAfter(LAST_RESERVATION_TIME)){
            throw new IllegalArgumentException("예약할 수 없는 시간입니다.");
        }
    }

    public static Reservation createWithoutId(String name, LocalDate date, LocalTime time) {
        return new Reservation(name, date, time);
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

    public void setId(long id) {
        this.id = id;
    }
}
