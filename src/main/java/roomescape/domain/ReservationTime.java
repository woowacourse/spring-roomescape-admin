package roomescape.domain;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public class ReservationTime {
    @NotNull(message = "[ERROR] id는 비어 있을 수 없습니다.")
    private final Long id;

    @NotNull(message = "[ERROR] 예약 시간은 비어 있을 수 없습니다.")
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
