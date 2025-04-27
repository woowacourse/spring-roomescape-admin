package roomescape.entity;

import java.time.LocalTime;

public record ReservationTimeEntity(
        Long id,
        LocalTime startAt
) {
    private static final LocalTime RUNNING_TIME = LocalTime.of(2, 0);
    private static final LocalTime OPERATING_START = LocalTime.of(10, 0);
    private static final LocalTime OPERATING_END = LocalTime.of(22, 0);

    public boolean isDuplicatedWith(ReservationTimeEntity other) {
        LocalTime otherStartAt = other.startAt();
        final int interval = Math.abs(otherStartAt.toSecondOfDay() - startAt.toSecondOfDay());
        return interval < RUNNING_TIME.toSecondOfDay();
    }

    public boolean isAvailable() {
        return !(startAt.isBefore(OPERATING_START) || startAt.isAfter(OPERATING_END));
    }
}
