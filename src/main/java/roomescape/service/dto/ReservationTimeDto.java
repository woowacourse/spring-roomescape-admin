package roomescape.service.dto;

import roomescape.domain.ReservationTime;

public class ReservationTimeDto {
    private final long id;
    private final String startAt;

    public ReservationTimeDto(long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeDto toDto(ReservationTime reservationTime) {
        return new ReservationTimeDto(reservationTime.getId(), reservationTime.getStartAt().toString());
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
