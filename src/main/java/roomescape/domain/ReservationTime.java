package roomescape.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public record ReservationTime(Long id, String startAt) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ReservationTime {
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("[ERROR] 시간 ID는 양수여야 합니다.");
        }

        if (startAt == null) {
            throw new IllegalArgumentException("[ERROR] 시간은 null일 수 없습니다.");
        }

        String trimmed = startAt.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 시간은 비어 있을 수 없습니다.");
        }

        try {
            LocalTime.parse(trimmed, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 시간 형식은 HH:mm 이어야 합니다.");
        }

        startAt = trimmed;
    }
}
