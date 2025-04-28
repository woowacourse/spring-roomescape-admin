package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private static final int MAX_NAME_LENGTH = 10;
    private Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(final Long id, final String name, final LocalDate date, final ReservationTime time) {
        validate(id, name, date);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final String name, final LocalDate date, final ReservationTime time) {
        validate(name, date);
        this.name = name;
        this.date = date;
        this.time = time;
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

    public void setId(final long id) {
        this.id = id;
    }

    private void validate(final String name, final LocalDate date) {
        validateName(name);
        validateDate(date);
    }

    private void validate(final Long id, final String name, final LocalDate date) {
        validateId(id);
        validateName(name);
        validateDate(date);
    }

    private void validateId(final Long id) {
        if (id == null) {
            throw new IllegalArgumentException("[ERROR] 예약 기록 id는 null이 될 수 없습니다.");
        }
    }

    private void validateName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("[ERROR] 예약자 이름은 null이 될 수 없습니다.");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 예약자 이름은 10글자를 초과할 수 없습니다.");
        }
    }

    private void validateDate(final LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("[ERROR] 예약 날짜는 null이 될 수 없습니다.");
        }

        boolean isPast = date.isBefore(LocalDate.now());
        if (isPast) {
            throw new IllegalArgumentException("[ERROR] 과거 날짜에 대한 예약을 할 수 없습니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
