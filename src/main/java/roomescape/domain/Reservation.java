package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDateTime dateTime;

    public Reservation(Long id, String name, LocalDateTime dateTime) {
        validateNameLength(name);
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

    private void validateNameLength(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 빈칸이거나 NULL일 수 없습니다.");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("이름의 길이는 " + 255 + " 초과할 수 없습니다.");
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
