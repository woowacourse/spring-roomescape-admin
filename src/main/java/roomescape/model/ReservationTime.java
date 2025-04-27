package roomescape.model;

import java.time.LocalTime;

public record ReservationTime(
    Long id,
    LocalTime startAt
) implements Entity<ReservationTime> {

    @Override
    public ReservationTime withId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("[ERROR] 전달받은 id는 null일 수 없습니다.");
        }
        return new ReservationTime(id, startAt);
    }
}
