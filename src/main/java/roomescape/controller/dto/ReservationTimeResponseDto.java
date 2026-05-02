package roomescape.controller.dto;

import roomescape.service.dto.ReservationTimeDto;

public class ReservationTimeResponseDto {
    private final long id;
    private final String startAt;

    public ReservationTimeResponseDto(long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponseDto toDto(ReservationTimeDto reservationTime) {
        return new ReservationTimeResponseDto(reservationTime.getId(), reservationTime.getStartAt());
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
