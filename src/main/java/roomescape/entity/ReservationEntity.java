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

    // TODO: 테스트 - now 의존성 제거 어떻게 할 것인지?
    public ReservationEntity {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime dateTime = LocalDateTime.of(date, time.startAt());
//        if (dateTime.isBefore(now)) {
//            throw new IllegalArgumentException("과거 시간에는 예약을 생성할 수 없습니다.");
//        }
    }

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
