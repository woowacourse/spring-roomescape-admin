package roomescape.domain;

import java.time.LocalTime;
import roomescape.dto.ReservationTimeResponseDto;

public class ReservationTime {
    private Long id;
    private LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) throws NullPointerException {
        validate(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validate(LocalTime startAt) {
        if(startAt == null) {
            throw new NullPointerException();
        }
    }

    public ReservationTimeResponseDto toDto() {
        return new ReservationTimeResponseDto(id, startAt);
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
