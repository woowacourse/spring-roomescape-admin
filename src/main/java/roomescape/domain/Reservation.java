package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    private static final int NAME_MAX_LENGTH = 10;

    private final long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(final long id, final String name, final LocalDate date, final ReservationTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(final String name, final LocalDate date, final ReservationTime time) {
        validateName(name);
        validateDateTime(date, time);
    }

    private void validateName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("[ERROR] name은 null이 될 수 없습니다.");
        }
        if (name.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 이름은 10글자를 넘을 수 없습니다.");
        }
    }

    private void validateDateTime(final LocalDate date, final ReservationTime time) {
        if (date == null) {
            throw new IllegalArgumentException("[ERROR] 날짜는 null이 될 수 없습니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException("[ERROR] 시간은 null이 될 수 없습니다.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("[ERROR] 과거 날짜로 예약할 수 없습니다.");
        }
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
}
