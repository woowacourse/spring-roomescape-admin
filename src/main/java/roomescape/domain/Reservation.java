package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private static final int VALID_MAX_NAME_LENGTH = 4;
    private long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(final String name, final LocalDate date, final LocalTime time) {
        validateNameLength(name);
        validateDateTime(date, time);
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
        if (name.isEmpty()) {
            throw new IllegalArgumentException("예약자의 이름은 1글자 이상이어야 합니다.");
        }

        if (name.length() > VALID_MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("예약자의 이름은 4글자를 초과할 수 없습니다.");
        }
    }

    private void validateDateTime(final LocalDate date, final LocalTime time) {
        if (date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now()) && time.isBefore(LocalTime.now())) {
            throw new IllegalArgumentException("예약 날짜와 시각은 현재보다 이전일 수 없습니다.");
        }
    }
}
