package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {

    private static final int NAME_MAX_LENGTH = 10;

    private final long id;
    private final String name;
    private final LocalDateTime dateTime;

    public Reservation(final long id, final String name, final LocalDateTime dateTime) {
        validate(name, dateTime);
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    private void validate(final String name, final LocalDateTime dateTime) {
        validateName(name);
        validateDateTime(dateTime);
    }

    private void validateName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("[ERROR] name은 null이 될 수 없습니다.");
        }
        if (name.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 이름은 10글자를 넘을 수 없습니다.");
        }
    }

    private void validateDateTime(final LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("[ERROR] dateTime은 null이 될 수 없습니다.");
        }
        if (dateTime.toLocalDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("[ERROR] 과거 날짜로 예약할 수 없습니다.");
        }
    }

    public boolean isSameId(final long givenId) {
        return id == givenId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
