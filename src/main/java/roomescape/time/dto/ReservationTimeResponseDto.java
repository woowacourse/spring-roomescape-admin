package roomescape.time.dto;

import roomescape.time.domain.ReservationTime;

public class ReservationTimeResponseDto {

    private final Long id;
    private final String startAt;

    public ReservationTimeResponseDto(final Long id, final String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponseDto from(final ReservationTime reservationTime) {
        return new ReservationTimeResponseDto(reservationTime.getId(), reservationTime.getStartAt().toString());
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
