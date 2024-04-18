package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reservation {
    private static final Pattern NAME_PATTERN = Pattern.compile("^\\d+$");
    private static final int TIME_UNIT = 10;

    private Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(String name, LocalDate date, LocalTime time) {
        validateName(name);
        validateDate(date);
        validateTime(time);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("예약자 이름은 비어있을 수 없습니다.");
        }
        Matcher matcher = NAME_PATTERN.matcher(name);
        if (matcher.matches()) {
            throw new IllegalArgumentException("예약자 이름은 숫자로만 구성될 수 없습니다.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("유효하지 않은 예약 날짜입니다.");
        }
    }

    private void validateTime(LocalTime time) {
        if (time == null || time.getMinute() % TIME_UNIT != 0) {
            throw new IllegalArgumentException("유효하지 않은 예약 시간입니다.");
        }
    }

    public void initializeId(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("예약 ID를 변경할 수 없습니다.");
        }
        this.id = id;
    }

    public boolean hasSameDateTime(LocalDate date, LocalTime time) {
        return this.time.equals(time) && this.date.equals(date);
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
