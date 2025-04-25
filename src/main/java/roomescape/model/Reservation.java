package roomescape.model;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private static final String NULL_VALUE_EXCEPTION = "널 값은 입력될 수 없습니다.";

    private Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this.name = Objects.requireNonNull(name, NULL_VALUE_EXCEPTION);
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this(name, date, time);
        this.id = id;
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
