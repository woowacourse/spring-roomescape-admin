package roomescape.model;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private final EntityId id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(EntityId id, String name, LocalDate date, ReservationTime time) {
        validate(id, name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(EntityId id, String name, LocalDate date, ReservationTime time) {
        validateNotNull(id, name, date, time);
        validateName(name);
    }

    private void validateNotNull(EntityId id, String name, LocalDate date, ReservationTime time) {
        if (id == null || name == null || date == null || time == null) {
            throw new IllegalArgumentException("id와 예약자 이름, 예약 날짜, 시간을 올바르게 입력해 주세요.");
        }
    }

    private void validateName(String name) {
        if (name.isBlank() || name.isEmpty()) {
            throw new IllegalArgumentException("예약자 이름은 빈 칸일 수 없습니다.");
        }
    }

    public Long getId() {
        return id.getId();
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(name, that.name) && Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, time);
    }
}
