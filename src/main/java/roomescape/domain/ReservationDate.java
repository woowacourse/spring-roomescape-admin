package roomescape.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public record ReservationDate(String value) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ReservationDate {
        if (value == null) {
            throw new IllegalArgumentException("[ERROR] 날짜는 null일 수 없습니다.");
        }

        String trimmed = value.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 날짜는 비어있을 수 없습니다.");
        }

        try {
            LocalDate.parse(trimmed, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 날짜 형식은 yyyy-MM-dd 여야 합니다.");
        }

        value = trimmed;
    }
}
