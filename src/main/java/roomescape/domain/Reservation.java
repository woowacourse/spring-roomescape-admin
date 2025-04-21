package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {

    private static final int MAX_NAME_LENGTH = 255;
    private static final String ERROR_NAME_BLACK_MESSAGE = "이름은 공백이거나 NULL일 수 없습니다.";
    private static final String ERROR_NAME_LENGTH_MESSAGE = "이름의 길이는 " + MAX_NAME_LENGTH + " 초과할 수 없습니다.";

    private final Long id;
    private final String name;
    private final LocalDateTime dateTime;

    public Reservation(Long id, String name, LocalDateTime dateTime) {
        validateName(name);
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this(id, name, LocalDateTime.of(date, time));
    }

    public String formatDateTime(DateTimeFormatter formatter) {
        return dateTime.format(formatter);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ERROR_NAME_BLACK_MESSAGE);
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(ERROR_NAME_LENGTH_MESSAGE);
        }
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
}
