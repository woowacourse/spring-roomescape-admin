package roomescape.model;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public record Time(
    Long id,

    @NotNull(message = "[ERROR] 시간은 반드시 필요합니다.")
    LocalTime startAt
) implements Entity<Time> {

    @Override
    public Time withId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("[ERROR] 전달받은 id는 null일 수 없습니다.");
        }
        return new Time(id, startAt);
    }
}
