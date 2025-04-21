package roomescape;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public record Reservation(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    private static final AtomicLong index = new AtomicLong(1);
    private static final LocalTime runningTime = LocalTime.of(2, 0);

    public static Reservation of(String name, LocalDateTime dateTime) {
        return new Reservation(
                index.getAndIncrement(),
                name,
                dateTime.toLocalDate(),
                dateTime.toLocalTime()
        );
    }

    public boolean isDuplicatedWith(Reservation other) {
        LocalDateTime startTime = LocalDateTime.of(date, time);
        LocalDateTime endTime = startTime.plusSeconds(runningTime.toSecondOfDay());
        LocalDateTime otherStartTime = LocalDateTime.of(other.date, other.time);
        return (otherStartTime.isAfter(startTime) || otherStartTime.isEqual(startTime))
                && otherStartTime.isBefore(endTime);
    }

    public boolean isSameId(final Long id) {
        return this.id.equals(id);
    }
}
