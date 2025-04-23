package roomescape.model;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public record ReservationTime(
    Long id,

    @NotNull(message = "[ERROR] 시간은 반드시 필요합니다.")
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
