package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(String name, LocalDate date, LocalTime time) {
        this(null, name, date, time);
    }

    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        if (reservation.isEntity()) {
            throw new IllegalArgumentException("이미 Entity화 되어있는 객체입니다.");
        }
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    private boolean isEntity() {
        return id != null;
    }

    private void validate(String name, LocalDate date, LocalTime time) {
        validateNotNull(name, date, time);
        validateName(name);
    }

    private void validateNotNull(String name, LocalDate date, LocalTime time) {
        if (name == null || date == null || time == null) {
            throw new IllegalArgumentException("예약자 이름과 예약 날짜, 시간을 올바르게 입력해 주세요.");
        }
    }

    private void validateName(String name) {
        if (name.isBlank() || name.isEmpty()) {
            throw new IllegalArgumentException("예약자 이름은 빈 칸일 수 없습니다.");
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

    public LocalTime getTime() {
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
