package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

    private static final String NULL_VALUE_EXCEPTION_MESSAGE = "널 값은 입력될 수 없습니다.";

    private Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this.name = Objects.requireNonNull(name, NULL_VALUE_EXCEPTION_MESSAGE);
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this(name, date, time);
        this.id = id;
    }

    public boolean isSameDate(final Reservation reservation) {
        return date.equals(reservation.getDate());
    }

    public boolean isSameTimeId(final Reservation reservation) {
        return time.isSameId(reservation.getTime());
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

    public Long getTimeId() {
        return time.getId();
    }

    public LocalTime getTimeStartAt() {
        return time.getStartAt();
    }
}
