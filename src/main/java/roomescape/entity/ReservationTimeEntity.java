package roomescape.entity;

import java.time.LocalTime;

public record ReservationTimeEntity(
        Long id,
        LocalTime startAt
) {
    private static final LocalTime RUNNING_TIME = LocalTime.of(2, 0);

    public boolean isDuplicatedWith(ReservationTimeEntity other) {
        LocalTime otherStartAt = other.startAt();
        LocalTime endAt = startAt.plusSeconds(RUNNING_TIME.toSecondOfDay());
        return (otherStartAt.isAfter(startAt) || otherStartAt.equals(startAt))
                && otherStartAt.isBefore(endAt);
    }
}
