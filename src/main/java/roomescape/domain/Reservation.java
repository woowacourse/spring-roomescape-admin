package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(final String name, final LocalDate date, final LocalTime time) {
        validateNameLength(name);
        this.name = name;
        this.date = date;
        this.time = time;
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

    public LocalTime getTime() {
        return time;
    }

    public void setId(long id) {
        this.id = id;
    }

    private void validateNameLength(final String name) {
        if (name.length() > 4) {
            throw new IllegalArgumentException("예약자의 이름은 4글자를 초과할 수 없습니다.");
        }
    }
}
