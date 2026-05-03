package roomescape.reservationtime;

import java.time.LocalTime;

public class ReservationTimeRequestDTO {
    private final LocalTime startAt;

    public ReservationTimeRequestDTO(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
