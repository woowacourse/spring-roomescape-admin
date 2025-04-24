package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationEntity(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeEntity time
) {
    public boolean isDuplicatedWith(ReservationEntity other) {
        return date.isEqual(other.date) && time.isDuplicatedWith(other.time);
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
