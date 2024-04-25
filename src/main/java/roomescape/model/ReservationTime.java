package roomescape.model;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class ReservationTime {

    private static final Long DEFAULT_ID = 0L;

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(String startAt) {
        this(DEFAULT_ID, startAt);
    }

    public ReservationTime(Long id, String startAt) {
        this(id, parseTime(startAt));
    }

    public ReservationTime(Long id, LocalTime startAt) {
        validateEmpty(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private static LocalTime parseTime(String startAt) {
        try {
            return LocalTime.parse(startAt);
        } catch (DateTimeParseException | NullPointerException e) {
            throw new IllegalArgumentException("유효하지 않은 시간입니다.");
        }
    }

    private static void validateEmpty(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("시간 정보에 빈 값이 포함될 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
