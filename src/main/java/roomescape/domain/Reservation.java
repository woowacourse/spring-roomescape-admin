package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reservation {
    private static final Pattern NAME_PATTERN = Pattern.compile("^\\d+$");

    private Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(String name, LocalDate date, LocalTime time) {
        validateName(name);
        validateDateTime(date, time);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateName(String name) {
        Matcher matcher = NAME_PATTERN.matcher(name);
        if (matcher.matches()) {
            throw new IllegalArgumentException("예약자 이름은 숫자로만 구성될 수 없습니다.");
        }
    }

    private void validateDateTime(LocalDate date, LocalTime time) {
        if (date == null || time == null) {
            throw new IllegalArgumentException("예약은 시간과 날짜가 null일 수 없습니다.");
        }
    }

    public void initializeId(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("예약 ID를 변경할 수 없습니다.");
        }
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
