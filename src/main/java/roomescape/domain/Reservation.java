package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private final Long id;
    private final ReservationName name;
    private final ReservationDate date;
    private final ReservationTime time;

    public Reservation(
            final Long id,
            final ReservationName name,
            final ReservationDate date,
            final ReservationTime time
    ) {
        this.id = Objects.requireNonNull(id, "예약의 id는 null일 수 없습니다.");
        this.name = Objects.requireNonNull(name, "예약의 이름는 null일 수 없습니다.");
        this.date = Objects.requireNonNull(date, "예약의 날짜는 null일 수 없습니다.");
        this.time = Objects.requireNonNull(time, "예약의 시간는 null일 수 없습니다.");
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name.getValue();
    }

    public LocalDate date() {
        return date.date();
    }

    public LocalTime time() {
        return time.time();
    }

    public ReservationTime reservationTime() {
        return time;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
