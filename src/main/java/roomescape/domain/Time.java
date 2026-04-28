package roomescape.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public record Time(String value) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public Time {
        if (value == null) {
            throw new IllegalArgumentException("[ERROR] 시간은 null일 수 없습니다.");
        }

        String trimmed = value.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 시간은 비어 있을 수 없습니다.");
        }

        try {
            LocalTime.parse(trimmed, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 시간 형식은 HH:mm 이어야 합니다.");
        }

        value = trimmed;
    }
}
