package roomescape.dto;

import roomescape.domain.ReservationTime;

public class ReservationTimeResponseDto {
    private final Long id;
    private final String startAt;

    public ReservationTimeResponseDto(final ReservationTime reservationTime) {
        this.id = reservationTime.getId();
        this.startAt = reservationTime.getStartAt();
    }

    public ReservationTimeResponseDto(final Long id, final String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
