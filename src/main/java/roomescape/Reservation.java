package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public record Reservation(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public Reservation {
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

    public boolean hasSameId(Long id) {
        return Objects.equals(this.id, id);
    }
}
