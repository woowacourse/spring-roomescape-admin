package roomescape.model;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    private ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime of(Long id, String startAt) {
        validateEmpty(id, startAt);
        LocalTime startTime = parseTime(startAt);
        return new ReservationTime(id, startTime);
    }

    private static void validateEmpty(Long id, String startAt) {
        if (id == null || startAt == null) {
            throw new IllegalArgumentException("시간 정보에 빈 값이 포함될 수 없습니다.");
        }
    }

    private static LocalTime parseTime(String startAt) {
        try {
            return LocalTime.parse(startAt);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("유효하지 않은 시간입니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
