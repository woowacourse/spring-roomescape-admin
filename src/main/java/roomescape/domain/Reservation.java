package roomescape.domain;

import java.time.LocalDate;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this(null, name, date, time);
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        validateDate(date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateDate(LocalDate date, ReservationTime time) {
        if (date.isBefore(LocalDate.now()) || isSameDatePastTime(date, time)) {
            throw new IllegalArgumentException("이전 시간대는 예약 할 수 없습니다.");
        }
    }

    private boolean isSameDatePastTime(LocalDate date, ReservationTime time) {
        return date.isEqual(LocalDate.now()) && time.isBeforeNow();
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
