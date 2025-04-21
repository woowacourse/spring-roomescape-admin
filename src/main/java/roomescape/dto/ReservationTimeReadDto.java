package roomescape.dto;

import java.time.LocalTime;

public class ReservationTimeReadDto {
    private Long id;
    private LocalTime startAt;

    public ReservationTimeReadDto(Long id, LocalTime startAt) {
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
