package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;

public final class Reservation {

    private static final long DEFAULT_ID = 0L;

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        validate(id, name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation createWithoutId(String name, LocalDate date, ReservationTime time) {
        return new Reservation(DEFAULT_ID, name, date, time);
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

    public ReservationTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, time);
    }

    private void validate(Long id, String name, LocalDate date, ReservationTime time) {
        validateNullId(id);
        validateBlankName(name);
        validateNullDate(date);
        validateNullTime(time);
    }

    private void validateBlankName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 비어있는 이름으로 예약을 생성할 수 없습니다.");
        }
    }

    private void validateNullId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("[ERROR] 비어있는 ID로 예약을 생성할 수 없습니다.");
        }
    }

    private void validateNullDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("[ERROR] 비어있는 예약날짜로 예약을 생성할 수 없습니다.");
        }
    }

    private void validateNullTime(ReservationTime reservationTime) {
        if (reservationTime == null) {
            throw new IllegalArgumentException("[ERROR] 비어있는 예약시간으로는 예약을 생성할 수 없습니다.");
        }
    }
}
