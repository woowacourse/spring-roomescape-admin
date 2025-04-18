package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Reservation {

    private Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.date = date;
        this.time = time;
        this.name = name;
        this.id = id;
    }

    public Reservation(String name, LocalDate date, LocalTime time) {
        this(null, name, date, time);
    }

    private void validate(String name, LocalDate date, LocalTime time) {
        if (name == null) {
            throw new IllegalArgumentException("이름은 null이 될 수 없습니다.");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("날짜는 null이 될 수 없습니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException("시간은 null이 될 수 없습니다.");
        }
    }

    public void updateId(Long savedId) {
        this.id = savedId;
    }

    public boolean isEqualId(Long id) {
        return Objects.equals(this.id, id);
    }

    public static Reservation deepCopyOf(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("null인 객체는 복사할 수 없습니다");
        }
        return new Reservation(reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }
}
