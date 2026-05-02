package roomescape.reservationtime;

import java.time.LocalTime;

public class ReservationTimeResponseDTO {
    private final Long id;
    private final LocalTime startAt;

    public ReservationTimeResponseDTO(Long id, LocalTime startAt) {
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
