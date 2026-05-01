package roomescape.domain.time.dto;

import java.time.LocalTime;
import roomescape.domain.time.ReservationTime;

public class ReservationTimeResponseDTO {
    private final Long id;
    private final LocalTime startAt;

    public ReservationTimeResponseDTO(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponseDTO from(ReservationTime reservationTime) {
        return new ReservationTimeResponseDTO(reservationTime.getId(), reservationTime.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
