package roomescape;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationEntity(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    private static final LocalTime runningTime = LocalTime.of(2, 0);

    public boolean isDuplicatedWith(LocalDateTime otherStartTime) {
        LocalDateTime startTime = LocalDateTime.of(date, time);
        LocalDateTime endTime = startTime.plusSeconds(runningTime.toSecondOfDay());
        return (otherStartTime.isAfter(startTime) || otherStartTime.isEqual(startTime))
                && otherStartTime.isBefore(endTime);
    }

    public ReservationEntity changeId(final Long id) {
        return new ReservationEntity(id, name, date, time);
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date, time);
    }
}
