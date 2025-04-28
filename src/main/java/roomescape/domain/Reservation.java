package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private final Long id;
    private final String name;
    private final ReservationDateTime reservationDateTime;

    public Reservation(final Long id, final String name, final ReservationDateTime reservationDateTime) {
        validateName(name);
        validateReservationDateTime(reservationDateTime);
        this.id = id;
        this.name = name;
        this.reservationDateTime = reservationDateTime;
    }

    private void validateReservationDateTime(ReservationDateTime reservationDateTime) {
        if (reservationDateTime == null) {
            throw new IllegalArgumentException("예약 일시가 null일 수 없습니다.");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름이 빈 값일 수 없습니다.");
        }
        if (name.length() > 10) {
            throw new IllegalArgumentException("이름은 10자를 초과할 수 없습니다.");
        }
    }

    public Reservation withId(Long id) {
        return new Reservation(id, name, reservationDateTime);
    }

    public boolean isEqualId(final Long id) {
        return this.id.equals(id);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return reservationDateTime.getDate();
    }

    public ReservationTime getReservationTime() {
        return reservationDateTime.getTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(reservationDateTime, that.reservationDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, reservationDateTime);
    }
}
