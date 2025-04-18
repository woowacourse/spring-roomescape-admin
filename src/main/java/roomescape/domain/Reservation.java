package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {

    private static final int MAX_NAME_LENGTH = 10;
    private final Long id;
    private final String name;
    private final LocalDateTime dateTime;

    public Reservation(final Long id, final String name, final LocalDateTime dateTime) {
        validate(id, name, dateTime);
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public boolean isSameId(final Long givenId) {
        return id.equals(givenId);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    private void validate(final Long id, final String name, final LocalDateTime dateTime) {
        validateId(id);
        validateName(name);
        validateDateTime(dateTime);
    }

    private void validateId(final Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id는 null이 될 수 없습니다.");
        }
    }

    private void validateName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name은 null이 될 수 없습니다.");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Name은 10글자를 초과할 수 없습니다.");
        }
    }

    private void validateDateTime(final LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("DateTime은 null이 될 수 없습니다.");
        }

        boolean isPast = dateTime.toLocalDate().isBefore(LocalDate.now());
        if (isPast) {
            throw new IllegalArgumentException("과거 날짜에 대한 예약을 할 수 없습니다.");
        }
    }
}
