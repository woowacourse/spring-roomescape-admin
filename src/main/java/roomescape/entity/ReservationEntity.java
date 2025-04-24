package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationEntity(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeEntity time
) {
    private static final LocalTime runningTime = LocalTime.of(2, 0);

    public boolean isDuplicatedWith(ReservationEntity other) {
        LocalDateTime startTime = LocalDateTime.of(date, time.startAt());
        LocalDateTime endTime = startTime.plusSeconds(runningTime.toSecondOfDay());
        LocalDateTime otherStartTime = other.getDateTime();
        return (otherStartTime.isAfter(startTime) || otherStartTime.isEqual(startTime))
                && otherStartTime.isBefore(endTime);
    }

    public ReservationEntity changeId(final Long id) {
        return new ReservationEntity(id, name, date, time);
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date, time.startAt());
    }

    public Long getTimeId() {
        return time.id();
    }
}
