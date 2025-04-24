package roomescape.entity;

import java.time.LocalTime;

public record ReservationTimeEntity(
        Long id,
        LocalTime startAt
) {
    private static final LocalTime RUNNING_TIME = LocalTime.of(2, 0);
    private static final LocalTime OPERATING_START = LocalTime.of(10, 0);
    private static final LocalTime OPERATING_END = LocalTime.of(22, 0);

    public ReservationTimeEntity {
        if (startAt.isBefore(OPERATING_START) || startAt.isAfter(OPERATING_END)) {
            throw new IllegalArgumentException("운영 시간 이외의 날짜는 예약할 수 없습니다.");
        }
    }

    public boolean isDuplicatedWith(ReservationTimeEntity other) {
        LocalTime otherStartAt = other.startAt();
        LocalTime endAt = startAt.plusSeconds(RUNNING_TIME.toSecondOfDay());
        return (otherStartAt.isAfter(startAt) || otherStartAt.equals(startAt))
                && otherStartAt.isBefore(endAt);
    }
}
